package com.vitane.bugtracker.repository;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    Page<Task> findByProject(Project project, Pageable pageable);
    Page<Task> findAllByOrderByPriority(Pageable pageable);
    Page<Task> findAllByOrderByDateOfCreation(Pageable pageable);
    Task findTaskById(int id);
}
