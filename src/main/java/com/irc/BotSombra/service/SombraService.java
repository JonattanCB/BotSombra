package com.irc.BotSombra.service;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.sombra.Sombrasavedto;
import com.irc.BotSombra.model.Sombra;
import com.irc.BotSombra.repository.SombraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SombraService {

    @Autowired
    private SombraRepository repository;

    public String saveSombra(Clasificacionobjdto clasificacionobjdto, Sombrasavedto sombradto){
        if (!existsByNombreAndUsuario(sombradto.nombre(), sombradto.usuario().getNick())){
            var sombra = new Sombra(sombradto, clasificacionobjdto);
            repository.save(sombra);
            return "[+] Sombra guardada";
        }else{
            return "[!] Ya existe una sombra con ese nombre";
        }
    }

    private boolean existsByNombreAndUsuario(String nombre, String usuario){
        return repository.existsByNombreAndUsuario(nombre, usuario);
    }

    public List<Sombra> sombraList(String usuario) {
       return  repository.findAllByUsuario(usuario);
    }

    public Sombra getSombraByNombre(String nombre) {
        return repository.getSombraByNombre(nombre);
    }
}
