/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Controller.Controller;
import Model.QueryExecutor;


import Controller.Controller;
import Model.QueryExecutor;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Niklas
 */
public class Test {
    private Connection con;
    private QueryExecutor exec;
    
    public Test() throws Exception {
        String user = "root"; // user name
        String pwd = "root"; // password 
        String database = "MediaLibrary"; // the name of the database in your DBMS
        String server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8";

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //java.sql.Driver d=new com.mysql.jdbc.Driver();
            con = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connected!");

            //executeQuery(con, "SELECT * FROM Employee");
        } 
        finally {
        }
        
        exec = new QueryExecutor(con);
        //Controller controller = new Controller(exec);
        
        /***** RUN QUERIES HERE *****/
        //controller.artistsByName(); // with thread
        //controller.artistsByRating();
        
        //controller.insertAlbum();
        //controller.insertArtist();
        //controller.insertAlbumDirectory();
        
        /*controller.albumByTitle();
        controller.albumByGenre();
        controller.albumByRating();
        controller.albumByArtist();
        
        controller.directorsByName();
        controller.directorsByRating();
        
        controller.insertMovie();
        controller.insertDirector();
        controller.insertMovieDirectory();
        
        controller.movieByTitle();
        controller.movieByGenre();
        controller.movieByRating();
        controller.movieByDirector();*/  
        con.close();
    }
    
}
