package com.example.it307_project.Model;

public class CreditModel {
    private String name;
    private float totalCredit;

    public CreditModel(String name, float totalCredit) {
        this.name = name;
        this.totalCredit = totalCredit;
    }

    public String getName() {
        return name;
    }

    public float getTotalCredit() {
        return totalCredit;
    }
}
