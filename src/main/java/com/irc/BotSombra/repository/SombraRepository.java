package com.irc.BotSombra.repository;


import com.irc.BotSombra.model.Sombra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SombraRepository extends JpaRepository<Sombra, Integer> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM sombra s WHERE s.nombre = :nombre AND s.usuario.nick = :usuario")
    boolean existsByNombreAndUsuario(String nombre, String usuario);

    @Query("SELECT s FROM sombra s WHERE s.usuario.nick = :usuario")
    List<Sombra> findAllByUsuario(String usuario);

    Sombra getSombraByNombre(String nombre);
}
