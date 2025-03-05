package com.irc.BotSombra.controllers;

import com.irc.BotSombra.config.IAAccion;
import com.irc.BotSombra.config.IAClasificacion;
import com.irc.BotSombra.config.MyBot;
import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.sombra.Sombrasavedto;
import com.irc.BotSombra.model.Categoria;
import com.irc.BotSombra.model.Nivel;
import com.irc.BotSombra.utils.Colors;
import com.irc.BotSombra.utils.Verificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PrincipalControllers extends MyBot {

    @Autowired
    private CategoriaControllers categoriaControllers;

    @Autowired
    private NivelControllers nivelControllers;

    @Autowired
    private UsuarioControllers usuarioControllers;

    @Autowired
    private SombraControllers sombraControllers;

    @Autowired
    private IAClasificacion ia;

    @Autowired
    private IAAccion iaAccion;

    private String nickAdmin;

    public PrincipalControllers() {
        this.nickAdmin = "Murcielago-ConBravura";
    }

    @Override
    protected void onMessage(String sala, String nickSent, String subject, String ip, String messenger) {
        opcionesComandosCategoria(nickSent, messenger, sala);
        opcionesComandosNivel(nickSent, messenger, sala);
        opcionesComandoUsers(nickSent, messenger,sala);
        opcionesComandosSombra(nickSent, messenger, sala);
        super.onMessage(sala, nickSent, subject, ip, messenger);
    }

    private void opcionesComandosCategoria(String usuario, String mensaje, String sala){
        String[] comandos = mensaje.split(" ");
        switch (comandos[0]){
            case ".newCategoria" -> {
                if(usuario.equals(nickAdmin)){
                    if (comandos.length <= 2) {
                        this.sendMessage(sala, Colors.rojo +" [ ! ] Comando incorrecto, usa el comando .helpCategoria para ver los comandos disponibles");
                    }else {
                        var categoria = categoriaControllers.saveCategoria(comandos);
                        this.sendMessage(sala, Colors.verde+"[ + ] "+Colors.gris+" Se guardo exitosamente la categoria : " + categoria + " \uD83D\uDCC2 ");
                    }
                }
            }
            case  ".listCategoria" -> {
                List<Categoria> categorias = categoriaControllers.categoriaList();
                this.sendMessage(sala, Colors.azulOscuro+"\uD83D\uDCC2 [ + ] "+Colors.gris+" Listado de categorias");
                for (Categoria categoria : categorias) {
                    this.sendMessage(sala, Colors.verde+"[ * ] "+Colors.gris +categoria.getNombre() + ": " + categoria.getDescripcion());
                }
            }
            case ".editStatusCategoria" -> {
                if(usuario.equals(nickAdmin)) {
                    var rs = categoriaControllers.editStatus(comandos);
                    this.sendMessage(sala, rs);
                }
            }
            case ".helpCategoria" -> {
                this.sendMessage(sala, Colors.azulOscuro+"[ + ] "+Colors.gris+" Comandos disponibles para las categorias");
                this.sendMessage(sala, Colors.verde+"[ * ] "+Colors.gris+".newCategoria [nombre] [descripcion] : Guarda una nueva categoria (Solo Administrador)");
                this.sendMessage(sala, Colors.verde+"[ * ] "+Colors.gris+".listCategoria : Muestra todas las categorias");
                this.sendMessage(sala, Colors.verde+"[ * ] "+Colors.gris+".editStatusCategoria [nombre] : Cambia el estado de una categoria (Solo Administrador)");
            }
        }
    }

    private void opcionesComandosNivel(String usuario, String mensaje, String sala){
        String[] comandos = mensaje.split(" ");
        switch (comandos[0]){
            case ".newNivel" -> {
                if(usuario.equals(nickAdmin)) {
                    if (comandos.length <= 2) {
                        this.sendMessage(sala, Colors.rojo + " [ ! ] Comando incorrecto, usa el comando .helpNivel para ver los comandos disponibles");
                    } else {
                        var nivel = nivelControllers.saveNivel(comandos);
                        this.sendMessage(sala, Colors.verde + "[ + ] " + Colors.gris + " Se guardo exitosamente el nivel : " + nivel + " \uD83D\uDCC2 ");
                    }
                }
            }
            case  ".listNivel" -> {
                List<Nivel> nivels = nivelControllers.nivelList();
                this.sendMessage(sala, Colors.azulOscuro+"\uD83D\uDCC2 [ + ] "+Colors.gris+" Listado de Niveles");
                for (Nivel nivel : nivels) {
                    this.sendMessage(sala, Colors.verde+"[ * ] "+Colors.gris +nivel.getTipo() + ": la cantidad de ponder tiene " + nivel.getPoder());
                }
            }
            case ".updateNivel" -> {
                if(usuario.equals(nickAdmin)) {
                    var rs = nivelControllers.updateNivel(comandos);
                    this.sendMessage(sala, rs);
                }
            }
            case ".helpNivel" -> {
                this.sendMessage(sala, Colors.azulOscuro + "[ + ] " + Colors.gris + " Comandos disponibles para los niveles");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".newNivel [nombre] [poder] : Guarda un nuevo nivel (Solo Administrador)");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".listNivel : Muestra todos los niveles");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".updateNivel [nombre] [poder] : Actualiza el poder de un nivel (Solo Administrador)");
            }
        }
    }

    private void opcionesComandoUsers(String usuario ,String mensaje, String sala) {
        String[] comandos = mensaje.split(" ");
        switch (comandos[0]) {
            case ".player" -> {
                if (comandos.length <= 2) {
                    String clave = comandos[1];
                    String descripcion = "";
                    for (int i = 2; i < comandos.length; i++) {
                        descripcion += comandos[i] + " ";
                    }
                    usuarioControllers.saveUsuario(usuario, clave, descripcion, clasificacion(descripcion));
                    this.sendMessage(sala, Colors.verde + "[ + ]" + Colors.gris + " Te has registrado exitosamente");
                } else {
                    this.sendMessage(sala, Colors.rojo + "[ ! ] Comando incorrecto, usa el comando .helpUser para ver los comandos disponibles");
                }
            }
            case ".status" -> {
                if (usuario.equals(nickAdmin)) {
                    if (comandos.length < 2) {
                        usuarioControllers.updateEstado(comandos);
                        this.sendMessage(sala, "[ + ] Se actualizo el estado del usuario");
                    } else {
                        this.sendMessage(sala, Colors.rojo + "[ ! ] Comando incorrecto, usa el comando .helpUser para ver los comandos disponibles");
                    }
                }
            }
            case ".helpUser" -> {
                this.sendMessage(sala, Colors.azulOscuro + "[ + ] " + Colors.gris + " Comandos disponibles para los usuarios");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".player [clave] [descripcion] : Registra un nuevo usuario");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".status [nombre] : Cambia el estado de un usuario (Solo Administrador)");
            }
        }
    }

    private void opcionesComandosSombra(String usuario, String mensaje, String sala)  {
        String[] comando = mensaje.split(" ");
        switch (comando[0]){
            case ".invocacion" -> {
                if (comando.length >1) {
                    var sombra = sombraControllers.getSombraByNombre(comando[1]);
                    if (sombra != null) {
                        String temp = iaAccion.Accion(sombra.getNombre(), sombra.getDescripcion());
                        String accion = temp.substring(4);
                        this.sendAction(sala, accion);
                    } else {
                        this.sendMessage(sala, Colors.rojo + "[ ! ] La sombra no existe en tu coleccion");
                    }
                }else{
                    this.sendMessage(sala, Colors.rojo + "[ ! ] Comando incorrecto, usa el comando .helpSombra para ver los comandos disponibles");
                }
            }
            case ".listSombra" -> {
                var sombras = sombraControllers.sombraList(usuario);
                this.sendMessage(sala, Colors.azulOscuro + "[ + ] " + Colors.gris + " Listado de tus sombras");
                for (var sombra : sombras) {
                    this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + sombra.getNombre() + ": " + sombra.getDescripcion());
                }
            }
            case ".helpSombra" -> {
                this.sendMessage(sala, Colors.azulOscuro + "[ + ] " + Colors.gris + " Comandos disponibles para las sombras");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".invocacion [nombre] : Invoca una sombra");
                this.sendMessage(sala, Colors.verde + "[ * ] " + Colors.gris + ".listSombra : Muestra todas tus sombras");
            }
            default -> {
                createSombra(usuario,comando,sala);
            }
        }
    }

    private void createSombra(String usuario, String[] comando, String sala)  {
        var user = usuarioControllers.getUsuario(usuario);
        if (usuarioControllers.existeUsuario(usuario)){
            if (comando[0].equalsIgnoreCase("."+user.getClave())){
                String nombSombra = comando[1];
                String descripcion = "";
                for (int i = 2; i < comando.length; i++) {
                    descripcion += comando[i] + " ";
                }
                var clasificacion = clasificacion(descripcion);
                var sombrasavedto = new Sombrasavedto(nombSombra,descripcion,user);

                if (clasificacion.nivel().getPoder() < user.getNivel().getPoder()){
                    this.sendMessage(sala, Colors.rojo + "[ ! ] No puedes crear una sombra con un nivel de poder mayor al tuyo");
                    if (Verificador.verificador()){
                        var resultado = sombraControllers.saveSombra(sombrasavedto, clasificacion);
                        this.sendMessage(sala,resultado);
                    }else{
                        this.sendMessage(sala, Colors.rojo + "[ ! ] No se pudo guardar la sombra");
                    }
                }else{
                    var resultado = sombraControllers.saveSombra(sombrasavedto, clasificacion);
                    this.sendMessage(sala,resultado);
                }
            }else{
                this.sendMessage(sala, Colors.rojo+"[ ! ] Clave incorrecta en el sistema");
            }
        }else{
            this.sendMessage(sala, Colors.rojo+"[ ! ] El jugador no esta registrado en el sistema");
        }
    }

    private Clasificacionobjdto clasificacion(String Descricion){
        var prompt = ia.Clasificar(Descricion);
        Nivel nivel = nivelControllers.byNivelPoder(prompt.nivel());
        Categoria categoria = categoriaControllers.getCategoria(prompt.categoria());
        if (nivel== null){
            nivel = nivelControllers.byNivelPoder("B");
        }
        if(categoria == null){
            categoria = categoriaControllers.getCategoria("Normal");
        }
        return new Clasificacionobjdto( categoria,nivel);
    }


}