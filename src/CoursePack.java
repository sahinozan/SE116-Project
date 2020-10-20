import java.util.ArrayList;

public class CoursePack {

    private String courseCode;
    private ArrayList<Object> courseData;

    public CoursePack(String courseCode, String data) {

        setCourseCode(courseCode);

        setCourseData(data);
    }

    public CoursePack(String courseCode, ArrayList<Grade> grades) {

        setCourseCode(courseCode);
        this.courseData = new ArrayList<>();
        this.courseData.addAll(grades);
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseData(String data) {

        courseData = new ArrayList<>();
        courseData.add(data);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public ArrayList<Object> getCourseData() {
        return courseData;

    }

    public ArrayList<Grade> getCourseGrades() {

        ArrayList<Grade> courseGrades = new ArrayList<>();

        for (Object o : getCourseData()) {

            if (o instanceof Grade) {

                Grade g = (Grade) o;

                courseGrades.add(g);
            }
        }
        return courseGrades;
    }

    public String getAbsenteeism() {

        for (Object o : getCourseData()) {

            if (o instanceof String) {

                return (String) o;
            }
        }
        return null;
    }
}

