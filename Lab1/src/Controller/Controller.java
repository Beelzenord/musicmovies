/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Album;
import Model.DBQueries;
import Model.QueryExecutor;
import Model.Artist;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public class Controller {
    private ArrayList<DBQueries> artists;
    
    public Controller(QueryExecutor exec) {
        artists = new ArrayList();
        artists.add(exec);
    }
    
    public void artistsByName() {
        String name = "jame";
        ArrayList<Artist> artister;
        artister = artists.get(0).getArtistsByName(name);
        for (Artist a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void artistsByRating() {
        String rating = "11";
        ArrayList<Artist> artister;
        artister = artists.get(0).getArtistsByRating(rating);
        for (Artist a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByTitle() {
        System.out.println("BY TITLE");
        String title = "horse";
        ArrayList<Album> artister;
        artister = artists.get(0).getAlbumsByTitle(title);
        for (Album a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByGenre() {
        System.out.println("BY GENRE");
        String genre = "punk";
        ArrayList<Album> artister;
        artister = artists.get(0).getAlbumsByGenre(genre);
        for (Album a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByRating() {
        System.out.println("BY RATING");
        String rating = "15";
        ArrayList<Album> artister;
        artister = artists.get(0).getAlbumsByRating(rating);
        for (Album a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByArtist() {
        System.out.println("BY ARTIST");
        String artist = "Me";
        ArrayList<Album> artister;
        artister = artists.get(0).getAlbumsByArtist(artist);
        for (Album a : artister) {
            System.out.println(a.toString());
        }
    }

    
    public void insertAlbum() {
        String title = "more";
        String genre = "svett";
        String rating = "2";
        Date rDate = new Date(97, 10, 23);
        
        artists.get(0).addNewAlbum(title, genre, rating, rDate);
    }
    
    public void insertArtist() {
        String fName = "Mel";
        String lName = "Tod";
        String rating = "3";
        artists.get(0).addNewArtist(fName, lName, rating);
    }
    
    public void insertArtistsAlbum() {
        artists.get(0).addNewArtistsAlbum();
    }
}


    //ALBUM
    /*public void artistsByTitle() {
        String title = "james";
        ArrayList<Artist> artister;
        artister = artists.get(0).getArtistsByTitle(title);
        for (Artist a : artister) {
            System.out.println(a.toString());
        }
    }
    
    public void artistsByGenre() {
        String genre = "james";
        ArrayList<Artist> artister;
        artister = artists.get(0).getArtistsByGenre(genre);
        for (Artist a : artister) {
            System.out.println(a.toString());
        }
    }*/