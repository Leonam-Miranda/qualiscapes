package io.github.qualiscapes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Periodico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String issn;
    private String avaliationArea;
    private String tier;

    public Periodico(){
    }

    public Periodico(String title, String issn, String avaliationArea, String tier){
        this.title = title;
        this.issn = issn;
        this.avaliationArea = avaliationArea;
        this.tier = tier;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssn() {
        return issn;
    }

    public String getAvaliationArea() {
        return avaliationArea;
    }

    public void setAvaliationArea(String avaliationArea) {
        this.avaliationArea = avaliationArea;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
}
