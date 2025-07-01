package org.example;

import java.util.*;

public class Main{
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<String> topStudents = new ArrayList<>();
        int maxGrade = 0;
        int gradesTotal = 0;
        HashMap<Character, Integer> gradeCounts = new HashMap<>();

        //Pre-populate hashmap
        for (char grade : new char[]{'A', 'B', 'C', 'D', 'F'}) {
            gradeCounts.put(grade, 0);
        }

        // Ask for how many students
        System.out.print("Enter number of students: ");
        int numberOfStudents = scanner.nextInt();

        //Iterate through number of students
        for(int i = 0; i < numberOfStudents; i++){

            //Enter name
            System.out.printf("Enter name of student %d: ", i+1);
            String studentName = scanner.next();


            int studentGrade;
            do{
                //Enter grade
                System.out.print("Enter score for " + studentName + ": ");
                studentGrade = scanner.nextInt();
            }while(studentGrade < 0 || studentGrade > 100);


            //Add grade to total
            gradesTotal += studentGrade;

            char letterGrade = '-';
            //Display letter grade
            if (studentGrade >= 90 && studentGrade <= 100){
                letterGrade = 'A';
            }else if (studentGrade >= 80 && studentGrade < 90){
                letterGrade = 'B';
            }else if (studentGrade >= 70 && studentGrade < 80){
                letterGrade = 'C';
            }else if (studentGrade >= 60 && studentGrade < 70){
                letterGrade = 'D';
            }else if(studentGrade < 60) {
                letterGrade = 'F';
            }
            System.out.println(studentName + " got grade: " + letterGrade);

            System.out.println();

            //Add letterGrade to the hashmap
            gradeCounts.put(letterGrade, gradeCounts.get(letterGrade) + 1);

            //Check if grade is equal to topStudents grade
            if (studentGrade > maxGrade){
                //Clear top students
                topStudents.clear();

                //Insert current student to top students
                topStudents.add(studentName);

                //update max grade
                maxGrade = studentGrade;
            }
            //if current grade equal to max grade
            else if (studentGrade == maxGrade){
                //add current student to top students
                topStudents.add(studentName);
            }
        }

        //Class Summary
        System.out.println("---- Class Summary ----");

        //Average
        float averageGrade = (float) gradesTotal / numberOfStudents;
        System.out.printf("Average Score: %.2f", averageGrade);
        System.out.println();

        //Grade Counts
        System.out.print("Grade Counts: ");
        for (char grade : new char[]{'A', 'B', 'C', 'D', 'F'}) {
            if(grade != 'F'){
                System.out.print(grade + ":" + gradeCounts.get(grade) + " ");
            }else{
                System.out.println(grade + ":" + gradeCounts.get(grade));
            }
        }

        //Top Student(s)
        System.out.print("Top Student(s): ");
        for(String topStudent : topStudents){
            System.out.print(topStudent + " ");
        }
        System.out.print("(" + maxGrade + ")");

    }
}
