package com.aips.mio.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.aips.mio.interfaces.IServiceKafka;

@Service
public class KakfaImpl implements IServiceKafka{
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KakfaImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

	@Override
	public void inviaMessaggio(String topic, String messaggio) {
		 kafkaTemplate.send(topic, messaggio);	
	}
}
