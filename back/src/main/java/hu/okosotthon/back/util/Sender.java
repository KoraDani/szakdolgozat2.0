package hu.okosotthon.back.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class Sender {
    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @PostConstruct
    private void init() {
        Scanner scanner = new Scanner(System.in);

        askForMessage(scanner);
    }

    private void sendMessage(final String message) {
        final ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection()) {
            final Channel channel = connection.createChannel();

            channel.exchangeDeclare(RabbitMqUtil.getExchange(), RabbitMqUtil.getType());

            channel.basicPublish(RabbitMqUtil.getExchange(), RabbitMqUtil.getRoutingKey(), false, null, message.getBytes());
        } catch (TimeoutException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void askForMessage(final Scanner scanner){
        LOG.info("Enter your message: ");
        String s = scanner.nextLine();

        sendMessage(s);

        askForMessage(scanner);
    }
}
