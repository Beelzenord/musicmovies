
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author fauzianordlund
 */
public class Model {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        String user;
        String pass;
    public Model(){
        
    }
    public void validateUserIdentity(String userName, String passWord) throws SQLException{
        System.out.println("UserName: " + userName + ", " + "password: " + passWord);
        this.user = userName;
        this.pass = passWord;
        getInside();
    }
    private void getInside() throws SQLException{
       try{
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kTunes?useSSL=false", user, pass);
            System.out.println("V has come to");
            
            myStmt = myConn.createStatement();
            String sql;
            sql=  "SHOW TABLES";
            myRs = myStmt.executeQuery(sql);
            
            myRs.close();
            myStmt.close();
            myConn.close();
        }
        finally{
            try{
                if(myConn!=null)
                    myConn.close();
                    System.out.println("Connection closed");
            }
            catch(SQLException e){
                
            }
        }
    }
    
}
