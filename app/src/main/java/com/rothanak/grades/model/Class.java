package com.rothanak.grades.model;

public class Class {

    private int id;
    private int period;
    private double percentageScore;
    private String name;
    private String teacher;

    public Class(int id, int period, double percentageScore, String name, String teacher) {
        this.id = id;
        this.period = period;
        this.percentageScore = percentageScore;
        this.name = name;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    public double getPercentageScore() {
        return percentageScore;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }
}
