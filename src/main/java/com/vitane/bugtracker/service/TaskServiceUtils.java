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
import java.util.stream.Collectors;

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

    List<Task> execute(Map<String, String> params) throws ParseException {
        initParameters(params);
        if ("none".equalsIgnoreCase(filter)) {
            taskList = taskService.findAll(page);
        } else {
            taskList = new ArrayList<>(getAllWithFilter());
        }
        taskList = new ArrayList<>(sortingTaskList());
        return taskList;
    }

    private void initParameters(Map<String, String> params) throws ParseException {
        this.page = Integer.parseInt(params.getOrDefault("page", "0"));
        this.sort = params.getOrDefault("sort", "none");
        this.filter = params.getOrDefault("filter", "none");
        this.statusType = Status.valueOf(params.getOrDefault("stype", "new").toUpperCase());
        this.priorityType = Integer.parseInt(params.getOrDefault("ptype", "0"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String datef = params.getOrDefault("datef", "01012000");
        String datet = params.getOrDefault("datet", "31122020");
        Instant instantFrom = dateFormat.parse(datef).toInstant();
        Instant instantTo = dateFormat.parse(datet).toInstant();
        ZoneId zone = ZoneId.systemDefault();
        this.dateFrom = LocalDateTime.ofInstant(instantFrom, zone);
        this.dateTo = LocalDateTime.ofInstant(instantTo, zone);
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
        sortCommands.put("date", () ->
                taskList.stream().sorted(Comparator.comparing(Task::getDateOfCreation)).collect(Collectors.toList()));
        sortCommands.put("priority", () ->
                taskList.stream().sorted(Comparator.comparingInt(Task::getPriority)).collect(Collectors.toList()));
        if (sortCommands.containsKey(sort.toLowerCase()))
            taskList = sortCommands.get(sort.toLowerCase()).execute();
        return taskList;
    }
}
