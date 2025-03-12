package com.demo.controller;

import com.demo.models.User;
import com.demo.service.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	Producer kafkaProducer;

	@PostMapping(value = "/publish")
	public ResponseEntity<User> postModelToKafka(@RequestBody User message)
			throws InterruptedException, ExecutionException {
		ListenableFuture<SendResult<String, User>> result = kafkaProducer.sendMessageToTopic(message);
		return new ResponseEntity<>(result.get().getProducerRecord().value(), HttpStatus.OK);
	}
}
