package com.vitane.bugtracker.Specification;

import com.vitane.bugtracker.Entity.Status;
import com.vitane.bugtracker.Entity.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> getTasksByStatus(Status status) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
                return predicate;
            }
        };
    }

    public static Specification<Task> getTasksByPriority(int priority) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("priority"), priority);
                return predicate;
            }
        };
    }

    public static Specification<Task> getTasksByDateTo(LocalDateTime dateTo) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.lessThan(root.get("date_of_creation"), dateTo);
                return predicate;
            }
        };
    }
}
