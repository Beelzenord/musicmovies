/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.Date;
import org.bson.types.ObjectId;

/**
 *
 * @author Niklas
 */
public class MongoDatabase implements AllDatabaseQueries {
    private DB db;
    private DBCollection coll;
    
    public MongoDatabase(DB db) {
        this.db = db;
    }
    
    private BasicDBObject createMediaQuery(String media, String searchBy) {
        coll = db.getCollection(media);
        BasicDBObject query = new BasicDBObject("title", "Thriller");
        
        return query;
    }

    @Override
    public ArrayList getMedia(String media, String searchBy, String searchWord) throws Exception {
        
        BasicDBObject query = createMediaQuery(media, searchBy);
        DBCursor cursor = coll.find(query);
        ArrayList tmp = new ArrayList();
           while(cursor.hasNext()) {
               ObjectId id = (ObjectId)cursor.next().get("_id");
               String title = (String)cursor.curr().get("title");
               String genre = (String)cursor.curr().get("genre");
               String rating = (String)cursor.curr().get("rating");
               Date releaseDate = (Date)cursor.curr().get("releaseDate");
               String artist = (String)cursor.curr().get("artist");
                       
               System.out.println(id.toString());
               String sa = id.toHexString();
               
               tmp.add(new Album(sa, title, genre
               , rating, releaseDate, artist));
               
               //System.out.println(newalbum.toString());
           }
           return tmp;
    }

    @Override
    public ArrayList getEntertainer(String entertainer, String searchBy, String searchWord) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addNewItem(String determ, String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws Exception {

    }

    @Override
    public int skipDuplicates(String determ, String name, String rating, String nationality) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRating(String item, String primaryKey, String rating) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
