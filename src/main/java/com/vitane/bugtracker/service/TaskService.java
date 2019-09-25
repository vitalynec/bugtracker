package com.vitane.bugtracker.service;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.entity.Status;
import com.vitane.bugtracker.entity.Task;
import com.vitane.bugtracker.exception.NotFoundException;
import com.vitane.bugtracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskServiceUtils taskServiceUtils = new TaskServiceUtils(this);
    private final int PAGE_SIZE = 10;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(Map<String, String> params) throws ParseException {
        return taskServiceUtils.execute(params);
    }

//    public List<Task> findAllByOrderByDateOfCreation(int page) {
//        return taskRepository.findAllByOrderByDateOfCreation(PageRequest.of(page, 10)).getContent();
//    }
//
//    public List<Task> findAllByOrderByPriority(int page) {
//        return taskRepository.findAllByOrderByPriority(PageRequest.of(page, 10)).getContent();
//    }

    public List<Task> findAll(Specification<Task> specification, int page) {
        return taskRepository.findAll(specification, PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    public List<Task> findAll(int page) {
        return taskRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    public List<Task> findByProject(Project project, int page) {
        return taskRepository.findByProject(project,
                PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    public boolean putTask(int id, Task task) throws NotFoundException, IllegalArgumentException {
        boolean flagResult;
        Status status = findById(id).getStatus();
        flagResult = (status == Status.NEW) || (status == Status.IN_PROGRESS);
        if (flagResult) {
            task.setId(id);
            save(task);
            flagResult = true;
        }
        return flagResult;
    }

    private void save(Task task) throws IllegalArgumentException {
        if (task == null) throw new IllegalArgumentException("Task is null!");
        if (task.getProject() == null) throw new IllegalArgumentException("Project is null!");
        if (task.getStatus() == null) throw new IllegalArgumentException("Status is null!");
        taskRepository.save(task);
    }

    public void addNewTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Task is null!");
        task.setStatus(Status.NEW);
        save(task);
    }

    public boolean deleteById(int id) {
        if (existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    public Task findById(int id) throws NotFoundException {
        if (existsById(id)) {
            return taskRepository.findTaskById(id);
        } else {
            throw new NotFoundException(String.format("Task with id %d is not found!", id));
        }
    }

    private boolean existsById(int id) {
        return taskRepository.existsById(id);
    }
}
