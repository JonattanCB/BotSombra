package com.irc.BotSombra.repository;

import com.irc.BotSombra.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    boolean existsByNombre(String nombre);

    Categoria getCategoriaByNombre(String nombre);

}
