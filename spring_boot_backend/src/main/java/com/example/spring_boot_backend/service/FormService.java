package com.example.spring_boot_backend.service;


import com.example.spring_boot_backend.model.Answer;
import com.example.spring_boot_backend.model.Form;
import com.example.spring_boot_backend.model.Question;
import com.example.spring_boot_backend.model.Section;
import com.example.spring_boot_backend.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private SectionService sectionService;






    public List<Form> getAllForms() {

        return formRepository.findAll();
    }

    public Form createForm(Form form){

        Form f = new Form();
        try{
        f.setTitle(form.getTitle());
        f.setCollect_email(form.getCollect_email());
        List<Section> sections=new ArrayList<>();

        for (Section s:form.getSections()
             ) {
            sections.add(sectionService.createSection(s));



        }
            f.setSections(sections);
        }catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }



        return formRepository.save(f);
    }

    public Form getFormWithoutAnswers(Long id){
        if(!formRepository.existsById(id)){
            throw new RuntimeException("Error: Form not found");
        }



        //noinspection OptionalGetWithoutIsPresent
        Form f = formRepository.findById(id).get();

        for (Section s:f.getSections()
        ) {
            for (Question q:s.getQuestions()
            ) {
                q.setAnswers(new ArrayList<>());

            }

        }
        return f;

    }


    public Form updateForm(Long id, Form form) {
        if(!formRepository.existsById(id)){
            throw new RuntimeException("Error: Form not found");
        }


        //noinspection OptionalGetWithoutIsPresent
        Form f = formRepository.findById(id).get();
        try{
        f.setTitle(form.getTitle());
        f.setCollect_email(form.getCollect_email());
        List<Section> sections=new ArrayList<>();
        for (Section s:form.getSections()
        ) {
            sections.add(sectionService.createSection(s));

    }
        f.setSections(sections);}
        catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }
        return formRepository.save(f);
    }



    public void deleteForm(Long id) {
        if(!formRepository.existsById(id)){
            throw new RuntimeException("Error: Form not found");
        }

        formRepository.deleteById(id);
    }


    public List<Answer> getAllAnswers(Long id) {
        if(!formRepository.existsById(id)){
            throw new RuntimeException("Error: Form not found");
        }


        //noinspection OptionalGetWithoutIsPresent
        Form f = formRepository.findById(id).get();
        List<Answer> answers=new ArrayList<>();
        for (Section s:f.getSections()
             ) {
            for (Question q:s.getQuestions()
                 ) {
                answers.addAll(q.getAnswers());

            }

        }
        return answers;
    }

//    public Form getFormById(Long id) {
//        if (!formRepository.existsById(id)) {
//            throw new RuntimeException("Error: Form not found");
//        }
//        //noinspection OptionalGetWithoutIsPresent
//        return formRepository.findById(id).get();
//
//    }


}
