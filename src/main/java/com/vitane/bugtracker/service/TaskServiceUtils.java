package com.vitane.bugtracker.service;

import com.vitane.bugtracker.entity.Status;
import com.vitane.bugtracker.entity.Task;
import com.vitane.bugtracker.service.command.Command;
import com.vitane.bugtracker.specification.TaskSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class TaskServiceUtils {

    private List<Task> taskList;
    private int page;
    private String sort;
    private String filter;
    private Status statusType;
    private int priorityType;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private final TaskService taskService;

    public TaskServiceUtils(TaskService taskService) {
        this.taskService = taskService;
    }

    List<Task> execute(Map<String, String> params) {
        initParameters(params);
        taskList = new ArrayList<>();
        if ("none".equalsIgnoreCase(filter)) {
            taskList = taskService.findAll(page);
        } else {
            taskList = getAllWithFilter();
        }
        taskList = sortingTaskList();
        return taskList;
    }

    private void initParameters(Map<String, String> params) {
        this.page = Integer.parseInt(params.getOrDefault("page", "0"));
        this.sort = params.getOrDefault("sort", "none");
        this.filter = params.getOrDefault("filter", "none");
        this.statusType = Status.valueOf(params.getOrDefault("stype", "new").toUpperCase());
        this.priorityType = Integer.parseInt(params.getOrDefault("ptype", "0"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String datef = params.getOrDefault("datef", "01012000");
            String datet = params.getOrDefault("datet", "31122020");
            Instant instantFrom = dateFormat.parse(datef).toInstant();
            Instant instantTo = dateFormat.parse(datet).toInstant();
            ZoneId zone = ZoneId.systemDefault();
            this.dateFrom = LocalDateTime.ofInstant(instantFrom, zone);
            this.dateTo = LocalDateTime.ofInstant(instantTo, zone);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Task> getAllWithFilter() {
        Map<String, Command> filterCommands = new HashMap<>();
        filterCommands.put("status", () ->
                taskService.findAll(TaskSpecification.getTasksByStatus(statusType), page));
        filterCommands.put("priority", () ->
                taskService.findAll(TaskSpecification.getTasksByPriority(priorityType), page));
        filterCommands.put("date", () -> {
            Specification<Task> specification = TaskSpecification.getTasksByDateTo(dateTo).
                    and(TaskSpecification.getTasksByDateFrom(dateFrom));
            return taskService.findAll(specification, page);
        });
        taskList = filterCommands.getOrDefault(filter.toLowerCase(), () -> taskService.findAll(page)).execute();

        return taskList;
    }

    private List<Task> sortingTaskList() {
        Map<String, Command> sortCommands = new HashMap<>();
        sortCommands.put("date", () -> {
            taskList.sort(Comparator.comparing(Task::getDateOfCreation));
            return taskList;
        });
        sortCommands.put("priority", () -> {
            taskList.sort(Comparator.comparingInt(Task::getPriority));
            return taskList;
        });
        if (sortCommands.containsKey(sort.toLowerCase()))
            taskList = sortCommands.get(sort.toLowerCase()).execute();

        return taskList;
    }
}
