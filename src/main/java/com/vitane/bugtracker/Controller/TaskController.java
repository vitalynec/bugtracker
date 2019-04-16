package com.vitane.bugtracker.Controller;

import com.vitane.bugtracker.Entity.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @RequestMapping("/tasks")
    public Task task() {
        return new Task();
    }
}
