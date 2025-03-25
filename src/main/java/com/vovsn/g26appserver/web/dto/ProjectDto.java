package com.vovsn.g26appserver.web.dto;

import com.vovsn.g26appserver.persistance.model.Project;

import java.time.LocalDate;

public class ProjectDto {

    private final Long id;

    private final String name;

    private final LocalDate dateCreated;


    public ProjectDto(Long id, String name, LocalDate dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectDto that = (ProjectDto) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return dateCreated.equals(that.dateCreated);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + dateCreated.hashCode();
        return result;
    }

    public static ProjectDto fromProject(Project project) {
        return new ProjectDto(project.getId(), project.getName(), project.getDateCreated());
    }

}
