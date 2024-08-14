package com.waut.cinemalocationservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cinemas")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema")
    @JsonManagedReference
    private List<PhoneNumber> phoneNumbers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema")
    @JsonManagedReference
    private List<Screen> screens;
    @ElementCollection
    private List<String> currentMoviesNowShowing = List.of();
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
