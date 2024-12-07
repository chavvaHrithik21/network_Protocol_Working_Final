package com.ring_protocol.network;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ring_protocol.network.model")
@EnableJpaRepositories(basePackages = "com.ring_protocol.network.repository")// <-- Adjust this to match your entity package
public class NetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(NetworkApplication.class, args);
	}
}