package io.github.ctlove0523.javasamples.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TraceProducerInterceptor
 *
 * @author: chentong
 * Date:     2019/1/23 21:39
 */
public class TraceProducerInterceptor implements ProducerInterceptor<String, String> {
    private AtomicLong successCounts = new AtomicLong(0);
    private AtomicLong failedCounts = new AtomicLong(0);

    @Override
    public ProducerRecord<String,String> onSend(ProducerRecord<String,String > record) {
        Header producerThread = new RecordHeader("producer thread",Thread.currentThread().getName().getBytes());
        record.headers().add(producerThread);
        return new ProducerRecord<>(record.topic(),record.partition(),record.timestamp(),record.key(),record.value(),record.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (null == exception) {
            successCounts.getAndIncrement();
        } else {
            failedCounts.getAndIncrement();
        }
    }

    @Override
    public void close() {
        System.out.println("success counts " + successCounts.get());
        System.out.println("failed counts " + failedCounts);
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}
