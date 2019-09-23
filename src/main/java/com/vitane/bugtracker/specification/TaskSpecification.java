package com.vitane.bugtracker.specification;

import com.vitane.bugtracker.entity.Status;
import com.vitane.bugtracker.entity.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> getTasksByStatus(Status status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> getTasksByPriority(int priority) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> getTasksByDateTo(LocalDateTime dateTo) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("dateOfCreation"), dateTo);
    }

    public static Specification<Task> getTasksByDateFrom(LocalDateTime dateFrom) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfCreation"), dateFrom);
    }
}
