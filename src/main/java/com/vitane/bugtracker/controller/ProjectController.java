package com.vitane.bugtracker.controller;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.entity.Task;
import com.vitane.bugtracker.exception.NotFoundException;
import com.vitane.bugtracker.service.ProjectService;
import com.vitane.bugtracker.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public final class ProjectController {

    private ProjectService projectService;
    private TaskService taskService;

    ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false, value = "page", defaultValue = "0") int page) {
        List<Project> projectList = projectService.findAll(page);
        if (projectList == null) {
            return ResponseEntity.badRequest().build();
        }
        if (projectList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(projectList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Project> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(projectService.findProjectById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/{id}/edit", "/{id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteById(@PathVariable int id) {
        return deleteProject(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteByProject(@RequestBody Project project) {
        return deleteProject(project.getId());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createProject(@RequestBody Project project) {
        projectService.save(project);
        return ResponseEntity.created(URI.create("/projects/" + project.getId())).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = {"/{id}/edit", "/{id}"},
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity putProject(@PathVariable int id, @RequestBody Project project) {
        if (projectService.changeProject(id, project)) {
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/tasks")
    public ResponseEntity<List<Task>> getTaskListByProjectId(@PathVariable int id, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        List<Task> taskList;
        try {
            taskList = taskService.findByProject(projectService.findProjectById(id), page);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        if (taskList == null || taskList.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(taskList);
        }
    }

    private ResponseEntity deleteProject(int id) {
        if (projectService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }
}
