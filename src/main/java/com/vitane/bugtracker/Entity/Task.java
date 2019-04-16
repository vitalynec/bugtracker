package com.vitane.bugtracker.Entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Project project;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private int priority;

    @Column(nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private Calendar dateOfCreation;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private Calendar dateOfLastModification;

    @Column(nullable = false)
    private Status status;
}

enum Status {
    NEW,
    IN_PROGRESS,
    CLOSE
}
