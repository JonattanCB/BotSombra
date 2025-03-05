package com.irc.BotSombra.dto.clasificacion;

import com.irc.BotSombra.model.Categoria;
import com.irc.BotSombra.model.Nivel;

public record Clasificacionobjdto(
        Categoria categoria,
        Nivel nivel
) {
}
