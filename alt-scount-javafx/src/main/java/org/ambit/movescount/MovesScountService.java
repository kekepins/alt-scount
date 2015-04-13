package org.ambit.movescount;

import java.util.ArrayList;
import java.util.List;

import org.ambit.movescount.model.AmbitDeviceInfo;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MovesScountService {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private static final String APP_KEY = "HpF9f1qV5qrDJ1hY1QK1diThyPsX10Mh4JvCw9xVQSglJNLdcwr3540zFyLzIC3e";
	private static final String MOVES_SCOUNT_URL = "https://uiservices.movescount.com/";
	
	private static final String USER_DEVICES_URI = "userdevices/";
	private static final String USER_DEVICES_PARAM = "?appkey=%s&email=%s&userKey=%s&onlychangedsettings=false&includeallcustommodes=false";

	public MovesScountService() {
		initRestTemplate();
	}
	
	private void initRestTemplate() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		
		// Add the Jackson Message converter
		messageConverters.add(converter);

		// Add the message converters to the restTemplate
		restTemplate.setMessageConverters(messageConverters);
	}
	
	public AmbitDeviceInfo readAmbitDeviceInfo(String deviceId, String mail, String userKey) {
		
		try {
			
			String url = String.format(MOVES_SCOUNT_URL + USER_DEVICES_URI + "%s" + USER_DEVICES_PARAM, deviceId, APP_KEY, mail, userKey);
			
			return  restTemplate.getForObject(url, AmbitDeviceInfo.class);
		}
		catch( RestClientException rce ) {
			rce.printStackTrace();
			return null;
		}
		
	}

}
