//<<<<<<< Updated upstream:Lab1/src/Controller/Connector.java

package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author fauzianordlund
 */
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
            
            /*myStmt = myConn.createStatement();
            String sql;
            sql=  "SHOW TABLES";
            myRs = myStmt.executeQuery(sql);
            myRs.close();
            myStmt.close();*/
        }
        finally{
            if(myConn!=null) {
                return true;
            }
            else
                return false;
        }
    }
    
    public void exitSafetly() throws SQLException {
       if (myConn != null) {
           myConn.close();
       } 
    }
    
    public Connection getMyConn() {
        return myConn;
    }
    
}