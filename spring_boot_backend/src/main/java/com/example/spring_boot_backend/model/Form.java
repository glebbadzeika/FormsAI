package com.example.spring_boot_backend.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    private String style;

    private Boolean collect_email;

//    private Date creation_date;
    @OneToMany
    @JoinColumn(name = "form_id")
    private List<Section> sections;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCollect_email() {
        return collect_email;
    }

    public void setCollect_email(Boolean collect_email) {
        this.collect_email = collect_email;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }



    @Override
    public String toString() {
        return this.sections.toString();
    }


}
