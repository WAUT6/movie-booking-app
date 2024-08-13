package com.waut.moviecatalogservice.service;

import com.waut.moviecatalogservice.dto.GenreRequest;
import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.model.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper {

    public GenreResponse toGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .imageUrl(genre.getImageUrl())
                .createdAt(genre.getCreatedAt())
                .updatedAt(genre.getUpdatedAt())
                .build();
    }

    public Genre toGenre(GenreRequest genreRequest) {
        return Genre.builder()
                .name(genreRequest.name())
                .description(genreRequest.description())
                .imageUrl(genreRequest.imageUrl())
                .build();
    }

}
