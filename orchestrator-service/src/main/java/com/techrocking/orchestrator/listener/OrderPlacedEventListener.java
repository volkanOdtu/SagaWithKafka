package com.techrocking.orchestrator.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.orchestrator.exception.ResourceNotFoundEx;
import com.techrocking.orchestrator.kafka.channel.OrchestratorChannel;
import com.techrocking.orchestrator.kafka.message.OrderEvent;
import com.techrocking.orchestrator.rest.util.RestClientUtil;

@Component
public class OrderPlacedEventListener {

	@Autowired
	private RestClientUtil restClient;

	@Value("${inventory.service.endpoint}")
	private String inventoryEndpoint;

	
	@StreamListener(target = OrchestratorChannel.INPUT_ORDER)
	public void listenOrderPlaced(@Payload OrderEvent orderEvent) {
		
		if (OrderEvent.OrderAction.ORDERPLACED.equals(orderEvent.getAction())) {
			System.out.println("Orchestrator-service: Inventory service is going to be called for the order " + orderEvent.getOrderId());
			
			try {
				restClient.doPost(inventoryEndpoint, orderEvent.getOrderId());
			} catch (ResourceNotFoundEx e) {
				e.printStackTrace();
			}
		}
	}
}
