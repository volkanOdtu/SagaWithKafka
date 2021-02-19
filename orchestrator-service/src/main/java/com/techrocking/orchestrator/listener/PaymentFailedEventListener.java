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
public class PaymentFailedEventListener {

	@Autowired
	private RestClientUtil restClient;

	@Value("${inventory.service.endpoint}")
	private String inventoryEndpoint;
	
	@StreamListener(OrchestratorChannel.INPUT_PAYMENT)
	public void listenPaymentFailed(@Payload PaymentEvent paymentFailedMessage) throws ResourceNotFoundEx{
		
		if (PaymentEvent.PaymentAction.PAYMENTNOTRECEIVED.equals(paymentFailedMessage.getAction())) {
			
			if (paymentFailedMessage.getOrderId() != null) {
				System.out.println("Calling the payment-service to compensate item for order id : "+ paymentFailedMessage.getOrderId());
				restClient.doPost(inventoryEndpoint + "compensate", paymentFailedMessage.getOrderId());
			}
		}

	}
}
