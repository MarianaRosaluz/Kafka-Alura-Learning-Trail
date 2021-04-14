package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] ars) throws ExecutionException, InterruptedException {
        try (var orderdispatcher = new KafkaDispatcher<Order>()) {
            try (var emaildispatcher = new KafkaDispatcher<Email>()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);
                    orderdispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
                    Email email = new Email("","Thank you for your order! we are processing your order");
                    emaildispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
                }
            }
        }
    }


}
