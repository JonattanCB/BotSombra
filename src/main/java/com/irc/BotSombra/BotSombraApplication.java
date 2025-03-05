package com.irc.BotSombra;

import com.irc.BotSombra.controllers.PrincipalControllers;
import com.irc.BotSombra.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotSombraApplication implements CommandLineRunner {
	@Autowired
	protected PrincipalControllers principalControllers;

	public static void main(String[] args) {
		SpringApplication.run(BotSombraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principalControllers.start();
	}
}
