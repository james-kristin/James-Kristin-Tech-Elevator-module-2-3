package com.techelevator.quiz.services;

import com.techelevator.quiz.model.Root;
import org.springframework.web.client.RestTemplate;

public class QuizService {
    public static String API_BASE_URL = "https://opentdb.com/api.php?amount=10";
    private RestTemplate restTemplate = new RestTemplate();

    public Root GetQuizQuestions(){
        return restTemplate.getForObject(API_BASE_URL, Root.class);
    }
}
