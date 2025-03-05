package com.irc.BotSombra.dto.categoria;

import com.irc.BotSombra.model.Estado;

public record Categoriadto(
        String nombre,
        String descripcion,
        Estado estado
) {
}
