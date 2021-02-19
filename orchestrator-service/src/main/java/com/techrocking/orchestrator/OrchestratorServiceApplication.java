package com.techrocking.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.techrocking.orchestrator.kafka.channel.OrchestratorChannel;

@SpringBootApplication
@EnableBinding(OrchestratorChannel.class)
public class OrchestratorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorServiceApplication.class, args);
	}

}
