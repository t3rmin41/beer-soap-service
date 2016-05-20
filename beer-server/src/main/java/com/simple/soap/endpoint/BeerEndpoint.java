package com.simple.soap.endpoint;

import org.springframework.stereotype.Component;

import com.soap.simple.beer.Beer;
import com.soap.simple.beer.BeerRequest;
import com.soap.simple.beer.BeerResponse;
import com.soap.simple.beer.BeerService;
import com.soap.simple.beer.ObjectFactory;

@Component
public class BeerEndpoint implements BeerService {

    private ObjectFactory factory = new ObjectFactory();
    
    @Override
    public BeerResponse getBeer(BeerRequest beerRequest) {
        BeerResponse beerResponse = factory.createBeerResponse();
        
        Beer beer = factory.createBeer();
        
        Integer requestId = beerRequest.getId();
        beer.setId(requestId);
        beer.setName("Kroenenburg");
        beerResponse.setBeer(beer);
        return beerResponse;
    }

}
