package com.irc.BotSombra.service;

import com.irc.BotSombra.dto.nivel.Niveldto;
import com.irc.BotSombra.model.Nivel;
import com.irc.BotSombra.repository.NivelRepository;
import com.irc.BotSombra.utils.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class NivelService {

    @Autowired
    private NivelRepository nivelRepository;

    public void saveNivel(Niveldto niveldto){
        if (!existNivel(niveldto.tipo())) {
            nivelRepository.save(new Nivel(niveldto));
        }
    }

    public String  updateNivelforPoder(Niveldto niveldto){
        if (existNivel(niveldto.tipo())) {
            Nivel nivel = getNivel(niveldto.tipo());
            nivel.editpoder(niveldto.poder());
            return Colors.verde + "[ + ] Nivel actualizado: " + nivel.getTipo() + " " + nivel.getPoder();
        }else{
            return Colors.rojo +"[ ! ] Nivel no encontrado";
        }
    }

    public List<Nivel> nivelList() {return nivelRepository.findAll();}

    private boolean existNivel(String tipo){
        return nivelRepository.existsByTipo(tipo);
    }

    public Nivel getNivel(String tipo) {
        return nivelRepository.findByTipo(tipo);
    }

}
