/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Niklas
 */
public class MongoDBDatabase implements AllDatabaseQueries {
    private MongoDatabase mdb;
    private MongoCollection coll;
    
    public MongoDBDatabase(MongoDatabase mdb) {
        this.mdb = mdb;
    }
    
    private Document createMediaQuery(String media, String searchBy, String searchWord) {
        coll = mdb.getCollection(media);
        Document query = new Document(searchBy, new Document("$regex", ".*" + searchWord + ".*"));
        return query;
    }
    
    @Override
    public ArrayList getMedia(String media, String searchBy, String searchWord) throws Exception {
        MongoCursor<Document> cursor = null;
        ArrayList tmp = new ArrayList();
        try {
            Document query = createMediaQuery(media, searchBy, searchWord);
            
            FindIterable find = coll.find(query);
            cursor = find.iterator();
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                
                ObjectId id = (ObjectId)doc.get("_id");
                String sid = id.toHexString();
                String title = (String)doc.get("title");
                String genre = (String)doc.get("genre");
                String rating = (String)doc.get("rating");
                Date releaseDate = (Date)doc.get("releaseDate");
                String entertainer = "";
                if (media.equals("album")) {
                    entertainer = (String)doc.get("artist");
                    tmp.add(new Album(sid, title, genre, rating, releaseDate, entertainer));
                }
                else {
                    entertainer = (String)doc.get("director");
                    tmp.add(new Movie(sid, title, genre, rating, releaseDate, entertainer));
                }
            }
            return tmp;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
    
    private Document createEntertainerQuery(String entertainer, String searchBy, String searchWord) {
        coll = mdb.getCollection(entertainer);
        Document query = new Document(searchBy, new Document("$regex", ".*" + searchWord + ".*"));
        return query;
    }
    
    @Override
    public ArrayList getEntertainer(String entertainer, String searchBy, String searchWord) throws Exception {
        MongoCursor<Document> cursor = null;
        ArrayList tmp = new ArrayList();
        try {
            Document query = createEntertainerQuery(entertainer, searchBy, searchWord);
            FindIterable find = coll.find(query);
            cursor = find.iterator();
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                ObjectId id = (ObjectId)doc.get("_id");
                String sid = id.toHexString();
                String name = (String)doc.get("name");
                String rating = (String)doc.get("rating");
                String nationality = (String)doc.get("nationality");
                String media = "";
                if (entertainer.equals("artist")) {
                    media = (String)doc.get("album");
                    tmp.add(new Artist(sid, name, rating, nationality, media));
                }
                else {
                    media = (String)doc.get("movie");
                    tmp.add(new Director(sid, name, rating, nationality, media));
                }
            }
            return tmp;
        
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
    
    
    
    private Document createInsertMedia(String determ, String title, String genre, String rating, Date rDate, String entertainer) {
        coll = mdb.getCollection(determ);
        Document insertMedia;
        if (determ.equals("album")) {
            insertMedia = new Document("title", title).
                    append("genre", genre).
                    append("rating", rating).
                    append("releaseDate", rDate).
                    append("artist", entertainer);
        }
        else {
            insertMedia = new Document("title", title).
                    append("genre", genre).
                    append("rating", rating).
                    append("releaseDate", rDate).
                    append("director", entertainer);
        }
        return insertMedia;
    }
    
    private Document createInsertEntertainer(String determ, String name, String rating, String nationality, String title) {
        Document insertEntertainer;
        if (determ.equals("album")) {
            coll = mdb.getCollection("artist");
            insertEntertainer = new Document("name", name).
                    append("rating", rating).
                    append("nationality", nationality).
                    append("album", title);
        }
        else {
            coll = mdb.getCollection("director");
            insertEntertainer = new Document("name", name).
                    append("rating", rating).
                    append("nationality", nationality).
                    append("movie", title);
        }
        return insertEntertainer;
    }
    
    @Override
    public void addNewItem(String determ, String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws Exception {
        Document insertMedia = createInsertMedia(determ, title, genre, ratingAM, rDate, name);
        coll.insertOne(insertMedia);

        Document insertEntertainer = createInsertEntertainer(determ, name, ratingAD, nationality, title);
        coll.insertOne(insertEntertainer);
    }
    
    @Override
    public int skipDuplicates(String determ, String name, String rating, String nationality) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Document createUpdateKey(String item, String primaryKey) {
        coll = mdb.getCollection(item);
        ObjectId oid = new ObjectId(primaryKey);
        Document query = new Document("_id", oid);
        return query;
    }
    
    @Override
    public void updateRating(String item, String primaryKey, String rating) throws Exception {
        Document updateKey = createUpdateKey(item, primaryKey);
        Document newRating = new Document("$set", new Document("rating", rating));
        coll.updateOne(updateKey, newRating);
    }
    
}
