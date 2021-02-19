package com.techrocking.orchestrator.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.orchestrator.exception.ResourceNotFoundEx;
import com.techrocking.orchestrator.kafka.channel.OrchestratorChannel;
import com.techrocking.orchestrator.kafka.message.ItemEvent;
import com.techrocking.orchestrator.rest.util.RestClientUtil;

@Component
public class ItemOutOfStockListener {

	@Autowired
	private RestClientUtil restClient;
	
	@Value("${order.service.endpoint}")
	private String orderEndpoint;
	
	//order-service compensate metodu cagriliyo
	@StreamListener(OrchestratorChannel.INPUT_INVNETORY)
	public void listenOutOfStockItem(@Payload ItemEvent itemEvent) throws ResourceNotFoundEx {
		
		if (ItemEvent.Action.ITEMOUTOFSTOCK.equals(itemEvent.getAction())) {
		
			System.out.println("orchestarator-service: Item out of stock ,order-service is being called for compensation");
			
			restClient.doPost(orderEndpoint + "compensate", itemEvent.getOrderId());
		}
	}
}
