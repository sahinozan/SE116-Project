import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Academic {

    private String name;
    private String ID;
    private ArrayList<ToDo> calendar;

    public Academic(String name, String ID, ArrayList<ToDo> calendar) {

        setName(name);
        setID(ID);
        setCalendar(calendar);
    }

    public void viewCalendar() {

        for (int i = 0; i < getCalendar().size(); i++) {

            System.out.printf("  ╟───┤ %-11.10s├──────────────────────────────────────────╢\n", getCalendar().get(i).getDate());

            for (int j = 0; j < getCalendar().get(i).getEvents().size(); j++) {

                System.out.printf("  ╟──┤ %-51.51s ├──╢\n", getCalendar().get(i).getEvents().get(j));

            }

            System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
        }
        System.out.println("  ╟───────────────────────────────────────────────────────────╜");

        Functional.sc.nextLine();
    }

    public void changeCalendar(String loginType) {

        ArrayList<String> localChanges = new ArrayList<>();

        while (true) {

            int i = 1;
            System.out.println("  ╠➾ Local Changes");
            for (String s : localChanges) {

                System.out.println(i + "- " + s);
                i++;
            }

            System.out.println("  ╠══➾ Enter 0 to Exit Edit Mode");
            System.out.println("  ╠══➾ Enter 1 to Add A New Event");
            System.out.println("  ╚══➾ Enter 2 to Delete An Event");
            String selection = Functional.sc.nextLine();

            // Save or Discard Changes, Then Exit
            if (selection.equals("0")) {

                System.out.println("  ╚➾ Do you want to save your changes? (Y / N) Enter anything else to cancel.");
                selection = Functional.sc.next();

                // Save and Exit
                if (selection.toUpperCase().equals("Y")) {

                    if (loginType.equals("Advisor")) {

                        System.out.println(" ╚➾ Do you also want to apply this change to all of your students? (Y / N)");

                        selection = Functional.sc.next();

                        if (selection.toUpperCase().equals("Y")) {


                            System.out.println("  ╚➾ Applying the changes to all students...");

                            if (this instanceof Lecturer) {

                                for (Lecture lecture : ((Lecturer) this).getLectures()) {

                                    for (Section section : lecture.getSections()) {

                                        for (Student student : section.getSectionStudents()) {

                                            LocalDate localDate;

                                            ArrayList<ToDo> tempCalendar = new ArrayList<>();

                                            try {

                                                BufferedReader br = new BufferedReader(
                                                        new FileReader(
                                                                System.getProperty("user.dir") + "\\SampleFolder\\Student\\" + student.getID() + "\\calendar.txt"
                                                        )
                                                );

                                                String line;

                                                while ((line = br.readLine()) != null) {

                                                    String[] dayMonthYearEvents = line.split("-");

                                                    String[] tempEvent = dayMonthYearEvents[3].split(" ! ");

                                                    List<String> studentEvent = new ArrayList<>(Arrays.asList(tempEvent));

                                                    localDate = LocalDate.of(
                                                            Integer.parseInt(dayMonthYearEvents[0]),
                                                            Integer.parseInt(dayMonthYearEvents[1]),
                                                            Integer.parseInt(dayMonthYearEvents[2])
                                                    );

                                                    tempCalendar.add(
                                                            new ToDo(
                                                                    localDate,      // Specific Date
                                                                    studentEvent    // studentEvent
                                                            )
                                                    );
                                                }

                                                tempCalendar.addAll(this.getCalendar());
                                                br.close();

                                                BufferedWriter bw = new BufferedWriter(
                                                        new FileWriter(
                                                                System.getProperty("user.dir") + "\\SampleFolder\\Student\\" + student.getID() + "\\calendar.txt"
                                                        )
                                                );

                                                for (ToDo toDo : tempCalendar) {

                                                    StringBuilder writeLine = new StringBuilder(toDo.getDate() + "-");

                                                    int k = 0;
                                                    while (k < toDo.getEvents().size()) {

                                                        writeLine.append(toDo.getEvents().get(k));

                                                        if (k == toDo.getEvents().size() - 1) {

                                                            writeLine.append("\n");
                                                        } else {

                                                            writeLine.append(" ! ");
                                                        }

                                                        k++;
                                                    }

                                                    bw.write(writeLine.toString());
                                                }
                                                bw.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (selection.toUpperCase().equals("N")) {


                            System.out.println("  ╚➾ Processing the changes...");
                        } else {

                            System.out.println(" ➾ Invalid input.");
                        }

                    }

                    try {
                        BufferedWriter bw = new BufferedWriter(
                                new FileWriter(
                                        System.getProperty("user.dir") + "\\SampleFolder\\" + (loginType.equals("Advisor") ? "Lecturer" : loginType) + "\\" + this.getID() + "\\calendar.txt"
                                )
                        );

                        for (ToDo toDo : this.getCalendar()) {

                            StringBuilder writeLine = new StringBuilder(toDo.getDate() + "-");

                            int k = 0;
                            while (k < toDo.getEvents().size()) {

                                writeLine.append(toDo.getEvents().get(k));

                                if (k == toDo.getEvents().size() - 1) {

                                    writeLine.append("\n");
                                } else {

                                    writeLine.append(" ! ");
                                }

                                k++;
                            }

                            bw.write(writeLine.toString());
                        }
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                // Discard and Exit
                else if (selection.toUpperCase().equals("N")) {

                    System.out.println("  ╚➾ Discarding the changes...");
                    break;
                }
                // Cancel action
                else {

                    System.out.println();
                }
            }
            // Add A New Event
            else if (selection.equals("1")) {

                System.out.println(" ➾ Date in Year(int)-Month(int)-Day(int) format");
                String date = Functional.sc.nextLine();
                LocalDate localDate = LocalDate.parse(date);

                System.out.println(" ➾ Enter Event name");
                String event = Functional.sc.nextLine();

                int addIndex = 0;

                for (ToDo t : getCalendar()) {

                    // If it matches with another date
                    if (t.getDate().isEqual(localDate)) {

                        t.getEvents().add(event);
                        break;
                    }
                    // If it doesn't match with any other date in the list
                    else if (t.getDate().isBefore(localDate) && addIndex == getCalendar().size() - 1) {

                        getCalendar().add(new ToDo(localDate, event));
                        break;
                    }
                    // If it's after an another date
                    else if (t.getDate().isBefore(localDate)) {

                        addIndex++;
                    }
                    // If it's before an another date
                    else if (t.getDate().isAfter(localDate)) {

                        getCalendar().add(addIndex, new ToDo(localDate, event));
                        break;
                    }
                }
                localChanges.add(event + " event in " + localDate + " is added.");
            }
            // Delete An Event
            else if (selection.equals("2")) {

                System.out.println(" ➾ Enter the Date in Year(int)-Month(int)-Day(int) format: ");
                String date = Functional.sc.nextLine();
                LocalDate localDate = LocalDate.parse(date);

                System.out.println(" ➾ Enter the Event name: ");
                String event = Functional.sc.nextLine();

                int dateIndex = -1;
                int eventIndex = -1;

                int j = 0;
                while (j < getCalendar().size()) {

                    if (getCalendar().get(j).getDate().isEqual(localDate)) {

                        dateIndex = j;

                        int k = 0;
                        while (k < getCalendar().get(j).getEvents().size()) {

                            if (getCalendar().get(j).getEvents().get(k).equals(event)) {

                                eventIndex = k;
                                break;
                            }
                            k++;
                        }
                    }
                    j++;
                }
                if (dateIndex == -1 || eventIndex == -1) {

                    System.out.println(event + " in " + localDate + " doesn't exist.");
                } else {

                    getCalendar().get(dateIndex).getEvents().remove(eventIndex);

                    if (getCalendar().get(dateIndex).getEvents().size() == 0) {

                        getCalendar().remove(dateIndex);
                    }

                    localChanges.add(event + " event in " + localDate + " is deleted.");
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<ToDo> getCalendar() {
        return calendar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCalendar(ArrayList<ToDo> myCalendar) {
        this.calendar = myCalendar;
    }
}
