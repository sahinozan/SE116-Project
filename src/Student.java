import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Student extends Academic implements Functional {

    private ArrayList<CoursePack> myGrades;
    private ArrayList<CoursePack> myAbsenteeism;

    // Basic initialization for Lecturers
    public Student(String name, String ID, CoursePack grades, CoursePack absenteeism) {

        super(name, ID, null);

        myGrades = new ArrayList<>();
        myGrades.add(grades);

        myAbsenteeism = new ArrayList<>();
        myAbsenteeism.add(absenteeism);
    }

    // Personal initialization for Students
    public Student(String name, String ID, ArrayList<CoursePack> myGrades, ArrayList<CoursePack> myAbsenteeism, ArrayList<ToDo> calendar) {

        super(name, ID, calendar);
        setMyGrades(myGrades);
        setMyAbsenteeism(myAbsenteeism);
    }


    @Override
    public int menu() {

        while (true) {

            this.staticMenu("MAIN MENU");
            System.out.println("  ╟" + StringUtils.rightPad("───╢    ➀ GRADES      ✎  ╟", 59, '─') + "╢");
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
                    System.out.println(" ➾ Yazık kafana !");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println(" ➾ Invalid input. Please enter a valid integer. ");
                System.out.println();
            }
        }
    }

    @Override
    public void grades() {

        Functional.cls();

        this.staticMenu("GRADES");

        for (int i = 0; i < getMyGrades().size(); i++) {

            System.out.printf("  ║        │ %-8.7s├────────────────────────────────────────╢\n",
                    getMyGrades().get(i).getCourseCode());
            System.out.println("  ╟────────┼─────────┴────────────────────────────────────────╢");


            for (int j = 0; j < getMyGrades().get(i).getCourseGrades().size(); j++) {

                System.out.printf("  ║        │ %-16.15s= %-3d                            ║\n",
                        getMyGrades().get(i).getCourseGrades().get(j).getGradeType(),
                        getMyGrades().get(i).getCourseGrades().get(j).getGradeValue());

            }
            System.out.println("  ╟────────┴──────────────────────────────────────────────────╜");
        }
        System.out.println("  ╙──── ➲ Enter any value to go back");
        sc.nextLine();
        sc.nextLine();
        Functional.cls();
    }

    @Override
    public void absenteeism() {

        Functional.cls();

        this.staticMenu("ABSENTEEISM");

        for (int i = 0; i < getMyAbsenteeism().size(); i++) {

            System.out.printf("  ╟───┤  %-8.7s├───────┤   %-4.3s├────────────────────────────╢\n", getMyAbsenteeism().get(i).getCourseCode(),
                    getMyAbsenteeism().get(i).getCourseData().get(0));

        }
        System.out.println("  ╟───────────────────────────────────────────────────────────╜");
        System.out.println("  ╙──── ➲ Enter any value to go back");

        sc.nextLine();
        sc.nextLine();
        Functional.cls();
    }

    @Override
    public void calendar() {

        this.staticMenu("CALENDAR");
        super.viewCalendar();

        changeCalendar("Student");
    }

    @Override
    public void changeCalendar(String loginType) {

        super.changeCalendar(loginType);
    }

    @Override
    public void staticMenu(String menuName) {

        System.out.println("  ╔═══════════════════════════════════════════════════════════╗");
        System.out.println("  ║" + StringUtils.rightPad("  Student Login: " + this.getName(), 59) + "║");
        System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
        System.out.println("  ║" + StringUtils.center(menuName, 59) + "║");
        System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
    }

    public void setMyAbsenteeism(ArrayList<CoursePack> myAbsenteeism) {
        this.myAbsenteeism = myAbsenteeism;
    }

    public void setMyGrades(ArrayList<CoursePack> myGrades) {
        this.myGrades = myGrades;
    }

    public ArrayList<CoursePack> getMyGrades() {
        return myGrades;
    }

    public ArrayList<CoursePack> getMyAbsenteeism() {
        return myAbsenteeism;
    }


}
