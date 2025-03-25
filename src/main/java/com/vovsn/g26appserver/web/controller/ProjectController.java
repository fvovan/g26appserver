package com.vovsn.g26appserver.web.controller;

import com.vovsn.g26appserver.persistance.model.Project;
import com.vovsn.g26appserver.service.ProjectService;
import com.vovsn.g26appserver.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/{id}")
    public ProjectDto findOne(@PathVariable Long id) {
        Optional<Project> maybeProject = projectService.findById(id);
        return maybeProject.map(ProjectDto::fromProject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }


    @GetMapping
    public List<ProjectDto> getAll() {
        List<Project> projects = projectService.findAll();

        return projects.stream().map(ProjectDto::fromProject)
                .collect(Collectors.toList());
    }
}
