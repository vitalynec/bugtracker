package com.vitane.bugtracker.service;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    private void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll(int page) {
        return projectRepository.findAll(PageRequest.of(page, 10)).getContent();
    }

    public Project findProjectById(int id) {
        return projectRepository.findProjectById(id);
    }

    public boolean existsById(int id) {
        return projectRepository.existsById(id);
    }

    public boolean deleteById(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    public void save(Project project) {
        projectRepository.save(project);
    }
}
