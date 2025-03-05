package com.irc.BotSombra.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.irc.BotSombra.utils.Extraccion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class IAAccion {
    private static final  String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    @Value("${clave_ia}")
    private String API_KEY;


    public String Accion(String nombre, String descripcion){
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
                """.formatted(CrearPrompt(nombre,descripcion));
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


    private String CrearPrompt(String nombre, String descripcion) {
        String descripcionLimpia = descripcion
                .replace("\"", "\\\"")
                .replace("\n", " ");

        return "Tengo un ser con el siguiente nombre: " + nombre +
                " y la siguiente descripción: " + descripcionLimpia +
                ". Genera una acción en primera persona utilizando el prefijo /me , en la que, basada en sus características, realice algo al azar. " +
                " La acción debe comenzar con '/me invoca a ' seguido del nombre del ser antes de describir lo que hace." +
                "Responde en este formato:" +
                "{accion: /me descripción de la acción}";
    }

    private String extraer(String respuesta){
        String accion = "";
        JsonParser parse = new JsonParser();
        JsonObject respu = parse.parse(respuesta).getAsJsonObject();

        JsonArray candidates = respu.getAsJsonArray("candidates");
        JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
        JsonObject content = firstCandidate.getAsJsonObject("content");
        JsonArray parts = content.getAsJsonArray("parts");
        String responseText = parts.get(0).getAsJsonObject().get("text").getAsString();

        if (responseText.contains("accion: ")){
            accion = responseText.split("accion: ")[1].split("\"")[0];
        }
        return Extraccion.extraerHastaCaracter(accion, '}');
    }
}
