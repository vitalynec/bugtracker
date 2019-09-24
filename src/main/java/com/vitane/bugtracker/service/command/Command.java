package com.vitane.bugtracker.service.command;

import com.vitane.bugtracker.entity.Task;

import java.util.List;

public interface Command {
    List<Task> execute();
}
