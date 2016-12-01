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
    private String fName;
    private String lName;
    private String rating;
    private Date releaseDate;
    
    public Artist(String fName, String lName, String rating) {
        this.fName = fName;
        this.lName = lName;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Artist{" + "fName=" + fName + ", lName=" + lName + ", rating=" + rating + '}';
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}
