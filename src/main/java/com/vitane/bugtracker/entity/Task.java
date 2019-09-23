package com.vitane.bugtracker.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonManagedReference
    private Project project;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private int priority;

    @Column(nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime dateOfCreation;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime dateOfLastModification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}