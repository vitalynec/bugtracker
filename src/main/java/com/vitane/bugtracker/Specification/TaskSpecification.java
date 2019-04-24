package com.vitane.bugtracker.Specification;

import com.vitane.bugtracker.Entity.Status;
import com.vitane.bugtracker.Entity.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> getTasksByStatus(Status status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
            return predicate;
        };
    }

    public static Specification<Task> getTasksByPriority(int priority) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("priority"), priority);
            return predicate;
        };
    }

    public static Specification<Task> getTasksByDateTo(LocalDateTime dateTo) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("dateOfCreation"), dateTo);
            return predicate;
        };
    }

    public static Specification<Task> getTasksByDateFrom(LocalDateTime dateFrom) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfCreation"), dateFrom);
            return predicate;
        };
    }
}
