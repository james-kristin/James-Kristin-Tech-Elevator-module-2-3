package com.techelevator.quiz.services;

import com.techelevator.quiz.model.Result;

import java.util.Scanner;
import java.util.Random;

public class ConsoleService {
    private final Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    public void PrintIntro(){
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("Welcome to my little quiz");
        System.out.println("Hope you enjoy it");
        System.out.println();
        System.out.println("Let's go!");
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
    }

    public boolean AskQuestion(Result result, int questionNumber){
        System.out.println();
        System.out.println("Question " + questionNumber);
        System.out.println(result.question);

        //determine how many possible answers
        int totalAnswers = result.incorrect_answers.size() + 1;

        //pick a random spot for the right answer
        int correctAnswer = rand.nextInt(totalAnswers) + 1;
        int currentWrong = 0;

        //iterate all answers and print options to player
        for(int i = 1; i<=totalAnswers; i++){
            //is the current slot our randomly selected correct answer?
            if(i == correctAnswer){
                System.out.println(i + ". " + result.correct_answer);
            } else {
                System.out.println(i + ". " + result.incorrect_answers.get(currentWrong));
                currentWrong++;
            }
        }

        int guess;
        try{
            guess = Integer.parseInt(scanner.nextLine());
        } catch(NumberFormatException e){
            guess = 0;
        }

        //if their answer is not a valid selection, use recursion to ask the question again
        if(guess == 0 || guess > totalAnswers){
            System.out.println("Invalid guess. Try again");
            return AskQuestion(result, questionNumber);
        }

        //return result
        if(guess == correctAnswer){
            System.out.println("Correct!!");
            return true;
        }
        System.out.println("Wrong answer!!");
        return false;
    }

    public void PrintFinalScore(int score, int totalQuestions){

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("You final score is " + score + " right out of " + totalQuestions + " questions");
        System.out.println();
        System.out.println("Hope you enjoyed it!");
        System.out.println("--------------------------------");
        System.out.println();
        String exit = scanner.nextLine();
    }
}
