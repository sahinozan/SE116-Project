import java.util.ArrayList;

public class Lecture {

    private String lectureCode;
    private ArrayList<Section> sections;

    public Lecture(String lectureCode, ArrayList<Section> sections) {

        setLectureCode(lectureCode);
        setSections(sections);
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }
}
