package com.vitane.bugtracker.Controller;

import com.vitane.bugtracker.Entity.Project;
import com.vitane.bugtracker.Repository.ProjectRepository;
import lombok.NonNull;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/projects")
public final class ProjectController {

    @NonNull
    private JdbcOperations jdbcOperations;
    private ProjectRepository projectRepository;

    ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectList(@PathVariable int id) {
        return ResponseEntity.ok(projectRepository.findProjectById(id));
    }

/*    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> project(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.jdbcOperations.queryForObject("select * from project where id = ? limit 1",
                    ((resultSet, i) ->
                            new Project(resultSet.getString("name"),
                                    resultSet.getString("description"),
                                    resultSet.getTimestamp("dateOfCreation").toLocalDateTime(),
                                    resultSet.getTimestamp("dateOfModification").toLocalDateTime())
                    )));
        } catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    /*    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProjectList() {
        try {
            return ResponseEntity.ok(this.jdbcOperations.query("select * from project", ((resultSet, i) ->
                    new Project(resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getTimestamp("dateOfCreation").toLocalDateTime(),
                            resultSet.getTimestamp("dateOfModification").toLocalDateTime())
            )));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
