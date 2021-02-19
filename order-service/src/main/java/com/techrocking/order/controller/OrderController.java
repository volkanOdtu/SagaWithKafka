package com.techrocking.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techrocking.order.kafka.channel.OrderChannel;
import com.techrocking.order.payload.PlaceOrderRequest;
import com.techrocking.order.payload.PlaceOrderResponse;
import com.techrocking.order.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping()
	public PlaceOrderResponse placeOrder(@RequestParam("itemId") long itemId ,@RequestParam("customerId") long customerId  /*PlaceOrderRequest request*/) {
		
		PlaceOrderRequest request = new PlaceOrderRequest();
		System.out.println("order-service: order is placed");
		
		request.setItemId(itemId);
		request.setCustomerId(customerId);
		
		return orderService.createOrder(request);
	}
	
	@PostMapping("/compensate")
	public PlaceOrderResponse compensateOrder(@RequestBody OrderRequest req) {
		System.out.println("order-service: order is being compensated");
		
		orderService.compensateOrder(req.getOrderId());
		PlaceOrderResponse response = new PlaceOrderResponse();
		response.setMessage("Order compensate request has placed ");
		
		return response;
		/*if(itemEvent.getAction().equals(ItemEvent.Action.ITEMOUTOFSTOCK)) {
			System.out.println("Order service: Item out of stock for the item: " + itemEvent.getItemId());
			System.out.println("Order service: Going to compensate order for id: " + itemEvent.getOrderId());
			orderService.compensateOrder(itemEvent.getOrderId());
		}*/
	}
}
