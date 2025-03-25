package com.vovsn.g26appserver.persistance.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dateCreated;


    public Project(String name, LocalDate dateCreated) {
        this.name = name;
        this.dateCreated = dateCreated;
    }

    protected Project() {
    }

    public Project(Project project) {
        this(project.getName(), project.getDateCreated());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!Objects.equals(id, project.id)) return false;
        if (!Objects.equals(name, project.name)) return false;
        return Objects.equals(dateCreated, project.dateCreated);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}

