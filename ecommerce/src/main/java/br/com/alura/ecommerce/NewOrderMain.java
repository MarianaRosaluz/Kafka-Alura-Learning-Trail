package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public  static void main(String [] ars) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var value = "21323,123123,131";
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value);
       Callback callback = (data, ex) ->{
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timeStamp " + data.timestamp());
        };
       var email = "Welcome! we are processing your order";
       var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", "email","email");
       producer.send(record, callback).get();
        producer.send(emailRecord, callback).get();
    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return properties;
    }

}
