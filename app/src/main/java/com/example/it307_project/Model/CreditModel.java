package com.example.it307_project.Model;

public class CreditModel {
    private String name;
    private String totalCredit;

    public CreditModel(String name, String totalCredit) {
        this.name = name;
        this.totalCredit = totalCredit;
    }

    public String getName() {
        return name;
    }

    public String getTotalCredit() {
        return totalCredit;
    }
}
