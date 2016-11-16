package com.simple.soap.repository;

import com.soap.simple.beer.Beer;

public interface BeerRepository {

  Beer getBeerById(Integer id);
}
