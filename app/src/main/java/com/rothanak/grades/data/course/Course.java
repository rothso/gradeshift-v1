package com.rothanak.grades.data.course;

public class Course {

    private final int yearId;

    private final String name;
    private final String instructor;
    private final int period;
    private final double grade;

    public Course(int yearId, String name, String instructor, int period, double grade) {
        this.yearId = yearId;
        this.name = name;
        this.instructor = instructor;
        this.period = period;
        this.grade = grade;
    }

    public int getYearId() {
        return yearId;
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getPeriod() {
        return period;
    }

    public double getGrade() {
        return grade;
    }
}
