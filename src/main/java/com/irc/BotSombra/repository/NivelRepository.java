package com.irc.BotSombra.repository;

import com.irc.BotSombra.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    boolean existsByTipo(String tipo);
    Nivel findByTipo(String tipo);
}
