package com.irc.BotSombra.repository;

import com.irc.BotSombra.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRespository extends JpaRepository<Usuario, Integer> {

    boolean existsByNick(String nick);

    Usuario getUsuarioByNick(String nick);

}
