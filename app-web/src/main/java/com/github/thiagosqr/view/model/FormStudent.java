package com.github.thiagosqr.view.model;

import java.util.Date;

public class FormStudent {

    private Integer id;
    private String name;
    private Date dob;
    private String sex;
    private Boolean active;

    public FormStudent(){

    }

    public FormStudent(Integer id, String name, Date dob, String sex, Boolean active) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.sex = sex;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
