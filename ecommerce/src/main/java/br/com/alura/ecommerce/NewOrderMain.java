package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] ars) throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()) {
            for (var i = 0; i < 100; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + "123123,131";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                var email = "Thank you for your order! we are processing your order";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }


}
