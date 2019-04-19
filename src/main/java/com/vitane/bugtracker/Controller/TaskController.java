package com.vitane.bugtracker.Controller;

import com.vitane.bugtracker.Entity.Status;
import com.vitane.bugtracker.Entity.Task;
import com.vitane.bugtracker.Repository.ProjectRepository;
import com.vitane.bugtracker.Repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    TaskController(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                  @RequestParam(required = false, value = "sortByPrority", defaultValue = "false") boolean sortByPrority,
                                                  @RequestParam(required = false, value = "sortByDate", defaultValue = "false") boolean sortByDate) {
        List<Task> taskList;
        if (sortByPrority) {
            PageRequest sortedByPriorityDesc = new PageRequest(page, 10, Sort.by("priority").descending());
            taskList = taskRepository.findAll(sortedByPriorityDesc).getContent();
        } else if (sortByDate) {
            PageRequest sortedByDateDesc = new PageRequest(page, 10, Sort.by("dateOfCreation").descending());
            taskList = taskRepository.findAll(sortedByDateDesc).getContent();
        } else
            taskList = taskRepository.findAll(PageRequest.of(page, 10)).getContent();

        return responseTaskList(taskList);
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Task> getById(@PathVariable int id) {
        if (taskRepository.existsById(id))
            return ResponseEntity.ok(taskRepository.findTaskById(id));
        else
            return ResponseEntity.notFound().build();
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.DELETE, value = {"/{id}/edit", "/{id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteById(@PathVariable int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTask(@RequestBody Task task) {
        if (task.getPriority() <= 0)
            return ResponseEntity.badRequest().build();
        task.setStatus(Status.NEW);
        taskRepository.save(task);
        return ResponseEntity.created(URI.create("/tasks/" + task.getId())).build();
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.PUT, value = {"/{id}/edit", "/{id}"},
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Task> putTask(@PathVariable int id, @RequestBody Task task) {
        if (taskRepository.existsById(id)) {
            Status status = taskRepository.findTaskById(task.getId()).getStatus();
            if (status == Status.NEW || status == Status.IN_PROGRESS) {
                task.setId(id);
                taskRepository.save(task);
                return ResponseEntity.noContent().build();
            } else return ResponseEntity.badRequest().build();
        } else
            return ResponseEntity.notFound().build();
    }

    private ResponseEntity<List<Task>> responseTaskList(List<Task> taskList) {
        if (taskList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(taskList);
    }
}
