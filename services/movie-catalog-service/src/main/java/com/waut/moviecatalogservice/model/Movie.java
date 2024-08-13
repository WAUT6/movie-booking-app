package com.waut.moviecatalogservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "movies")
@EnableMongoAuditing
@CompoundIndex(def = "{'title': 1, 'releaseDate': 1}", unique = true)
public class Movie {
    @Id
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Integer durationMinutes;
    private Instant releaseDate;
    private List<String> genres;
    private String director;
    private List<String> cast;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
