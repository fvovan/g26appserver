package com.vovsn.g26appserver.persistance.repository;

import com.vovsn.g26appserver.persistance.model.Project;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;


import java.util.Optional;

public interface ProjectRepository extends ListCrudRepository<Project, Long>, ListPagingAndSortingRepository<Project, Long> {

    Optional<Project> findById(long id);
}
