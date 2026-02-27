package com.totvs.contas_api;

import org.springframework.boot.SpringApplication;

public class TestContasApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(ContasApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
