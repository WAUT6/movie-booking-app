package com.waut.moviecatalogservice.repository;

import com.waut.moviecatalogservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
