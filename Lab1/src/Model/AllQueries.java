/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public interface AllQueries<E> {
    
    public ArrayList<E> getMedia(String media, String searchBy, String searchWord) throws SQLException;
    public ArrayList<E> getEntertainer(String entertainer, String searchBy, String searchWord) throws SQLException;
    
    
}
