package com.example.spring_boot_backend.model;

import jakarta.persistence.*;


//write hello world

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String option_text;

    private Boolean is_open;

    @OneToOne
    private Section special_section;



    public Section getSpecial_section() {
        return special_section;
    }

    public void setSpecial_section(Section special_section) {
        this.special_section = special_section;
    }

    public String getOption_text() {
        return option_text;
    }

    public void setOption_text(String choice_text) {
        this.option_text = choice_text;
    }

    public Boolean getIs_open() {
        return is_open;
    }

    public void setIs_open(Boolean is_open) {
        this.is_open = is_open;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
