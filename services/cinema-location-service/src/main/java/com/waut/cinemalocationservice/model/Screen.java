package com.waut.cinemalocationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "cinema_screens", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cinema_id", "screen_number"})
})
@EntityListeners(AuditingEntityListener.class)
public class Screen {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    @JsonBackReference
    private Cinema cinema;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "screen")
    @JsonManagedReference
    private List<Seat> seats;
    private String screenNumber;
    private Integer capacity;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
