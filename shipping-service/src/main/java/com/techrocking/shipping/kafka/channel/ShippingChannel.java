package com.techrocking.shipping.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ShippingChannel {

	String OUTPUT = "shipping-out";
	 
	@Output(OUTPUT)
	MessageChannel outboundShipping();

}
