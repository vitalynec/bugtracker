package com.vitane.bugtracker.Repository;

import com.vitane.bugtracker.Entity.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

    Project findProjectById(Integer id);
    List<Project> findAll();
}
