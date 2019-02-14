// Nick Kouthong 
// 8/8/16
// CSE 143 Ac Tanvi Dighde
// Homework 6: 20 questions
//
// This program constructs a QuestionTree object that plays and keeps
// track of a game of 20 questions. The game will grow smarter with every
// wrong answer it guesses. 

import java.util.*;
import java.io.*;
public class QuestionTree {
   
   private UserInterface currUi;// Stores current user interface
   private QuestionNode overallRoot;// Stores questions and answers
   private int games;//Records number of games played
   private int wins;//Records number of games won by the computer
   
   //Pre:Passed in UserInterface must not be null otherwise an
   //IllegalArgumentException is thrown.
   //Post:Takes in a UserInterface ui to initialize an interface the user
   //will interact with. Game initially starts with a single answer "computer".
   public QuestionTree(UserInterface ui) {
      if(ui == null) {
         throw new IllegalArgumentException();
      }
      this.currUi = ui;
      this.overallRoot = new QuestionNode("computer");
   }
   
   //Post: Initiates and plays a game of 20 questions with the user.If the 
   //computer guesses the wrong answer, it will ask the user for the object,
   //a defining question and a yes/no to answer for that question and stores 
   //this information for the next round. 
   public void play() {
      this.games++;
      this.overallRoot = this.playHelper(this.overallRoot);
   }
   
   //Post: Takes in a QuestionNode root and returns a QuestionNode
   //reflecting the user's responses the to game's questions. If the computer
   //guesses a wrong answer, the game will incorporate that object and a 
   //user inputted defining question into the game. 
   private QuestionNode playHelper(QuestionNode root) {
      if(root.left == null && root.right == null) {
         this.currUi.print("Would your object happen to be " + root.question + "?");
         if(this.currUi.nextLine().toLowerCase().startsWith("n")) {
            this.currUi.print("I lose. What is your object?");
            String object = this.currUi.nextLine();
            this.currUi.print("Type a yes/no question to distinguish your item from " + 
                  root.question + ":");
            String question = this.currUi.nextLine();
            this.currUi.print("And what is the answer for your object?"); 
            if(this.currUi.nextLine().toLowerCase().startsWith("y")) {
               root = new QuestionNode(question,new QuestionNode(object),root);
            }else {
               root = new QuestionNode(question,root,new QuestionNode(object));
            }
         }else {
            this.currUi.println("I win!");
            wins++;
         }
      }else {
         this.currUi.print(root.question);
         if(this.currUi.nextLine().toLowerCase().startsWith("n")) {
            root.right = playHelper(root.right);
         }else {
            root.left = playHelper(root.left);
         }
      }
      return root;
   }
   
   //Pre: PrintStream must not be null otherwise an IllegalArgumentException
   //is thrown
   //Post: Takes in a PrintStream output and saves the current state of the 
   //game into the output file.The game is stored in preorder format
   public void save(PrintStream output) {
      if(output == null) {
         throw new IllegalArgumentException();
      }
      this.saveHelper(this.overallRoot,output); 
   }
   
   //Post: Takes in a QuestionNode root and PrintStream output and
   //saves the current state of the game into the specified output file
   //in preorder format.
   private void saveHelper(QuestionNode root, PrintStream output) {
      if(root.left == null && root.right == null) {
         output.println("A:" + root.question);
      }else {
         output.println("Q:" + root.question);
         saveHelper(root.left,output);
         saveHelper(root.right,output);
      }
   }
   
   //Pre:Passed in Scanner must not be null otherwise an 
   //IllegalArgumentException is thrown.
   //Post: Takes in a Scanner input to load a previous game state to be
   //played with the user. This replaces the current state of the game.
   //Loading a game will not effect the amount of games played and the amount
   //of games won by the computer.
   public void load(Scanner input) {
      if(input == null) {
         throw new IllegalArgumentException();
      }
      this.overallRoot = loadHelper(this.overallRoot,input);
   }
  
   //Post:Takes in a QuestionNode root and Scanner input to reconstruct
   //a saved game which is stored in the passed in scanner. Replaces the
   //current state of the game.
   private QuestionNode loadHelper(QuestionNode root,Scanner input) {
      String[] line = input.nextLine().trim().split(":");
      root = new QuestionNode(line[1]);
      if(line[0].equalsIgnoreCase("Q") ) {
         root.left = loadHelper(root.left,input);
         root.right = loadHelper(root.right,input);     
      }
      return root;
   }
   
   //Post: Returns the total amount of games played
   public int totalGames() {
      return this.games;
   }
   
   //Post: Returns the number of games won by the computer
   public int gamesWon() {
      return this.wins;
   }
}