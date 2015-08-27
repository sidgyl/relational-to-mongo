import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.*;

import java.util.ArrayList;

/**
 * Created by sidharthgoyal on 4/1/14.
 */
public class MainClass {
    public static void main(String[] args){

        ArrayList <String> Pname = new ArrayList<String>();
        Pname.add("name1");
        Pname.add("name2");
        Pname.add("name3");
        ArrayList <String> hours = new ArrayList<String>();
        hours.add("hours1");
        hours.add("hours2");
        hours.add("hours3");

        try {
            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("DatabasesII");

            DBCollection collection = db.getCollection("Employee");

            System.out.println("BasicDBObject example...");
            BasicDBObject document = new BasicDBObject();
            document.put("Fname", "John");
            document.put("Lname", "Doe");

            BasicDBObject documentDetail = new BasicDBObject();
            for (String s : Pname)



/*
            BasicDBObject documentDetail = new BasicDBObject();
            documentDetail.put("records", 99);
            documentDetail.put("index", "vps_index1");
            documentDetail.put("active", "true");
            document.put("detail", documentDetail);
*/
            collection.insert(document);

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
