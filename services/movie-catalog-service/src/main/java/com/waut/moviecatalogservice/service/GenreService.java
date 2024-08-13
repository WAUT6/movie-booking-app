package com.waut.moviecatalogservice.service;

import com.waut.moviecatalogservice.dto.GenreRequest;
import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.exception.ExceptionUtils;
import com.waut.moviecatalogservice.exception.GenreNotFoundException;
import com.waut.moviecatalogservice.model.Genre;
import com.waut.moviecatalogservice.repository.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public boolean validateGenreIds(List<String> genreIds) {
        for(String genreId : genreIds) {
//            Check if genreId is valid
            if(!genreRepository.existsById(genreId)) {
                throw new GenreNotFoundException("Genre with id " + genreId + " not found");
            }
        }

        return true;
    }

    public String createGenre(@Valid GenreRequest genreRequest) {
        Genre genre = genreMapper.toGenre(genreRequest);
        return ExceptionUtils.<Genre>saveItemOrThrowDuplicateKeyException(() -> genreRepository.save(genre).getId());
    }

    public List<GenreResponse> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toGenreResponse).toList();
    }

    public GenreResponse findById(String genreId) {
        return genreRepository.findById(genreId).map(genreMapper::toGenreResponse).orElseThrow(
                () -> new GenreNotFoundException("Genre with id " + genreId + " not found")
        );
    }

    public List<GenreResponse> findAllInByIds(List<String> genres) {
        return genreRepository.findAllById(genres).stream().map(genreMapper::toGenreResponse).toList();
    }

    public boolean deleteById(String genreId) {
        if(!genreRepository.existsById(genreId)) {
            throw new GenreNotFoundException("Genre with id " + genreId + " not found");
        }
        genreRepository.deleteById(genreId);
        return true;
    }

    public void updateById(String genreId, @Valid GenreRequest genreRequest) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new GenreNotFoundException("Genre with id " + genreId + " not found")
        );
        genreRepository.save(mergeGenre(genre, genreRequest));
    }

    private Genre mergeGenre(Genre genre, GenreRequest genreRequest) {
        if (StringUtils.isNotBlank(genreRequest.name())) {
            genre.setName(genreRequest.name());
        }
        if (StringUtils.isNotBlank(genreRequest.description())) {
            genre.setDescription(genreRequest.description());
        }
        if (StringUtils.isNotBlank(genreRequest.imageUrl())) {
            genre.setImageUrl(genreRequest.imageUrl());
        }
        return genre;
    }
}
