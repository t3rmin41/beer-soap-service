package com.simple.soap.endpoint;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.simple.soap.repository.BeerRepository;
import com.soap.simple.beer.Beer;
import com.soap.simple.beer.BeerRequest;
import com.soap.simple.beer.BeerResponse;
import com.soap.simple.beer.BeerService;
import com.soap.simple.beer.ObjectFactory;

@Component
public class BeerEndpoint implements BeerService {

    private ObjectFactory factory = new ObjectFactory();
    
    @Inject
    private BeerRepository beerRepo;
    
    @Override
    public BeerResponse getBeer(BeerRequest beerRequest) {
        BeerResponse beerResponse = factory.createBeerResponse();
        
        Beer beer = factory.createBeer();
        
        Integer requestId = beerRequest.getId();
        beer = beerRepo.getBeerById(requestId);
        beerResponse.setBeer(beer);
        return beerResponse;
    }

}
