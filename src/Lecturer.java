import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Lecturer extends Academic implements Functional {

    private boolean isAdvisor;
    private ArrayList<Lecture> lectures;

    public Lecturer(String name, String ID, ArrayList<Lecture> lectures, boolean isAdvisor, ArrayList<ToDo> calendar) {

        super(name, ID, calendar);

        setIsAdvisor(isAdvisor);
        setLectures(lectures);
    }

    @Override
    public int menu() {

        while (true) {

            this.staticMenu("MAIN MENU");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
            System.out.println("  ╟" + StringUtils.rightPad("───╢    ➀ GRADES      ✎  ╟", 58, '─') + "╢");
            System.out.println("  ╟" + StringUtils.rightPad("───╢    ➁ ABSENTEEISM \uD83D\uDD5A  ╟", 59, '─') + "╢");
            System.out.println("  ╟" + StringUtils.rightPad("───╢    ➂ CALENDAR    \uD83D\uDCC5  ╟", 59, '─') + "╢");
            System.out.println("  ╟" + StringUtils.rightPad("───╢    ④ EXIT    \uD83D\uDEAA      ╟", 60, '─') + "╢");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╝");
            System.out.print("  ╙────➲ Select one: ");

            try {

                int selection = sc.nextInt();

                Functional.cls();

                if (selection == 1 || selection == 2 || selection == 3 || selection == 4) {

                    return selection;
                } else {

                    System.out.println(" ➾ Invalid input. Please enter a valid integer.");
                }
            } catch (InputMismatchException e) {

                sc.nextLine();
                System.out.println(" ➾ Invalid input. Please enter a valid integer.");
                System.out.println();
            }
        }
    }

    @Override
    public void grades() {

        while (true) {

            int i = 1;
            staticMenu("GRADES");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
            // Print Lecturer's Lectures
            for (Lecture l : getLectures()) {

                System.out.printf("  ╟────────┤ %d) %8.7s ├────────────────────────────────────╢\n", i, l.getLectureCode());
                System.out.println("  ╟────────┴──────────────────────────────────────────────────╜");
                i++;
            }
            System.out.println("  ╟──── ➲ Enter 0 to go back");
            System.out.print("  ╙──── ➲ Please select a lecture : ");

            // Select Lecture

            int lectureIndex = Functional.catcher(0, getLectures().size()) - 1;


            if (lectureIndex == -1) {

                Functional.cls();
                break;
            }

            i = 1;
            Functional.cls();

            staticMenu("GRADES");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╣");


            // Print That Lecture's Sections
            for (Section s : getLectures().get(lectureIndex).getSections()) {

                System.out.printf("  ╟────────┤ %d) %9.8s ├───────────────────────────────────╢\n", i, s.getSectionNumber());
                System.out.println("  ╟────────┴──────────────────────────────────────────────────╜");
                i++;
            }

            System.out.println("  ╟──── ➲ Enter 0 to go back");
            System.out.print("  ╙──── ➲ Please select a section : ");

            // Select Section
            int sectionIndex = Functional.catcher(0, getLectures().get(lectureIndex).getSections().size()) - 1;


            if (sectionIndex == -1) {

                Functional.cls();
                break;
            }
            Functional.cls();

            staticMenu("GRADES");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╝");

            System.out.print("  ╟──┤" + StringUtils.center("ID", 11) + "├──┤" + StringUtils.center("NAME", 12) + "│     ");


            for (Grade g : getLectures().get(lectureIndex).getSections().get(sectionIndex).getSectionStudents().get(0).getMyGrades().get(0).getCourseGrades()) {

                System.out.print("  │" + StringUtils.center(g.getGradeType(), 7) + "│");
            }

            System.out.println();
            // Print the Grades of that Section Students
            for (Student student : getLectures().get(lectureIndex).getSections().get(sectionIndex).getSectionStudents()) {

                System.out.print("  ║  │" + student.getID() + "│  │" + student.getName() + "  │     ");
                for (Grade g : student.getMyGrades().get(0).getCourseGrades()) {


                    System.out.print("  │" + StringUtils.center(String.valueOf(g.getGradeValue()), 7) + "│");
                }
                System.out.println();
            }

            System.out.println("  ╠═══════════════════════════════════════════════════════════════════════════════");
            System.out.println("  ╠➾ Enter 0 to go back");
            System.out.println("  ╚➾ Enter -1 to enable Edit Mode");

            String selection = sc.next();
            //If Lecturer enters specific value to go back
            if (selection.equals("0")) {

                Functional.cls();
                break;
            }
            // If Lecturer enters specific value to change grades
            else if (selection.equals("-1")) {

                changeGrades(
                        getLectures().get(lectureIndex).getSections().get(sectionIndex),
                        getLectures().get(lectureIndex).getLectureCode()
                );
            } else {
                System.out.println(" ➾ Invalid Input!");
            }


        }
    }

    public void changeGrades(Section section, String courseCode) {

        ArrayList<String> localChanges = new ArrayList<>();

        ArrayList<Student> overwriteID = new ArrayList<>();

        while (true) {

            int i = 1;
            System.out.println("  ╠➾ Local Changes");
            for (String s : localChanges) {

                System.out.println(i + "- " + s);
                i++;
            }
            System.out.println();

            System.out.println("  ╚➾ Please Enter the Student ID. Enter 0 to Exit Edit Mode");
            String selection = sc.next();

            if (selection.equals("0")) {

                System.out.println("  ╚➾ Do you want to save your changes? (Y / N) Enter anything else to cancel.");
                selection = sc.next();

                if (selection.toUpperCase().equals("Y")) {

                    System.out.println("  ╚➾ Processing the changes...");

                    for (Student s : overwriteID) {

                        try {
                            BufferedWriter bw = new BufferedWriter(
                                    new FileWriter(
                                            System.getProperty("user.dir") + "/SampleFolder/Student/" + s.getID() + "/Grades/" + courseCode
                                    )
                            );

                            for (Grade g : s.getMyGrades().get(0).getCourseGrades()) {

                                String writeLine = g.getGradeType() + " - " + g.getGradeValue() + "\n";
                                bw.write(writeLine);
                            }
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                } else if (selection.toUpperCase().equals("N")) {

                    System.out.println("  ╚➾ Discarding the changes...");
                    break;
                } else {

                    System.out.println();
                }
            }

            System.out.println("  ╚➾ Please Enter the grading criteria");
            String criteria = sc.next();

            System.out.println("  ╚➾ Please Enter the grade");
            int grade = Functional.catcher(0, 100);

            for (Student s : section.getSectionStudents()) {

                if (s.getID().equals(selection)) {

                    for (Grade g : s.getMyGrades().get(0).getCourseGrades()) {

                        if (g.getGradeType().equals(criteria)) {

                            g.setGradeValue(grade);

                            overwriteID.add(s);
                            localChanges.add(criteria + " grade for " + selection + " is now " + grade + ".");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void absenteeism() {

        while (true) {

            int i = 1;
            // Print Lecturer's Lectures
            for (Lecture l : getLectures()) {

                staticMenu("ABSENTEEISM");
                System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
                System.out.printf("  ╟────────┤ %d) %8.7s├─────────────────────────────────────╢\n", i, l.getLectureCode());
                i++;
            }
            System.out.println("  ╟───────────────────────────────────────────────────────────╜");
            System.out.println("  ╟──── ➲ Enter 0 to go back");
            System.out.print("  ╙──── ➲ Please select a lecture : ");

            // Select Lecture
            int lectureIndex = Functional.catcher(0, getLectures().size()) - 1;

            Functional.cls();

            if (lectureIndex == -1) {

                Functional.cls();
                break;
            }

            i = 1;
            staticMenu("ABSENTEEISM");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╣");

            // Print That Lecture's Sections
            for (Section s : getLectures().get(lectureIndex).getSections()) {

                System.out.printf("  ╟────────┤ %d) %9.8s├────────────────────────────────────╢\n", i, s.getSectionNumber());
                i++;
            }

            System.out.println("  ╟────────┴──────────────────────────────────────────────────╜");
            System.out.println("  ╟──── ➲ Enter 0 to go back");
            System.out.print("  ╙──── ➲ Please select a section : ");

            // Select Section
            int sectionIndex = Functional.catcher(0, getLectures().get(lectureIndex).getSections().size()) - 1;

            Functional.cls();

            if (sectionIndex == -1) {

                Functional.cls();
                break;
            }

            staticMenu("ABSENTEEISM");
            System.out.println("  ╠═══════════════════════════════════════════════════════════╝");

            System.out.println("  ╟──┤" + StringUtils.center("ID", 11) + "├──┤" + StringUtils.center("NAME", 11) + "├──┤" + StringUtils.center("ABSENTEEISM", 23) + "├──╢");


            // Print the Grades of that Section Students
            for (Student student : getLectures().get(lectureIndex).getSections().get(sectionIndex).getSectionStudents()) {

                System.out.println("  ╟──┤" + StringUtils.center(student.getID(), 11) + "├──┤" + StringUtils.center(student.getName(), 11) + "├──┤" + StringUtils.center(student.getMyAbsenteeism().get(0).getAbsenteeism(), 23) + "├──╢");
            }


            System.out.println("  ╠➾ Enter 0 to go back");
            System.out.println("  ╚➾ Enter -1 to enable Edit Mode");

            String selection = sc.next();
            //If Lecturer enters specific value to go back
            if (selection.equals("0")) {

                Functional.cls();
                break;
            }
            // If Lecturer enters specific value to change grades
            else if (selection.equals("-1")) {

                changeAbsenteeism(
                        getLectures().get(lectureIndex).getSections().get(sectionIndex),
                        getLectures().get(lectureIndex).getLectureCode()
                );
            }
        }
        Functional.cls();
    }

    public void changeAbsenteeism(Section section, String courseCode) {

        ArrayList<String> localChanges = new ArrayList<>();

        ArrayList<Student> overwriteID = new ArrayList<>();

        while (true) {

            int i = 1;
            System.out.println();
            for (String s : localChanges) {

                System.out.println(i + "- " + s);
                i++;
            }
            System.out.println();

            System.out.println("  ╚➾ Please Enter the Student ID. Enter 0 to Exit Edit Mode");
            String selection = sc.next();

            if (selection.equals("0")) {

                System.out.println("  ╚➾ Do you want to save your changes? (Y / N) Enter anything else to cancel.");
                selection = sc.next();

                if (selection.toUpperCase().equals("Y")) {

                    System.out.println("  ╚➾ Processing the changes...");

                    for (Student s : overwriteID) {

                        try {

                            BufferedWriter bw = new BufferedWriter(
                                    new FileWriter(
                                            System.getProperty("user.dir") + "/SampleFolder/Student/" + s.getID() + "/Absenteeism/" + courseCode
                                    )
                            );
                            bw.write(s.getMyAbsenteeism().get(0).getAbsenteeism());
                            bw.close();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                    break;
                } else if (selection.toUpperCase().equals("N")) {

                    System.out.println("  ╚➾ Discarding the changes...");
                    break;
                } else {

                    System.out.println();
                }
            }

            String absenteeism;
            while (true) {

                System.out.println("  ╚➾ Please Enter the Absenteeism Value");

                absenteeism = sc.next();
                try {

                    int tempAbsenteeism = Integer.parseInt(absenteeism);

                    if (tempAbsenteeism >= 0) {
                        break;
                    }
                } catch (InputMismatchException e) {

                    System.out.println("  Invalid Input ! Please enter a positive integer value ");
                }
            }

            for (Student s : section.getSectionStudents()) {

                if (s.getID().equals(selection)) {

                    s.getMyAbsenteeism().get(0).setCourseData(absenteeism);

                    overwriteID.add(s);
                    localChanges.add("  ╚➾ Absenteeism for " + selection + " is now " + absenteeism + ".");
                }
            }
        }
    }

    @Override
    public void calendar() {

        while (true) {

            this.staticMenu("CALENDAR");
            super.viewCalendar();

            System.out.println("  ╠═══════════════════════════════════════════════════════════════════════════════");
            System.out.println("  ╠➾ Enter 0 to Go Back");
            System.out.println("  ╚➾ Enter -1 to Enable Edit Mode");

            int selection = Functional.catcher(-1, 0);

            if (selection == -1) {

                if(isAdvisor){
                    changeCalendar("Advisor");
                } else {

                    changeCalendar("Lecturer");
                }
            } else if (selection == 0) {

                Functional.cls();
                break;
            } else {

                System.out.println(" ➾ Invalid Input.");
            }
        }
    }

    @Override
    public void changeCalendar(String loginType) {

        super.changeCalendar(loginType);

    }

    @Override
    public void staticMenu(String menuName) {

        System.out.println("  ╔═══════════════════════════════════════════════════════════╗");
        System.out.println("  ║" + StringUtils.rightPad("Lecturer Login: " + this.getName(), 59) + "║");
        System.out.println("  ║" + StringUtils.rightPad("Advisor Access: " + (getIsAdvisor() ? "☑" : "☒"), 59) + "║");
        System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
        System.out.println("  ║" + StringUtils.center(menuName, 59) + "║");
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public boolean getIsAdvisor() {

        return isAdvisor;
    }

    public void setIsAdvisor(boolean isAdvisor) {

        this.isAdvisor = isAdvisor;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }
}
