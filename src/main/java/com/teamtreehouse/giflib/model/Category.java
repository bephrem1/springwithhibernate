package com.teamtreehouse.giflib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id //When an ID is marked all others automatically get the @Column annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category") //One category can be mapped to many gifs (one to many gifs)
                                        //This means the "category field in gif will be used to map the category of each gif,
                                      //Makes category_id column in the gif table
    private List<Gif> gifs = new ArrayList<>();

    @NotNull //This value must not be null for validation
    @Size(min = 3, max = 12)
    private String name;

    @NotNull
    @Pattern(regexp = "#[0-9a-fA-F]{6}") //Uses regex to validate input
    private String colorCode;

    public Category(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<Gif> getGifs() {
        return gifs;
    }

}
