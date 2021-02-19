package com.techrocking.notification.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.notification.kafka.channel.NotificationChannel;
import com.techrocking.notification.kafka.message.ShippingEvent;

@Component
public class GoodsShippedEventListener {
	
	@StreamListener(NotificationChannel.INPUT_SHIPPING)
	public void listensGoodsShipped(@Payload ShippingEvent goodsShippedMessage) {
		 
		if(ShippingEvent.Action.GOODSSHIPPED.equals(goodsShippedMessage.getAction())) {

			if(goodsShippedMessage.getOrderId() != null) {
				System.out.println("notification-service: goods shipped orderId: "+ goodsShippedMessage.getOrderId());
			}
		}
	}
}
