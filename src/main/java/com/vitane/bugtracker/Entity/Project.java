package com.vitane.bugtracker.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    int id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime dateOfCreation;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime dateOfLastModification;
}
