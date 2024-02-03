package com.example.spring_boot_backend.model;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
//    private Question question_answered;

    private Long question_id;

    private String answer_text;

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

//    public Question getQuestion_answered() {
//        return question_answered;
//    }
//
//    public void setQuestion_answered(Question answer_to_question) {
//        this.question_answered = answer_to_question;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public Long getId() {
        return id;
    }

//    public Long getQuestion_id() {
//        return question_answered.getId();
//    }
}
