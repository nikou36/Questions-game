// Nick Kouthong 
// 8/8/16
// CSE 143 Ac Tanvi Dighde
// Homework 6: 20 questions
//
//This program constructs a QuestionNode object that stores the questions
//and answers used in a game of 20 questions. 

public class QuestionNode {
   public String question;//Stores string representing a question or answer
   public QuestionNode left;//Stores reference to left node 
   public QuestionNode right;//Stores reference to right node
   
   //Post:Takes in a String question to inialize a new QuestionNode
   public QuestionNode(String question) {
      this.question = question;
   }
   
   //Post:Takes in a String question, a QuestionNode left, and QuestionNode 
   //right to inialize a new QuestionNode. 
   public QuestionNode(String question, QuestionNode left, QuestionNode right) {
      this.question = question;
      this.left = left;
      this.right = right;
   }
}  