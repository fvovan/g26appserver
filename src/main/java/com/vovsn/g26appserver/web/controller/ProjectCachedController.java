package com.vovsn.g26appserver.web.controller;

import com.vovsn.g26appserver.persistance.model.Project;
import com.vovsn.g26appserver.service.ProjectService;
import com.vovsn.g26appserver.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects-cached")
public class ProjectCachedController {

    private final ProjectService projectService;

    @Autowired
    public ProjectCachedController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    public List<ProjectDto> getAllCached() {
        List<Project> projects = projectService.findAllCached();

        return projects.stream().map(ProjectDto::fromProject)
                .collect(Collectors.toList());
    }
}
