package com.irc.BotSombra.controllers;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.usuario.Usuariodto;
import com.irc.BotSombra.model.Usuario;
import com.irc.BotSombra.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UsuarioControllers {

    private Usuario usuario;

    @Autowired
    private UsuarioService usuarioService;

    public void saveUsuario(String nick, String clave, String descripcion, Clasificacionobjdto clasificacionobjdto){
        Usuariodto usuariodto = new Usuariodto(nick, descripcion, clave);
        usuarioService.saveUsuario(usuariodto, clasificacionobjdto) ;
    }

    public void updateEstado(String[] comando){
        String nick = comando[1];
        usuarioService.updateEstado(nick);
    }

    public Usuario getUsuario(String nick){
        return usuarioService.getUsuario(nick);
    }

    public boolean existeUsuario(String nick){
        return usuarioService.isUserExist(nick);
    }

}
