package com.waut.moviecatalogservice.repository;

import com.waut.moviecatalogservice.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
