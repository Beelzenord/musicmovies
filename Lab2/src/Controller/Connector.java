//<<<<<<< Updated upstream:Lab1/src/Controller/Connector.java

package Controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;
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
        private Connection myConn = null;
        private Statement myStmt = null;
        private ResultSet myRs = null;
        private String user;
        private String pass;
        private MongoClient mongoClient;
        private DB db;
        
    public Connector(){
        
    }
    
    public boolean getInsideMongo() {
        mongoClient = new MongoClient( "localhost" , 27017 );
        
        db = mongoClient.getDB( "test" );
        if (db != null)
            return true;
        else
            return false;
    }
    
    public DB getMongoDB() {
        return db;
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