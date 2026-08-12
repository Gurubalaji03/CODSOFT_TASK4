package task4;
import java.util.Scanner;
import java.util.concurrent.*;

public class QuizApplication 
{

    public static void main(String[] args) 
    {
        Scanner gp=new Scanner(System.in);
        String[] questions= 
        {
            "Which keyword is used to inherit a class in Java?",
            "Which method is the entry point of a Java program?",
            "Which data type is used to store decimal numbers?",
            "Which collection does not allow duplicate elements?",
            "Which keyword is used to create an object?"
        };
        String[][]options=
        {
            {"this","super","extends","implements"},
            {"start()","main()","run()","execute()"},
            {"int","char","double","boolean"},
            {"List","Set","ArrayList","Vector"},
            {"class","new","object","create"}
        };
        int[]answers={3,2,3,2,2};
        int score=0;
        int correctAnswers=0;
        int wrongAnswers=0;
        int timeLimit=10;
        System.out.println("================================");
        System.out.println("       JAVA QUIZ APPLICATION");
        System.out.println("================================");
        for(int i=0;i<questions.length;i++) 
        {
            System.out.println("\n--------------------------------");
            System.out.println("Question "+(i+1));
            System.out.println("--------------------------------");
            System.out.println(questions[i]);
            for(int j=0;j<options[i].length;j++) 
            {
                System.out.println((j+1)+". "+options[i][j]);
            }
            System.out.println("\nYou have "+timeLimit+" seconds.");
            System.out.print("Enter your answer: ");
            ExecutorService executor=Executors.newSingleThreadExecutor();
            Future<Integer>future=executor.submit(() ->
            {
                return gp.nextInt();
            });
            try
            {
                int userAnswer=future.get(timeLimit,TimeUnit.SECONDS);
                if(userAnswer==answers[i]) 
                {
                    System.out.println("Correct Answer!");
                    score++;
                    correctAnswers++;
                } 
                else if(userAnswer>=1&&userAnswer<= 4) 
                {
                    System.out.println("Wrong Answer!");
                    System.out.println(
                        "Correct answer: "+options[i][answers[i] - 1]
                    );
                    wrongAnswers++;
                }
                else 
                {
                    System.out.println("Invalid option!");
                    wrongAnswers++;
                }
            } 
            catch(TimeoutException e) 
            {
                System.out.println("\nTime's up!");
                System.out.println(
                    "Correct answer: "+options[i][answers[i] - 1]
                );
                wrongAnswers++;
                future.cancel(true);
            }
            catch(Exception e) 
            {
                System.out.println("Invalid input!");
                wrongAnswers++;
            }
            finally 
            {
                executor.shutdownNow();
            }
        }
        double percentage=((double) score / questions.length)*100;
        System.out.println("\n================================");
        System.out.println("          QUIZ RESULT");
        System.out.println("================================");
        System.out.println("Total Questions : "+questions.length);
        System.out.println("Correct Answers : "+correctAnswers);
        System.out.println("Wrong Answers   : "+wrongAnswers);
        System.out.println("Final Score     : "+score + " / "+questions.length);
        System.out.printf("Percentage       : %.2f%%\n",percentage);
        System.out.println("================================");
        System.out.println("       THANK YOU!");
        System.out.println("================================");
    }
}