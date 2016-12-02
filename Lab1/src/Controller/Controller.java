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
import Model.Movie;
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
        String name = "Mel";
        ArrayList<Artist> tmp;
        tmp = artists.get(0).getArtistsByName(name);
        for (Artist a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void artistsByRating() {
        String rating = "11";
        ArrayList<Artist> tmp;
        tmp = artists.get(0).getArtistsByRating(rating);
        for (Artist a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByTitle() {
        System.out.println("BY TITLE");
        String title = "horse";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByTitle(title);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByGenre() {
        System.out.println("BY GENRE");
        String genre = "punk";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByGenre(genre);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByRating() {
        System.out.println("BY RATING");
        String rating = "15";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByRating(rating);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByArtist() {
        System.out.println("BY ARTIST");
        String artist = "e";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByArtist(artist);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    /*** GET MOVIE.. ***/
    public void movieByTitle() {
        System.out.println("BY TITLE");
        String title = "horse";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByTitle(title);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByGenre() {
        System.out.println("BY GENRE");
        String genre = "punk";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByGenre(genre);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByRating() {
        System.out.println("BY RATING");
        String rating = "15";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByRating(rating);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByDirector() {
        System.out.println("BY ARTIST");
        String director = "e";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByDirector(director);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }

    
    public void insertAlbum() {
        String title = "yes";
        String genre = "works";
        String rating = "6";
        Date rDate = new Date(97, 10, 23);
        
        artists.get(0).addNewAlbum(title, genre, rating, rDate);
    }
    
    public void insertArtist() {
        String fName = "Mel";
        String lName = "master";
        String rating = "5";
        artists.get(0).addNewArtist(fName, lName, rating);
    }
    
    public void insertArtistsAlbum() {
        artists.get(0).addNewArtistsAlbum();
    }
}


    //ALBUM
    /*public void artistsByTitle() {
        String title = "james";
        ArrayList<Artist> tmp;
        tmp = artists.get(0).getArtistsByTitle(title);
        for (Artist a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void artistsByGenre() {
        String genre = "james";
        ArrayList<Artist> tmp;
        tmp = artists.get(0).getArtistsByGenre(genre);
        for (Artist a : tmp) {
            System.out.println(a.toString());
        }
    }*/