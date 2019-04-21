package com.vitane.bugtracker.Controller;

import com.vitane.bugtracker.Entity.Project;
import com.vitane.bugtracker.Entity.Task;
import com.vitane.bugtracker.Repository.ProjectRepository;
import com.vitane.bugtracker.Repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public final class ProjectController {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    ProjectController(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

// TODO: mapping listed-get-request
// @RequestMapping(method = RequestMethod.GET)
// public ResponseEntity<List<Project>> getListOfProjects(@RequestParam(required = false, value = "id", defaultValue = "0") int[] id)

// TODO: mapping head-requests
// @RequestMapping(method = RequestMethod.HEAD)

    //  REST API OK
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false, value = "page", defaultValue = "0") int page) {
        List<Project> projectList = projectRepository.findAll(PageRequest.of(page, 10)).getContent();
        if (projectList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(projectList);
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Project> getById(@PathVariable int id) {
        if (projectRepository.existsById(id))
            return ResponseEntity.ok(projectRepository.findProjectById(id));
        else
            return ResponseEntity.notFound().build();
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.DELETE, value = {"/{id}/edit", "/{id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteById(@PathVariable int id) {
        return deleteProject(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteByProject(@RequestBody(required = false) Project project) {
        return deleteProject(project.getId());
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createProject(@RequestBody Project project) {
        projectRepository.save(project);
        return ResponseEntity.created(URI.create("/projects/" + project.getId())).build();
    }

    //  REST API OK
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
        List<Task> taskList = taskRepository.findByProject(
                projectRepository.findProjectById(id),
                PageRequest.of(page, 10)).getContent();
        if (taskList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(taskList);
    }

    private ResponseEntity deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    private ResponseEntity changeProject(int id, Project project) {
        if (projectRepository.existsById(id)) {
            project.setId(id);
            projectRepository.save(project);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }
}
