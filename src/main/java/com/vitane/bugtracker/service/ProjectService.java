package com.vitane.bugtracker.service;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.exception.NotFoundException;
import com.vitane.bugtracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public Project findProjectById(int id) throws NotFoundException {
        if (existsById(id)) {
            return projectRepository.findProjectById(id);
        } else throw new NotFoundException(String.format("Project with id %d is not found!", id));
    }

    private boolean existsById(int id) {
        return projectRepository.existsById(id);
    }

    public boolean deleteById(int id) {
        if (existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    public boolean changeProject(int id, Project project) {
        if (existsById(id)) {
            project.setId(id);
            save(project);
            return true;
        } else
            return false;
    }

    public void save(Project project) {
        projectRepository.save(project);
    }
}
