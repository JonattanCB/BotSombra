package com.irc.BotSombra.dto.sombra;

import com.irc.BotSombra.model.Usuario;

public record Sombrasavedto(
        String nombre,
        String Description,
        Usuario usuario
) {
}
