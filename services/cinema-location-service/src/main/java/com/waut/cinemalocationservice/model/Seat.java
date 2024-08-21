package com.waut.cinemalocationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "screen_seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"screen_id", "rowNumber", "columnNumber"})
})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "id")
    @JsonBackReference
    private Screen screen;
    private int rowNumber;
    private int columnNumber;
    @Column(columnDefinition = "boolean default true")
    private boolean isAvailable;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
