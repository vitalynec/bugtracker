package com.vitane.bugtracker.Repository;

import com.vitane.bugtracker.Entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

    Project findProjectById(Integer id);
    Page<Project> findAll(Pageable pageable);
}
