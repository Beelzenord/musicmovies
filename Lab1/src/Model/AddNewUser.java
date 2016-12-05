package Model;

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
