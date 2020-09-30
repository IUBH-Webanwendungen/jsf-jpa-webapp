package com.byteslounge.jsf.war;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pojo {

    public Pojo() {}

    private String text;

    @Id
    @GeneratedValue
    private int id;

    public void setText(String a) { this.text = a; }
    public String getText() { return text; }

    public void setId(int i) { this.id = i; }
    public int getId() { return id;}

    public String toString() {
        return "Pojo " + id + " : \"" + text + "\".";
    }

}
