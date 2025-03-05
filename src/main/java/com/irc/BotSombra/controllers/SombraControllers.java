package com.irc.BotSombra.controllers;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.sombra.Sombrasavedto;
import com.irc.BotSombra.model.Sombra;
import com.irc.BotSombra.service.SombraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SombraControllers {

    @Autowired
    private SombraService sombraService;

    public String saveSombra(Sombrasavedto sombrasavedto, Clasificacionobjdto clasificacionobjdto){
        return sombraService.saveSombra(clasificacionobjdto, sombrasavedto);
    }

    public List<Sombra> sombraList(String usuario){
        return sombraService.sombraList(usuario);
    }

    public Sombra getSombraByNombre(String nombre){
        return sombraService.getSombraByNombre(nombre);
    }

}

