package com.vitane.bugtracker.Controller;

import com.vitane.bugtracker.Entity.Status;
import com.vitane.bugtracker.Entity.Task;
import com.vitane.bugtracker.Specification.TaskSpecification;
import com.vitane.bugtracker.Repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //  REST API OK
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam Map<String, String> params) {
        List<Task> taskList;

        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        String sort = params.getOrDefault("sort", "none");
        switch (sort.toLowerCase()) {
            case "priority": {
//                taskList = taskRepository.findAll( new PageRequest(page, 10, Sort.by("priority").ascending())).getContent();
                taskList = taskRepository.findAllByOrderByPriority(PageRequest.of(page, 10)).getContent();
                break;
            }
            case "date": {
                taskList = taskRepository.findAllByOrderByDateOfCreation(PageRequest.of(page, 10)).getContent();
                break;
            }
            default: {
                // GET param like /?filter=priority&ptype=0 or /?filter=status&stype=in_progress
                String filter = params.getOrDefault("filter", "none");
                switch (filter.toLowerCase()) {
                    case "status": {
                        Status statusType = Status.valueOf(params.getOrDefault("stype", "new").toUpperCase());
                        taskList = taskRepository.findAll(TaskSpecification.getTasksByStatus(statusType),
                                PageRequest.of(page, 10)).getContent();
                        break;
                    }
                    case "priority": {
                        int priorityType = Integer.parseInt(params.getOrDefault("ptype", "0"));
                        taskList = taskRepository.findAll(TaskSpecification.getTasksByPriority(priorityType),
                                PageRequest.of(page, 10)).getContent();
                        break;
                    }
                    case "date": {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                        String datef = params.getOrDefault("datef", "01-01-2000");
                        String datet = params.getOrDefault("datet", "31122020");
                        try {
                            LocalDateTime dateFrom = LocalDateTime.ofInstant(dateFormat.parse(datef).toInstant(),
                                    ZoneId.systemDefault());
                            LocalDateTime dateTo = LocalDateTime.ofInstant(dateFormat.parse(datet).toInstant(),
                                    ZoneId.systemDefault());
                            taskList = taskRepository.findAll(TaskSpecification.getTasksByDateTo(dateTo).
                                            and(TaskSpecification.getTasksByDateFrom(dateFrom)),
                                    PageRequest.of(page, 10)).getContent();
                        } catch (ParseException e) {
                            System.err.println(e.getMessage());
                            return ResponseEntity.badRequest().build();
                        }
                        break;
                    }
                    default: {
                        taskList = taskRepository.findAll(PageRequest.of(page, 10)).getContent();
                        break;
                    }
                }
            }
        }
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


