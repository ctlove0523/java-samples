package io.github.ctlove0523.javasamples.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.util.Map;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TraceConsumerInterceptor
 *
 * @author: chentong
 * Date:     2019/1/23 22:22
 */
public class TraceConsumerInterceptor implements ConsumerInterceptor<String, String> {

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        byte[] currentThreadName = Thread.currentThread().getName().getBytes();
        Header header = new RecordHeader("consumer Thread", currentThreadName);
        records.forEach(record -> record.headers().add(header));
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
