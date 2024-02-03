package com.example.spring_boot_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    @Autowired
    private RestTemplate restTemplate;

    public String getChatResponse(List<String> closedAnswers ,String openAnswer,String questionText) throws RuntimeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiKey = "";
        if(apiKey.isEmpty()){
            throw new RuntimeException("OpenAI API key is missing");
        }
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", formulatePrompt(closedAnswers,questionText)),
                Map.of("role", "user", "content", "\nHere's a new response: " + openAnswer + " What is the best category for this response from the list above, " +
                        "or should I create a new category for it? Response can be 4 words max.")));
        requestBody.put("max_tokens", 150);
        requestBody.put("temperature", 0.5);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String openAIUrl = "https://api.openai.com/v1/chat/completions";
        return restTemplate.postForObject(openAIUrl, entity, String.class);
    }



    private String formulatePrompt(List<String> closedAnswers,String questionText){

        String introduction = "I need to categorize responses based on a set of predefined answers to a question that sounds like this \""+questionText+ "\"If a response doesn't fit any predefined answer, " +
                "I should suggest a new category. Here are the predefined answers:\n";


        String closedAnswersListed = closedAnswers.stream()
                .map(answer -> "- " + answer)
                .collect(Collectors.joining("\n"));



        return introduction + closedAnswersListed +" my answer can be no longer than 4 words and should be a category from the list above or the new one.";
    }
}
