package com.vovsn.g26appserver.service;

import com.vovsn.g26appserver.persistance.model.Project;


import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(Long id);

    List<Project> findAll();

    List<Project> findAllCached();

    Project save(Project project);
}
