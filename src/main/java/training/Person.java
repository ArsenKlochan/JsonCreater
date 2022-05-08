package training;

import java.util.List;

public class Person {
    public Person() {
    }

    public Person(String name, int age, boolean isMarried, List hobbies,
                  List kids) {
        this.name = name;
        this.age = age;
        this.isMarried = isMarried;
        this.hobbies = hobbies;
        this.kids = kids;
    }

    Person(String name, int age) {
        this(name, age, false, null, null);
    }

    private String name;
    private Integer age;
    private Boolean isMarried;
    private List hobbies;
    private List kids;

    // getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getisMarried() {
        return isMarried;
    }

    public void setMarried(Boolean married) {
        isMarried = married;
    }

    public List getHobbies() {
        return hobbies;
    }

    public void setHobbies(List hobbies) {
        this.hobbies = hobbies;
    }

    public List getKids() {
        return kids;
    }

    public void setKids(List kids) {
        this.kids = kids;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", hobbies=" + hobbies +
                ", kids=" + kids +
                '}';
    }
}

