package com.irc.BotSombra.controllers;

import com.irc.BotSombra.dto.nivel.Niveldto;
import com.irc.BotSombra.model.Nivel;
import com.irc.BotSombra.service.NivelService;
import com.irc.BotSombra.utils.Colors;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NivelControllers {
    private Niveldto dto;
    private Nivel nivel;

    @Autowired
    private NivelService nivelService;

    public String saveNivel(String[] comando){
        if (comando.length > 2){
            String tipo = comando[1];
            int poder = Integer.parseInt(comando[2]);
            dto = new Niveldto(tipo, poder);
            nivelService.saveNivel(dto);
        }
        return comando[1];
    }

    public List<Nivel> nivelList(){return nivelService.nivelList();}

    @Transactional
    public  String updateNivel(String[] comando){
        if (comando.length > 2){
            String tipo = comando[1];
            int poder = Integer.parseInt(comando[2]);
            dto = new Niveldto(tipo, poder);
            return nivelService.updateNivelforPoder(dto);
        }else{
            return Colors.rojo +"[ ! ]Error, no se pudo actualizar el nivel";
        }
    }
    public Nivel byNivelPoder(String tipo){
        return nivelService.getNivel(tipo);
    }
}
