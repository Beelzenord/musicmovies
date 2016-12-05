/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public interface QueryGenerator <E> {


    public ArrayList<E> search(String searchBy, String searchWord) throws SQLException;
    //public ArrayList<E> searchByPrimaryKey(ArrayList<Integer> pKey) throws SQLException;
    public ArrayList<E> searchByAll(String search1, String search2, String search3, Date search4) throws SQLException;
    public void updateRating(int primaryKey, String searchWord) throws SQLException;
    
}
