/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author Niklas
 */
public class Director {
    private String directorId;
    private String name;
    private String rating;
    private String nationality;
    private String movie;
    
    
    public Director(int directorId, String name, String rating, String nationality, String movie) { 
        this.directorId = String.valueOf(directorId);
        this.name = name;
        this.rating = rating;
        this.nationality = nationality;
        this.movie = movie;
    }

    public Director(String directorId, String name, String rating, String nationality, String movie) { 
        this.directorId = directorId;
        this.name = name;
        this.rating = rating;
        this.nationality = nationality;
        this.movie = movie;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
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

    public void setMovie(String movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Director{" + "name=" + name + ", rating=" + rating + ", nationality=" + nationality + ", movie=" + movie + '}';
    }

    
}
