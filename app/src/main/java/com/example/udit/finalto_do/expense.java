package com.example.udit.finalto_do;

/**
 * Created by udit on 7/7/2017.
 */

public class expense {
    String title;
    String category;
    Integer id;
    String description;

    public expense(String title, String description, String category, Integer id) {
        this.title = title;
        this.description=description;
        this.category = category;
        this.id=id;
    }

    public expense() {

    }
}
