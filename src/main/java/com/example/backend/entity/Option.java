package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="options")
public class Option implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column options.id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    @Id
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column options.option_text
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    private String optionText;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column options.question_id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    private Long questionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table options
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    private static final long serialVersionUID = 1L;

    public Option() {

    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column options.id
     *
     * @return the value of options.id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column options.id
     *
     * @param id the value for options.id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column options.option_text
     *
     * @return the value of options.option_text
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public String getOptionText() {
        return optionText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column options.option_text
     *
     * @param optionText the value for options.option_text
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public void setOptionText(String optionText) {
        this.optionText = optionText == null ? null : optionText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column options.question_id
     *
     * @return the value of options.question_id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column options.question_id
     *
     * @param questionId the value for options.question_id
     *
     * @mbg.generated Sun May 26 01:02:30 CST 2024
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Option(Long id, String text, Long questionId) {
        this.id = id;
        this.optionText = text;
        this.questionId = questionId;
    }
}