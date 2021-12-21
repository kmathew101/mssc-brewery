package guru.springframework.msscbrewery.services;


import com.github.javafaker.Faker;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    Faker faker;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName( faker.beer().name())
                .beerStyle( faker.beer().style())
                .build();
    }
}
