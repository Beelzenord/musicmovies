/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Niklas
 */
public class Artist {
    private int artistId;
    private String name;
    private String rating;
    
    public Artist(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Artist{" + "name=" + name + ", rating=" + rating + '}';
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

}
