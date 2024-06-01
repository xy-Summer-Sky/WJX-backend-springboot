package com.example.backend.DTO;

import java.io.Serializable;

/**
 * DTO for {@link com.example.backend.entity.Response}
 */

public record ResponseDto( Long questionId, String answerText) implements Serializable {

    public String getanswerText() {
        return answerText;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "questionId = " + questionId + ", " +
                "answerText = " + answerText + ")";
    }
}