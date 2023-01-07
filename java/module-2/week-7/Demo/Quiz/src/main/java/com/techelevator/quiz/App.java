package com.techelevator.quiz;

import com.techelevator.quiz.model.Root;
import com.techelevator.quiz.services.ConsoleService;
import com.techelevator.quiz.services.QuizService;

public class App {
    private final ConsoleService consoleService = new ConsoleService();
    private final QuizService quizService = new QuizService();

    public static void main(String[] args){
        App app = new App();
        app.run();
    }

    private void run(){
        consoleService.PrintIntro();

        //Call our quiz API to get the questions
        Root quizData = quizService.GetQuizQuestions();

        //Initialize variables
        int questionCount = quizData.results.size();
        int questionNumber = 0;
        int score = 0;

        //Ask the questions
        for(int a = 0; a < questionCount; a++){
            questionNumber++;
            boolean result = consoleService.AskQuestion(quizData.results.get(a), questionNumber);
            if(result) score++;
        }

        //Tell the player the final score
        consoleService.PrintFinalScore(score, questionCount);
    }
}
