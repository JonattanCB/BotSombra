package com.irc.BotSombra.config;

import org.jibble.pircbot.PircBot;

public class MyBot extends PircBot {
    private final String IRC_SERVER =   "irc.chathispano.net";
    private final int IRC_PORT = 6667;
    private String channel = "#mysombra";
    public  String nick = "Sombra21314256";

    public void start() {
        try {
            configure();
            this.connect(IRC_SERVER, IRC_PORT);
            this.joinChannel(channel);
        }catch (Exception e){
            System.err.printf("Error al conectar al servidor: %s", e.getMessage());
        }
    }

    private void configure() {
        this.setName(nick);
        this.setVerbose(true);
    }

}
