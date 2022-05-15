package database.objects;

public class Mark {
    private String discipline_ukr;
    private String discipline_eng;
    private int mark;
    private int hours;

    public Mark(String discipline_ukr, String discipline_eng, int hours, int mark) {
        this.discipline_ukr = discipline_ukr;
        this.discipline_eng = discipline_eng;
        this.mark = mark;
        this.hours= hours;
    }

    public String getDiscipline_ukr() {
        return discipline_ukr;
    }
    public String getDiscipline_eng() {
        return discipline_eng;
    }
    public String getCredits() {
        return String.format("%.1f", (this.hours)/30.0);
    }
    public int getMark() {
        return mark;
    }
    public int getHours() {
        return hours;
    }
    public String getNational_grade() {
        return getNational_grade(mark);
    }
    public String getECTS() {return getECTS(mark);}

    public void setMark(int mark) {
        this.mark = mark;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    //     Визначення оцінки за шкалою ECTS
    private String getECTS(int mark){
        if (mark > 89){
            return "A";
        }
        else if(mark > 81){
            return "B";
        }
        else if(mark > 73){
            return "C";
        }
        else if(mark > 63){
            return "D";
        }
        else if(mark > 59){
            return "E";
        }
        else if(mark > 34){
            return "Fx";
        }
        else{
            return "F";
        }
    }
//     Визначення оцінки за національною шкалою
    private String getNational_grade(int mark){
        if (mark > 89){
            return "Відмінно /Excellent";
        }
        else if(mark > 73){
            return "Добре / Good";
        }
        else if(mark > 59){
            return "Задовільно / Satisfactory";
        }
        else{
            return "Незараховано / Fail";
        }
    }

    @Override
    public String toString() {
        return "Mark{" +
                "discipline_ukr='" + discipline_ukr + '\'' +
                ", discipline_eng='" + discipline_eng + '\'' +
                ", credits='" + getCredits() + '\'' +
                ", mark=" + mark +
                ", national_grade='" + getNational_grade() + '\'' +
                ", ECTS='" + getECTS() + '\'' +
                '}';
    }
}
