package com.vovsn.g26appserver.service.impl;

import com.vovsn.g26appserver.persistance.model.Project;
import com.vovsn.g26appserver.persistance.repository.ProjectRepository;
import com.vovsn.g26appserver.service.ProjectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {
        if (Objects.isNull(project.getId())) {
            project.setDateCreated(LocalDate.now());
        }
        return projectRepository.save(project);
    }

    @Override
    @Cacheable(value = "projectListCache")
    public List<Project> findAllCached() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
