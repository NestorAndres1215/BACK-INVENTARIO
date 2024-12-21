package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.backend.servicio.UsuarioService;



@SpringBootApplication
public class BackendMiniMarketApplication implements CommandLineRunner{
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BackendMiniMarketApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
			
	}
}
