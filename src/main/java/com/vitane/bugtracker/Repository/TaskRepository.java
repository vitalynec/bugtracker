package com.vitane.bugtracker.Repository;

import com.vitane.bugtracker.Entity.Project;
import com.vitane.bugtracker.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

    Page<Task> findByProject(Project project, Pageable pageable);
    Task findTaskById(int id);
}
