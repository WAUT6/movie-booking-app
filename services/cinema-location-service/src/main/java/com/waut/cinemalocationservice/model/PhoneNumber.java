package com.waut.cinemalocationservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "cinema_phone_numbers")
@EntityListeners(AuditingEntityListener.class)
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    private Cinema cinema;
    @Column(unique = true)
    private String phoneNumber;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
