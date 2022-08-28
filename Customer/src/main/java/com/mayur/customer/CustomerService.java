package com.mayur.customer;

import com.mayur.amqp.RabbitMQMessageProducer;
import com.mayur.clients.fraud.FraudCheckResponse;
import com.mayur.clients.fraud.FraudClient;
import com.mayur.clients.notification.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FraudClient fraudClient;

    @Autowired
    RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .build();
        customerRepository.saveAndFlush(customer);



        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.getIsFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        rabbitMQMessageProducer.publish(new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to World...",
                        customer.getFirstName())
        ), "internal.exchange", "internal.notification.routing-key");
    }
}
