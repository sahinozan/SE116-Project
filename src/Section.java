import java.util.ArrayList;

public class Section {

    private String sectionNumber;
    private ArrayList<Student> sectionStudents;

    public Section(String sectionNumber, ArrayList<Student> sectionStudents) {

        setSectionNumber(sectionNumber);
        setSectionStudents(sectionStudents);
    }

    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public void setSectionStudents(ArrayList<Student> sectionStudents) {
        this.sectionStudents = sectionStudents;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public ArrayList<Student> getSectionStudents() {
        return sectionStudents;
    }
}
