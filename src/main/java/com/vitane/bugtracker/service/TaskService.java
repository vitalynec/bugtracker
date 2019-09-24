package com.vitane.bugtracker.service;

import com.vitane.bugtracker.entity.Project;
import com.vitane.bugtracker.entity.Status;
import com.vitane.bugtracker.entity.Task;
import com.vitane.bugtracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskServiceUtils taskServiceUtils;

    public List<Task> findAll(Map<String, String> params) {
        return taskServiceUtils.execute(params);
    }

    public List<Task> findAllByOrderByDateOfCreation(int page) {
        return taskRepository.findAllByOrderByDateOfCreation(PageRequest.of(page, 10)).getContent();
    }

    public List<Task> findAllByOrderByPriority(int page) {
        return taskRepository.findAllByOrderByPriority(PageRequest.of(page, 10)).getContent();
    }

    public List<Task> findAll(Specification<Task> specification, int page) {
        return taskRepository.findAll(specification, PageRequest.of(page, 10)).getContent();
    }

    public List<Task> findAll(int page) {
        return taskRepository.findAll(PageRequest.of(page, 10)).getContent();
    }

    public List<Task> findByProject(Project project, int page) {
        return taskRepository.findByProject(project,
                PageRequest.of(page, 10)).getContent();
    }

    public boolean putTask(int id, Task task) {
        boolean flagResult = false;
        if (isTaskAvaliable(id)) {
            task.setId(id);
            save(task);
            flagResult = true;
        }
        return flagResult;
    }

    public boolean isTaskAvaliable(int id) {
        boolean flagResult = false;
        if (existsById(id)) {
            Status status = findById(id).getStatus();
            flagResult = (status == Status.NEW) || (status == Status.IN_PROGRESS);
        }
        return flagResult;
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void saveNewTask(Task task) {
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

    public Task findById(int id) {
        return taskRepository.findTaskById(id);
    }

    public boolean existsById(int id) {
        return taskRepository.existsById(id);
    }
}
