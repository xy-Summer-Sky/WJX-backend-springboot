package com.example.backend.DTO;

import java.io.Serializable;

/**
 * DTO for {@link com.example.backend.entity.Response}
 */

public record ResponseDto(Long id, Long questionId, String answerText) implements Serializable {
    public ResponseDto(Long id, Long questionId, String answerText) {
        this.id = id;
        this.questionId = questionId;
        this.answerText = String.valueOf(answerText);

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "questionId = " + questionId + ", " +
                "answerText = " + answerText + ")";
    }
}