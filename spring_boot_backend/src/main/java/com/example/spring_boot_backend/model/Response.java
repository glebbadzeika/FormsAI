package com.example.spring_boot_backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Form response_to;

    @OneToMany
    private List<Answer> response_from_answers;

    public Form getResponse_to() {
        return response_to;
    }

    public void setResponse_to(Form response_to) {
        this.response_to = response_to;
    }

    public List<Answer> getResponse_from_answers() {
        return response_from_answers;
    }

    public void setResponse_from_answers(List<Answer> response_from) {
        this.response_from_answers = response_from;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
