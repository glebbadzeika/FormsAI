package com.example.spring_boot_backend.service;

import com.example.spring_boot_backend.model.Answer;
import com.example.spring_boot_backend.repository.AnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;




    public Answer createAnswer(Answer answer) {
        Answer a=new Answer();
        a.setAnswer_text(answer.getAnswer_text());
        a.setQuestion_id(answer.getQuestion_id());

        return answerRepository.save(a);

    }
}
