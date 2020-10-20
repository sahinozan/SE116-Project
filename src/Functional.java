import java.io.*;
import java.time.LocalDate;
import java.util.*;

public interface Functional {

    Scanner sc = new Scanner(System.in);

    int menu();

    void grades();

    void absenteeism();

    void calendar();

    void staticMenu(String menuName);

    static String loginCheck(String loginType) {

        int remainingAttempts = 3;

        while (true) {

            try {

                System.out.println(" ➾ Please Enter Your ID: ");
                String ID = sc.nextLine();

                System.out.println();

                File pathFile = new File(System.getProperty("user.dir") + "/SampleFolder/Login/" + loginType + "/" + ID + ".txt");

                if (remainingAttempts == 0) {

                    return null;
                } else if (pathFile.exists()) {

                    BufferedReader br = new BufferedReader(new FileReader(pathFile));

                    System.out.println(" ➾ Please Enter Your Password: ");
                    String inputPassword = sc.nextLine();

                    System.out.println();

                    String password = br.readLine();
                    br.close();

                    if (inputPassword.equals(password)) {

                        System.out.println(" ➾ Login successful!");
                        return ID;
                    } else {

                        System.out.println(" ➾ Incorrect Password! " + remainingAttempts + " attempts remaining.");
                        remainingAttempts--;
                    }
                } else {

                    System.out.println(" ➾ ID Not Found! " + remainingAttempts + " attempts remaining.");
                    remainingAttempts--;
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    static Student createStudent(String ID) {

        String studentName = null, studentID = null;

        ArrayList<CoursePack> studentGrades = new ArrayList<>();
        ArrayList<CoursePack> studentAbsenteeism = new ArrayList<>();
        ArrayList<ToDo> studentCalendar = new ArrayList<>();

        File baseFile = new File(System.getProperty("user.dir") + "/SampleFolder/Student/" + ID);

        BufferedReader br;

        String line;
        LocalDate localDate;

        try {

            File[] infoFolders = baseFile.listFiles();

            for (File f : infoFolders) {

                if (f.isDirectory()) {

                    File[] lectureFiles = f.listFiles();

                    for (File l : lectureFiles) {

                        br = new BufferedReader(new FileReader(l.getPath()));

                        if (l.getParentFile().getName().equals("Absenteeism")) {

                            studentAbsenteeism.add(
                                    new CoursePack(
                                            l.getName(),    // Course Code
                                            br.readLine())  // Value of Absenteeism
                            );
                        } else if (l.getParentFile().getName().equals("Grades")) {

                            ArrayList<Grade> grades = new ArrayList<>();

                            while ((line = br.readLine()) != null) {

                                String[] gradeTypeAndValue = line.split(" - ");

                                if (gradeTypeAndValue.length == 1) {

                                    String[] fixedArray = new String[2];
                                    fixedArray[0] = gradeTypeAndValue[0];
                                    fixedArray[1] = String.valueOf(0);

                                    gradeTypeAndValue = fixedArray;
                                }
                                grades.add(
                                        new Grade(
                                                gradeTypeAndValue[0],
                                                Integer.parseInt(gradeTypeAndValue[1])
                                        )
                                );
                            }

                            studentGrades.add(
                                    new CoursePack(
                                            l.getName(),  // Course Code
                                            grades        // The Course's Grading Criteria's and Their Values
                                    )
                            );
                        }
                        br.close();
                    }
                } else {

                    if (f.getName().equals("calendar.txt")) {

                        br = new BufferedReader(new FileReader(f.getPath()));

                        while ((line = br.readLine()) != null) {

                            String[] dayMonthYearEvents = line.split("-");

                            String[] tempEvent = dayMonthYearEvents[3].split(" ! ");

                            List<String> studentEvent = new ArrayList<>(Arrays.asList(tempEvent));

                            localDate = LocalDate.of(
                                    Integer.parseInt(dayMonthYearEvents[0]),
                                    Integer.parseInt(dayMonthYearEvents[1]),
                                    Integer.parseInt(dayMonthYearEvents[2])
                            );

                            studentCalendar.add(
                                    new ToDo(
                                            localDate,      // Specific Date
                                            studentEvent    // studentEvent
                                    )
                            );
                        }
                        br.close();
                    } else if (f.getName().equals("info.txt")) {

                        br = new BufferedReader(new FileReader(f.getPath()));
                        studentName = br.readLine();
                        studentID = br.readLine();
                        br.close();
                    }
                }
            }
            return new Student(studentName, studentID, studentGrades, studentAbsenteeism, studentCalendar);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    static Lecturer createLecturer(String ID) {

        String lecturerID = null, lecturerName = null;
        boolean isAdvisor = false;

        ArrayList<Lecture> lecturerLectures = new ArrayList<>();
        ArrayList<ToDo> lecturerCalendar = new ArrayList<>();

        File baseFile = new File(System.getProperty("user.dir") + "/SampleFolder/Lecturer/" + ID);

        BufferedReader br;

        String line;
        LocalDate localDate;

        try {

            File[] lectureFolders = baseFile.listFiles();

            for (File f : lectureFolders) {

                if (f.isDirectory()) {

                    String lectureCode = f.getName();

                    ArrayList<Section> sections = new ArrayList<>();

                    File[] sectionFiles = f.listFiles();

                    for (File s : sectionFiles) {

                        br = new BufferedReader(new FileReader(s.getPath()));

                        String sectionNumber = s.getName();
                        ArrayList<Student> sectionStudents = new ArrayList<>();

                        // Read and process all Student ID's in a Section file
                        while ((line = br.readLine()) != null) {

                            ArrayList<Grade> grades = new ArrayList<>();

                            String studentID = line;
                            String studentPath = System.getProperty("user.dir") + "/SampleFolder/Student/" + studentID;

                            // Fetching the Student's Name
                            BufferedReader bri = new BufferedReader(new FileReader(studentPath + "/info.txt"));
                            String studentName = bri.readLine();

                            // Fetching the Student's Grades for this Lecture
                            bri = new BufferedReader(new FileReader(studentPath + "/Grades/" + lectureCode));
                            while ((line = bri.readLine()) != null) {

                                String[] gradeTypeAndValue = line.split(" - ");

                                grades.add(
                                        new Grade(
                                                gradeTypeAndValue[0],
                                                Integer.parseInt(gradeTypeAndValue[1])
                                        )
                                );
                            }
                            CoursePack studentGrades = new CoursePack(lectureCode, grades);

                            // Fetching the Student's Absenteeism for this Lecture
                            bri = new BufferedReader(new FileReader(studentPath + "/Absenteeism/" + lectureCode));
                            CoursePack studentAbsenteeism = new CoursePack(lectureCode, bri.readLine());

                            // Creating the Student object by using all the information
                            sectionStudents.add(
                                    new Student(
                                            studentName,
                                            studentID,
                                            studentGrades,
                                            studentAbsenteeism
                                    )
                            );
                            bri.close();
                        }
                        sections.add(
                                new Section(
                                        sectionNumber,
                                        sectionStudents
                                )
                        );
                    }

                    lecturerLectures.add(
                            new Lecture(
                                    lectureCode,
                                    sections
                            )
                    );

                } else {

                    if (f.getName().equals("calendar.txt")) {

                        br = new BufferedReader(new FileReader(f.getPath()));

                        while ((line = br.readLine()) != null) {

                            String[] dayMonthYearEvents = line.split("-");

                            String[] tempEvent = dayMonthYearEvents[3].split(" ! ");

                            List<String> studentEvent = new ArrayList<>(Arrays.asList(tempEvent));

                            localDate = LocalDate.of(
                                    Integer.parseInt(dayMonthYearEvents[0]),
                                    Integer.parseInt(dayMonthYearEvents[1]),
                                    Integer.parseInt(dayMonthYearEvents[2])
                            );

                            lecturerCalendar.add(
                                    new ToDo(
                                            localDate,      // Specific Date
                                            studentEvent    // studentEvent
                                    )
                            );
                        }
                        br.close();
                    } else if (f.getName().equals("info.txt")) {

                        br = new BufferedReader(new FileReader(f.getPath()));
                        lecturerName = br.readLine();
                        lecturerID = br.readLine();
                        String advisorCheck = br.readLine();

                        if (advisorCheck.equals("true")) {

                            isAdvisor = true;
                        }

                        br.close();
                    }
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return new Lecturer(lecturerName, lecturerID, lecturerLectures, isAdvisor, lecturerCalendar);
    }

    static void cls() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    static int catcher(int min, int max) {

        while (true) {

            try {

                int temp = sc.nextInt();

                if (min <= temp && max >= temp) {

                    return temp;
                } else {

                    System.out.printf("Invalid input integer between %d and %d %n", min, max);
                }

            } catch (InputMismatchException e) {

                sc.nextLine();
                System.out.println("Invalid input");
            }
        }
    }
}
