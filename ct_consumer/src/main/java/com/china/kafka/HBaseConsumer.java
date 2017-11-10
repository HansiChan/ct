package com.china.kafka;

import com.china.dao.HBaseDAO;
import com.china.utils.PropertiesUtil;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HBaseConsumer {
    public static void main(String[] args) {
        // 创建配置对象
        ConsumerConfig consumerConfig = new ConsumerConfig(PropertiesUtil.properties);
        // 得到当前消费主题
        String callLogTopic = PropertiesUtil.getProperty("topic");

        // 订阅主题，开始消费
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put(callLogTopic, 1);

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = consumerConnector.createMessageStreams(topicMap, keyDecoder, valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(callLogTopic).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        HBaseDAO hBaseDAO = new HBaseDAO();
        while (it.hasNext()) {
            // 将消息实时写入到Hbase中
            String msg = it.next().message();
            System.out.println(msg);
            hBaseDAO.put(msg);
        }
    }
}

