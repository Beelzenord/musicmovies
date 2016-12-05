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
public class Artist {
    private int artistId;
    private String name;
    private String rating;
    private String nationality;
    private ArrayList<String> albums;
    
    
    public Artist(int artistId, String name, String rating, String nationality, ArrayList<String> albums) { //
        this.artistId = artistId;
        this.name = name;
        this.rating = rating;
        this.nationality = nationality;
        this.albums = new ArrayList();
        for (String s : albums)
            this.albums.add(s);
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
    
    public String getAlbums() {
        String tmp = new String("");
        for (String s : albums) {
            tmp += s + ", ";
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "Artist{" + "name=" + name + ", rating=" + rating + ", nationality=" + nationality + ", albums=" + albums + '}';
    }

}
