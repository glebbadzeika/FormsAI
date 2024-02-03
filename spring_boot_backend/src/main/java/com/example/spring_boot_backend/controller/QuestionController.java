package com.example.spring_boot_backend.controller;

import com.example.spring_boot_backend.model.Answer;
import com.example.spring_boot_backend.model.Question;
import com.example.spring_boot_backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @PostMapping("/{id}/answers")
    public ResponseEntity<?> answerQuestion(@PathVariable Long id, @RequestBody Answer answer) {
        Question q;
        try{
        if (!Objects.equals(answer.getQuestion_id(), id)) {
            return ResponseEntity.badRequest().body("Error: Question id in url and answer body do not match");
        }
        q = questionService.answerQuestion(id, answer);}
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.ok(q);
    }

    @PutMapping("/{id}/questions")
    public ResponseEntity<?> batchUpdateQuestions(@PathVariable Long id, @RequestBody Question q) {
        Question res;
        try{
        res=questionService.batchUpdateQuestions(id, q);}
        catch (Exception e){
            if (e.getMessage().equals("Error: Question not found")){
                return ResponseEntity.badRequest().body("No question with this id");}
            else{
                return ResponseEntity.badRequest().body("Error you passed incorrect question json");}

        }
        return ResponseEntity.ok(res);
    }
}
