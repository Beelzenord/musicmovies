/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public class Director {
    private int directorId;
    private String name;
    private String rating;
    private String nationality;
    private String movie;
    
    
    public Director(int directorId, String name, String rating, String nationality, String movie) { //
        this.directorId = directorId;
        this.name = name;
        this.rating = rating;
        this.nationality = nationality;
        this.movie = movie;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public String getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "Director{" + "name=" + name + ", rating=" + rating + ", nationality=" + nationality + '}';
    }
    
    
}
