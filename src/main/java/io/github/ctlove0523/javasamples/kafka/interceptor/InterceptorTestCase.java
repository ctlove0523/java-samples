package io.github.ctlove0523.javasamples.kafka.interceptor;

import io.github.ctlove0523.javasamples.kafka.PropertiesFactory;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: InterceptorTestCase
 *
 * @author: chentong
 * Date:     2019/1/24 19:31
 */
public class InterceptorTestCase {
    private static ExecutorService executor = Executors.newFixedThreadPool(3);

    @Test
    public void test_kafkaInterceptor() throws Exception {
        Properties producerProperties = PropertiesFactory.getProducerProperties();

        // configure producer interceptor
        List<String> producerInterceptors = new ArrayList<>(1);
        producerInterceptors.add("io.github.ctlove0523.javasamples.kafka.interceptor.TraceProducerInterceptor");
        producerProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, producerInterceptors);

        // use thread pool to send message
        Producer<String, String> producer = new KafkaProducer<>(producerProperties);
        List<Future> sendResults = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sendResults.add(executor.submit(new Runnable() {
                @Override
                public void run() {
                    producer.send(new ProducerRecord<>("interceptor", "Hello Interceptor"));
                }
            }));
        }

        sendResults.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        });

        Properties consumerProperties = PropertiesFactory.getConsumerProperties();
        List<String> consumerInterceptors = new ArrayList<>(1);
        consumerInterceptors.add("io.github.ctlove0523.javasamples.kafka.interceptor.TraceProducerInterceptor");
        consumerProperties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, consumerInterceptors);

        Consumer<String,String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("interceptor"));

        for (;;) {
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                record.headers().headers("producer thread")
                        .forEach(header -> System.out.print("producer thread = " + new String(header.value())));
                record.headers().headers("consumer Thread")
                        .forEach(header -> System.out.print("\t consumer Thread = " + new String(header.value())));
            });
        }

    }
}
