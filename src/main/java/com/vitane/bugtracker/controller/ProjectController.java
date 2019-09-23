package com.vitane.bugtracker.controller;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.entity.Task;
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

// TODO: mapping listed-get-request
// @RequestMapping(method = RequestMethod.GET)
// public ResponseEntity<List<Project>> getListOfProjects(@RequestParam(required = false, value = "id", defaultValue = "0") int[] id)

// TODO: mapping head-requests
// @RequestMapping(method = RequestMethod.HEAD)

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false, value = "page", defaultValue = "0") int page) {
        List<Project> projectList = projectService.findAll(page);
        if (projectList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(projectList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Project> getById(@PathVariable int id) {
        if (projectService.existsById(id))
            return ResponseEntity.ok(projectService.findProjectById(id));
        else
            return ResponseEntity.notFound().build();
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
        return changeProject(id, project);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/{id}/edit", "/{id}"},
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patchProject(@PathVariable int id, @RequestBody Project project) {
        return changeProject(id, project);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/tasks")
    public ResponseEntity<List<Task>> getTaskListByProjectId(@PathVariable int id, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        List<Task> taskList = taskService.findByProject(projectService.findProjectById(id), page);
        if (taskList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(taskList);
    }

    private ResponseEntity deleteProject(int id) {
        if (projectService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    private ResponseEntity changeProject(int id, Project project) {
        if (projectService.existsById(id)) {
            project.setId(id);
            projectService.save(project);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }
}
