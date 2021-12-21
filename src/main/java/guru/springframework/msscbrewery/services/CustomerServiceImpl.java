package guru.springframework.msscbrewery.services;

import com.github.javafaker.Faker;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-21.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    Faker faker;

    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name(faker.name().lastName() + " " +  faker.name().firstName())
                .build();
    }
}
