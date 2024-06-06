package com.example.backend.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SurveyDto implements Serializable {
    private Long id;
    private String title;
    private Integer createdBy;
    private String description;
    private Date createdAt;
    private String surveyState;
    private Integer receiveNumber;

    // Constructors
    public SurveyDto() {
    }

    public SurveyDto(Long id, String title, Integer createdBy, String description, Date createdAt, String surveyState, Integer receiveNumber) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.description = description;
        this.createdAt = createdAt;
        this.surveyState = surveyState;
        this.receiveNumber = receiveNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurveyState() {
        return surveyState;
    }

    public void setSurveyState(String surveyState) {
        this.surveyState = surveyState;
    }

    public Integer getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(Integer receiveNumber) {
        this.receiveNumber = receiveNumber;
    }
    // Equals, hashCode and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyDto entity = (SurveyDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.createdBy, entity.createdBy) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, createdBy, description);
    }

    @Override
    public String toString() {
        return "SurveyDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                '}';
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
