<<<<<<< Updated upstream:Lab1/src/Controller/Connector.java

package Controller;
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
>>>>>>> Stashed changes:Lab1/src/Model/Model.java

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fauzianordlund
 */
<<<<<<< Updated upstream:Lab1/src/Controller/Connector.java
public class Connector {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        String user;
        String pass;
        public Connector(){
    }
    
    public boolean validateUserIdentity(String userName, String passWord) throws SQLException{
        System.out.println("UserName: " + userName + ", " + "password: " + passWord);
        this.user = userName;
        this.pass = passWord;
        if (getInside())
            return true;
        else
            return false;
    }
    private boolean getInside() throws SQLException{
        try{
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kTunes?useSSL=false", user, pass);
            System.out.println("V has come to");
            
            myStmt = myConn.createStatement();
            String sql;
            sql=  "SHOW TABLES";
            myRs = myStmt.executeQuery(sql);
            
            myRs.close();
            myStmt.close();
            //myConn.close();
=======
public class AddNewUser implements InsertGenerator{
    private Connection con;
    private Statement sm;
    private PreparedStatement searchForUser;
    private PreparedStatement insertUserPass;
    private ResultSet r = null;
    private int UserPKEY;
    private int artistPKEY;
    private String name;
    private String pass;
    private Model model;
    private String test;
    public AddNewUser(String name, String pass,Connection con) throws SQLException{
        // initPreparedStatements();
        this.name = name;
        this.pass = pass;
        System.out.println("New User Created");
        this.con = con;
        if(con.isClosed()){
            System.out.println("no effect");
>>>>>>> Stashed changes:Lab1/src/Model/Model.java
        }
        initPreparedStatements();
        //  System.out.println(name);
    }
    public void initPreparedStatements() throws SQLException{
        String s =   " SELECT name FROM T_User WHERE name LIKE ?";
        searchForUser = con.prepareStatement(s);
        
        ResultSet rs = null;
        try {
            searchForUser.setString(1,"%"+ name+"%");
            rs = searchForUser.executeQuery();
            while (rs.next()){
                test = rs.getString("name");
                System.out.println(test);
            }
        }
        finally {
            if (rs != null)
                rs.close();
            if (searchForUser != null)
                searchForUser.close();
        }
        
        
    }
    
    
    @Override
    public void addNew(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
