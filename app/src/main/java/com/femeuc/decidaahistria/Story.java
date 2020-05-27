package com.femeuc.decidaahistria;

public class Story {
    private int id;
    private int genre;
    private String title;
    private String description;
    private int beginning_page_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBeginning_page_id() {
        return beginning_page_id;
    }

    public void setBeginning_page_id(int beginning_page_id) {
        this.beginning_page_id = beginning_page_id;
    }
}
