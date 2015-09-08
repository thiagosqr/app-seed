package com.github.thiagosqr.entities;

import com.github.thiagosqr.Convertible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by thiago-rs on 2/23/15.
 */
@Entity
public class Student implements Convertible{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private Date dob;

    @Column
    private String sex;

    @Column
    private Boolean active;

    public Student(){}

    public Student(Integer id,String name, Date dob, String sex, Boolean active) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.sex = sex;
        this.active = active;
    }

    public String dateAsString(){
        return this.asString(dob);
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