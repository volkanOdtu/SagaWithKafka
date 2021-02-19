package com.techrocking.orchestrator.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.orchestrator.exception.ResourceNotFoundEx;
import com.techrocking.orchestrator.kafka.channel.OrchestratorChannel;
import com.techrocking.orchestrator.kafka.message.PaymentEvent;
import com.techrocking.orchestrator.rest.util.RestClientUtil;

@Component
public class PaymentReceivedEventListener {

	@Autowired
	private RestClientUtil restClient;
	
	@Value("${shipment.service.endpoint}")
	private String shipmentEndpoint;
	
	@StreamListener(OrchestratorChannel.INPUT_PAYMENT)
	public void listenItemFetchedEvent(@Payload PaymentEvent paymentReceivedMessage) throws ResourceNotFoundEx{
		
		if (PaymentEvent.PaymentAction.PAYMENTRECEIVED.equals(paymentReceivedMessage.getAction())) {
			 
			if (paymentReceivedMessage.getOrderId() != null) {
				System.out.println("Calling shipment service for order id : " + paymentReceivedMessage.getOrderId());
				restClient.doPost(shipmentEndpoint, paymentReceivedMessage.getOrderId());
			}
		}

	}
}
