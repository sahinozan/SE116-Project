

public class Grade {

    private String gradeType;
    private int gradeValue;

    public Grade(String gradeType, int gradeValue) {

        setGradeType(gradeType);
        setGradeValue(gradeValue);
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeType() {
        return gradeType;
    }

    public int getGradeValue() {
        return gradeValue;
    }
}
