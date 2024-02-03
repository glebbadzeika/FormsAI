package com.example.spring_boot_backend.service;

import com.example.spring_boot_backend.model.Answer;

import com.example.spring_boot_backend.model.Option;
import com.example.spring_boot_backend.model.Question;
import com.example.spring_boot_backend.model.QuestionType;
import com.example.spring_boot_backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionService choiceService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private OpenAIService openAIService;
    @Autowired
    private OpenAIResponseParserService openAIResponseParserService;


    public Question createQuestion(Question question) {
        Question q = new Question();
        try{
        q.setQuestion_text(question.getQuestion_text());
        q.setIs_required(question.getIs_required());
        q.setQuestion_type(question.getQuestion_type());
        List<Option> options =new ArrayList<>();

        for (Option c: question.getOptions()
             ) {
            options.add(choiceService.createChoice(c));

        }
        q.setOptions(options);}
        catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }
        return questionRepository.save(q);
    }

    public Question answerQuestion(Long id, Answer answer) throws RuntimeException {
        Answer ans = answerService.createAnswer(answer);
        if(!questionRepository.existsById(id)){
            throw new RuntimeException("Error: Question not found");
        }


        //noinspection OptionalGetWithoutIsPresent
        Question q=questionRepository.findById(id).get();

        if(q.getQuestion_type() == QuestionType.CLOSED_QUESTION){
            List<String> option_text=q.getOptions().stream().map(Option::getOption_text).toList();
            if(option_text.contains(ans.getAnswer_text())){
                q.getAnswers().add(ans);
                return questionRepository.save(q);

            }
            else{
                throw new RuntimeException("Error: Answer text does not match any option");
            }
        }




        List<String> option_text=q.getOptions().stream().map(Option::getOption_text).toList();
        if(option_text.contains(ans.getAnswer_text()) || option_text.contains(ans.getAnswer_text().toLowerCase()) || option_text.contains(ans.getAnswer_text().toUpperCase())
        || q.getQuestion_type()==QuestionType.TEXT_QUESTION){
            q.getAnswers().add(ans);
            return questionRepository.save(q);

        }
        try{
        String transformedAnswer=openAIService.getChatResponse(option_text,ans.getAnswer_text(),q.getQuestion_text());
            System.out.println(transformedAnswer);
            transformedAnswer= openAIResponseParserService.getAnswerFromResponse(transformedAnswer);
            String[] parts = transformedAnswer.split("\"");

            if (parts.length == 2 || parts.length == 4 || (parts.length == 3 && parts[2].equals("."))) {
                transformedAnswer = parts[1];
            }
            if (parts.length >= 4) {
                transformedAnswer = parts[3] ;
            }
            parts = transformedAnswer.split(":");
            if (parts.length == 2) {
                transformedAnswer = parts[1];
            }
            ans.setAnswer_text(transformedAnswer);
            q.getAnswers().add(ans);
            if(!option_text.contains(transformedAnswer)){
                Option opt=new Option();
                opt.setOption_text(transformedAnswer);
                q.getOptions().add(choiceService.createChoice(opt));
            }
        }catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }

        return questionRepository.save(q);


    }

    public Question batchUpdateQuestions(Long id, Question q) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Error: Question not found");
        }
        Question question;
        try{
            //noinspection OptionalGetWithoutIsPresent
            question=questionRepository.findById(id).get();
        question.setQuestion_text(q.getQuestion_text());
        question.setIs_required(q.getIs_required());
        question.setQuestion_type(q.getQuestion_type());
        List<Option> options =new ArrayList<>();

        for (Option c: q.getOptions()
        ) {
            options.add(choiceService.createChoice(c));

        }
        question.setOptions(options);}
        catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }
        return questionRepository.save(question);
    }
}
