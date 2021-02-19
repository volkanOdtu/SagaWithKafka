package com.techrocking.orchestrator.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.techrocking.orchestrator.exception.ResourceNotFoundEx;
import com.techrocking.orchestrator.kafka.channel.OrchestratorChannel;
import com.techrocking.orchestrator.kafka.message.ItemEvent;
import com.techrocking.orchestrator.rest.util.RestClientUtil;

@Component
public class ItemFetchedEventListener {
	
	@Autowired
	private RestClientUtil restClient;
	
	@Value("${payment.service.endpoint}")
	private String paymentEndPoint;

	@StreamListener(OrchestratorChannel.INPUT_INVNETORY)
	public void listenItemFetchedEvent(@Payload ItemEvent itemFetchedMessage)throws ResourceNotFoundEx{
		if(ItemEvent.Action.ITEMFETCHED.equals(itemFetchedMessage.getAction())) {
			
			System.out.println("orchestrator-service: payment-service is being called via RestTemplate" );
			if(itemFetchedMessage.getOrderId() != null && itemFetchedMessage.getItemId() != null) {
				restClient.doPost(paymentEndPoint, itemFetchedMessage.getOrderId());
			}
		}
	}
	
}
