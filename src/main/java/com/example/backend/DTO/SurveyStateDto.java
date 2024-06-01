package com.example.backend.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.example.backend.entity.SurveyState}
 */
public class SurveyStateDto implements Serializable {
    private final Long surveyId;
    private final Integer receivenumber;
    private final String state;

    public SurveyStateDto(Long surveyId, Integer receivenumber, String state) {
        this.surveyId = surveyId;
        this.receivenumber = receivenumber;
        this.state = state;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public Integer getReceivenumber() {
        return receivenumber;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyStateDto entity = (SurveyStateDto) o;
        return Objects.equals(this.surveyId, entity.surveyId) &&
                Objects.equals(this.receivenumber, entity.receivenumber) &&
                Objects.equals(this.state, entity.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, receivenumber, state);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "surveyId = " + surveyId + ", " +
                "receivenumber = " + receivenumber + ", " +
                "state = " + state + ")";
    }
}