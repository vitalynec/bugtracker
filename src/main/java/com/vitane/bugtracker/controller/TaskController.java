package com.vitane.bugtracker.controller;

import com.vitane.bugtracker.entity.Task;
import com.vitane.bugtracker.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam Map<String, String> params) {
        List<Task> taskList = taskService.findAll(params);
        if (taskList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(taskList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Task> getById(@PathVariable int id) {
        if (taskService.existsById(id))
            return ResponseEntity.ok(taskService.findById(id));
        else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/{id}/edit", "/{id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteById(@PathVariable int id) {
        if (taskService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTask(@RequestBody Task task) {
        if (task.getPriority() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        taskService.saveNewTask(task);
        return ResponseEntity.created(URI.create("/tasks/" + task.getId())).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = {"/{id}/edit", "/{id}"},
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Task> putTask(@PathVariable int id, @RequestBody Task task) {
        if (taskService.isTaskAvaliable(id)) {
            if (taskService.putTask(id, task)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}


