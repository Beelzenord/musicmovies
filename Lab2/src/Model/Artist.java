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
public class Artist {
    private int artistId;
    private String name;
    private String rating;
    private String nationality;
    private String album;
    
    
    public Artist(int artistId, String name, String rating, String nationality, String album) { 
        this.artistId = artistId;
        this.name = name;
        this.rating = rating;
        this.nationality = nationality;
        this.album = album;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
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
    
    public String getAlbum() {
        return album;
    }

    @Override
    public String toString() {
        return "Artist{" + "name=" + name + ", rating=" + rating + ", nationality=" + nationality + '}';
    }

}
