package com.irc.BotSombra.test;

import com.irc.BotSombra.config.IAAccion;
import com.irc.BotSombra.controllers.SombraControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Test {

    @Autowired
    private SombraControllers sombraControllers;

    @Autowired
    IAAccion accion;

    public void test() {
        var sombra = sombraControllers.getSombraByNombre("igris");
        var accion1 = accion.Accion(sombra.getNombre(),sombra.getDescripcion());
        System.out.println(accion1);
    }

}
