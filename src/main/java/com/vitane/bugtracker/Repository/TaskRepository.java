package com.vitane.bugtracker.Repository;

import com.vitane.bugtracker.Entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {
}
