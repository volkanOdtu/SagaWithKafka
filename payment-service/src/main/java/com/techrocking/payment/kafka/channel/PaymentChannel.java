package com.techrocking.payment.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentChannel {
	String OUTPUT = "payment-out";
	
	@Output(OUTPUT)
	MessageChannel outboundPayment();
	
}
