package com.example.spring_boot_backend.service;

import com.example.spring_boot_backend.model.Question;
import com.example.spring_boot_backend.model.Section;
import com.example.spring_boot_backend.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionService questionService;
    public Section createSection(Section section){
        Section s = new Section();
        try {
        s.setTitle(section.getTitle());
        s.setDescription(section.getDescription());

        List<Question> questions=new ArrayList<>();

            for (Question q:section.getQuestions()
            ) {
                questions.add(questionService.createQuestion(q));



            }
            s.setQuestions(questions);


        } catch (Exception e) {
            throw new RuntimeException("Error: "+e.getMessage());
        }


        return sectionRepository.save(s);
    }

}
