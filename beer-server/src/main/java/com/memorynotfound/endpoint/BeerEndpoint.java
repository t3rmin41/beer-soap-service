package com.memorynotfound.endpoint;

import com.memorynotfound.beer.Beer;
import com.memorynotfound.beer.BeerRequest;
import com.memorynotfound.beer.BeerResponse;
import com.memorynotfound.beer.BeerService;
import com.memorynotfound.beer.ObjectFactory;

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
