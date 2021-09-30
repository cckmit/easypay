package ink.breakpoint.easypay.verification.config;


import ink.breakpoint.easypay.verification.handler.AbstractVerificationHandler;
import ink.breakpoint.easypay.verification.handler.SmsNumberVerificationHandler;
import ink.breakpoint.easypay.verification.sms.qcloud.QCloudSmsService;
import ink.breakpoint.easypay.verification.store.VerificationStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BusinessConfig {

	@Autowired
	private VerificationStore verificationStore;

	@Autowired
	private QCloudSmsService qCloudSmsService;

	@Bean
	public SmsNumberVerificationHandler smsNumberVerificationHandler() {
		SmsNumberVerificationHandler smsNumberVerificationHandler = new SmsNumberVerificationHandler("sms", 6);
		smsNumberVerificationHandler.setVerificationStore(verificationStore);
		smsNumberVerificationHandler.setSmsService(qCloudSmsService);
		return smsNumberVerificationHandler;
	}

	@Bean
	public Map<String, AbstractVerificationHandler> verificationHandlerMap() {
		List<AbstractVerificationHandler> verificationHandlerList = new ArrayList<>();
		verificationHandlerList.add(smsNumberVerificationHandler());

		Map<String, AbstractVerificationHandler> verificationHandlerMap = new HashMap<>();
		for (AbstractVerificationHandler handler : verificationHandlerList) {
			verificationHandlerMap.put(handler.getName(), handler);
		}
		return verificationHandlerMap;
	}

}
