package com.irc.BotSombra.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.irc.BotSombra.dto.clasificacion.Clasificaciondto;
import com.irc.BotSombra.model.Categoria;
import com.irc.BotSombra.model.Estado;
import com.irc.BotSombra.model.Nivel;
import com.irc.BotSombra.service.CategoriaService;
import com.irc.BotSombra.service.NivelService;
import com.irc.BotSombra.utils.Extraccion;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class IAClasificacion {
    private static final  String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    @Value("${clave_ia}")
    private String API_KEY;

    @Autowired
    private NivelService nivelService;

    @Autowired
    private CategoriaService categoriaService;

    public Clasificaciondto Clasificar(String descripcion){
       String respuesta="";
        try {
           HttpClient client = HttpClient.newHttpClient();
            String jsonBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(CrearPrompt(descripcion));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL+API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respuesta = response.body();
        }catch (Exception e){
           System.err.printf("Error al conectar al servidor: %s", e.getMessage());
       }
        return extraer(respuesta);
    }


    private String CrearPrompt(String descripcion) {
        String descripcionLimpia = descripcion
                .replace("\"", "\\\"")
                .replace("\n", " ");

        return "Tengo una base de datos con las siguientes categorías y tipos de poder: " +
                "Categorías: " + categorias() + " y " +
                "Tipos de poder: " + nivels() + "." +
                "La sombra tiene la siguiente descripción: " + descripcionLimpia +
                "Clasifica esta sombra asignándole una categoría y un tipo de poder según su descripción. " +
                "Responde en este formato: {categoria: nombre, poder: tipo-nivel}";
    }

    private String nivels(){
        String prompt = "";
        List<Nivel> list = nivelService.nivelList();
        for (Nivel nivel : list) {
            prompt += nivel.getTipo()+"-"+nivel.getPoder()+", ";
        }
        return prompt;
    }

    private  String categorias(){
        String prompt = "";
        List<Categoria> list =categoriaService.categoriaList().stream().filter(categoria -> categoria.getEstado() == Estado.ACTIVE).toList();
        for (Categoria categoria : list) {
            prompt += categoria.getNombre()+"("+categoria.getDescripcion()+"), ";
        }
        return prompt;
    }

    private Clasificaciondto extraer(String respuesta){
        String cate = "";
        String nivel = "";
        JsonParser parse = new JsonParser();
        JsonObject respu = parse.parse(respuesta).getAsJsonObject();

        JsonArray candidates = respu.getAsJsonArray("candidates");
        JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
        JsonObject content = firstCandidate.getAsJsonObject("content");
        JsonArray parts = content.getAsJsonArray("parts");
        String responseText = parts.get(0).getAsJsonObject().get("text").getAsString();

        if (responseText.contains("categoria: ")){
            cate = responseText.split("categoria: ")[1].split("\"")[0];
            cate = Extraccion.extraerHastaCaracter(cate, ',');
            nivel = responseText.split("poder: ")[1].split("\"")[0];
            nivel = Extraccion.extraerHastaCaracter(nivel, '-');
        }
        return new Clasificaciondto(cate, nivel);
    }


}
