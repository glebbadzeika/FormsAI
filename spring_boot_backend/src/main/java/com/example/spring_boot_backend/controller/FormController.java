package com.example.spring_boot_backend.controller;


import com.example.spring_boot_backend.model.Answer;
import com.example.spring_boot_backend.model.Form;

import com.example.spring_boot_backend.service.FormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;


    @PostMapping
    public ResponseEntity<?> createForm(@RequestBody Form form) {
        try {
            Form f=formService.createForm(form);


            return ResponseEntity.ok(f);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Error you passed incorrect form json");


        }

    }

    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());


    }

    @GetMapping("/{id}/read")
    public ResponseEntity<?> getFormById(@PathVariable Long id) {
        Form form;
        try {
            form = formService.getFormWithoutAnswers(id);


        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No form with this id");
        }
        return ResponseEntity.ok(form);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody Form form) {
        Form res;
        try {
            res = formService.updateForm(id, form);
        } catch (Exception e) {
            if (e.getMessage().equals("Error: Form not found")){
                return ResponseEntity.badRequest().body("No form with this id");}
            else{
                return ResponseEntity.badRequest().body("Error you passed incorrect form json");}


        }
        return ResponseEntity.ok(res);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForm(@PathVariable Long id) {
        try{
            formService.deleteForm(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No form with this id");
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/answers")
    public ResponseEntity<?> getAllAnswers(@PathVariable Long id) {
        List<Answer> answers;
        try {
            answers = formService.getAllAnswers(id);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No form with this id");


        }
        return ResponseEntity.ok(answers);
    }


}






