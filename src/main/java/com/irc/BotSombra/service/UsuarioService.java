package com.irc.BotSombra.service;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.usuario.Usuariodto;
import com.irc.BotSombra.model.Estado;
import com.irc.BotSombra.model.Usuario;
import com.irc.BotSombra.repository.UsuarioRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRespository usuarioRepository;

    public Usuario saveUsuario(Usuariodto usuariodto, Clasificacionobjdto clasificacionobjdto){
        if (!isUserExist(usuariodto.nick())){
            Usuario usuario = new Usuario(usuariodto,clasificacionobjdto);
            usuarioRepository.save(usuario);
            return usuario;
        }
        return getUsuario(usuariodto.nick());
    }

    @Transactional
    public void updateEstado(String nick){
        Usuario usuario = getUsuario(nick);
        if (usuario.getEstado() == Estado.ACTIVE ){
            usuario.setEstado(Estado.INACTIVE);
        }else {
            usuario.setEstado(Estado.ACTIVE);
        }
    }

    public boolean isUserExist(String nick){
        return usuarioRepository.existsByNick(nick);
    }

    public Usuario getUsuario(String nick){
        return usuarioRepository.getUsuarioByNick(nick);
    }
}
