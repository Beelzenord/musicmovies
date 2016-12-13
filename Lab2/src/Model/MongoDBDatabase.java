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
        Document query;
        if (searchBy.equals("album"))
            query = new Document("albums", new Document("$elemMatch", new Document("album", new Document("$regex", ".*" + searchWord + ".*"))));
        
        else if (searchBy.equals("movie"))
            query = new Document("movies", new Document("$elemMatch", new Document("movie", new Document("$regex", ".*" + searchWord + ".*"))));
        
        else 
            query = new Document(searchBy, new Document("$regex", ".*" + searchWord + ".*"));
        
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
                ArrayList<Document> media;
                
                if (entertainer.equals("artist")) {
                    media = (ArrayList<Document>)doc.get("albums");
                    for (Document d : media) {
                        String album = d.getString("album");
                        tmp.add(new Artist(sid, name, rating, nationality, album));
                    }
                }
                else {
                    media = (ArrayList<Document>)doc.get("movies");
                    for (Document d : media) {
                        String movie = d.getString("movie");
                        tmp.add(new Director(sid, name, rating, nationality, movie));
                    }
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
    
    private Document createInsertNewEntertainer(String determ, String name, String rating, String nationality, String title) {
        Document insertEntertainer;
        ArrayList<Document> list = new ArrayList();
        if (determ.equals("album")) {
            coll = mdb.getCollection("artist");
            list.add(new Document("album", title));
            insertEntertainer = new Document("name", name).
                    append("rating", rating).
                    append("nationality", nationality).
                    append("albums", list);
        }
        else {
            coll = mdb.getCollection("director");
            list.add(new Document("movie", title));
            insertEntertainer = new Document("name", name).
                    append("rating", rating).
                    append("nationality", nationality).
                    append("movies", list);
        }
        return insertEntertainer;
    }
    
    private Document createInsertEntertainer(String pKey, String determ, String title) {
        Document insertEntertainer;
        if (determ.equals("album")) {
            coll = mdb.getCollection("artist");
            ObjectId oid = new ObjectId(pKey);
            insertEntertainer = new Document("_id", oid);
        }
        else {
            coll = mdb.getCollection("director");
            ObjectId oid = new ObjectId(pKey);
            insertEntertainer = new Document("_id", oid);
        }
        return insertEntertainer;
    }
    
    private Document createInsertMediaToEntertainer(String determ, String title) {
        Document insertEntertainer;
        if (determ.equals("album")) {
            coll = mdb.getCollection("artist");
            insertEntertainer = new Document("$push", new Document("albums", new Document("album", title)));
        }
        else {
            coll = mdb.getCollection("director");
            insertEntertainer = new Document("$push", new Document("movies", new Document("movie", title)));
        }
       return insertEntertainer;
    }
    
    @Override
    public void addNewItem(String determ, String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws Exception {
        Document insertMedia = createInsertMedia(determ, title, genre, ratingAM, rDate, name);
        coll.insertOne(insertMedia);

        // only insert new if entertainer doesn't already exists
        String pKey = skipDuplicates(determ, name, ratingAD, nationality);
        if (pKey.equals("-1")) {
            Document insertNewEntertainer = createInsertNewEntertainer(determ, name, ratingAD, nationality, title);
            coll.insertOne(insertNewEntertainer);
        }
        else {
            Document insertEntertainer = createInsertEntertainer(pKey, determ, title);
            Document insertMediaToEntertainer = createInsertMediaToEntertainer(determ, title);
            coll.updateOne(insertEntertainer, insertMediaToEntertainer);
        }
    }
    
    
        private Document createSkipDuplicatesQuery(String determ, String name, String rating, String nationality) {
            Document query;
            if (determ.equals("album")) {
                coll = mdb.getCollection("artist");
                query = new Document();
                query.append("name", name).
                append("rating", rating).
                append("nationality", nationality);
            }
            else {
                coll = mdb.getCollection("director");
                query = new Document();
                query.append("name", name).
                append("rating", rating).
                append("nationality", nationality);
            }
        return query;
    }
    
    
    @Override
    public String skipDuplicates(String determ, String name, String rating, String nationality) throws Exception {
        MongoCursor<Document> cursor = null;
        String tmp = "-1";
        try {
            Document query = createSkipDuplicatesQuery(determ, name, rating, nationality);
            FindIterable find = coll.find(query);
            cursor = find.iterator();
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                ObjectId id = (ObjectId)doc.get("_id");
                String sid = id.toHexString();
                tmp = sid;
            }
            return tmp;
        } finally {
            if (cursor != null)
                cursor.close();
        }
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
