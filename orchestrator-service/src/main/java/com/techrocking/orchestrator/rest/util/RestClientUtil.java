package com.techrocking.orchestrator.rest.util;
 
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.techrocking.orchestrator.exception.ResourceNotFoundEx;

@Component
public class RestClientUtil {

	public String doPost(String url ,Long orderId) throws ResourceNotFoundEx{
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			OrderRequest req = new OrderRequest();
			req.setOrderId(orderId);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("orderId", orderId.toString());
			
			String response = restTemplate.postForObject(url, req, String.class);
			return response;
			
		}catch (RestClientException ex) {
			throw new ResourceNotFoundEx("service unavailable");
		}
	}
}
