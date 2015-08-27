/**
 * Created by sidharthgoyal on 4/28/14.
 */

import java.sql.*;
import java.lang.*;
import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.*;



public class DepartmentInput {

    public static void main(String[] args) {

        ArrayList <Department> allDepartments = new ArrayList<Department>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project2", "root", "");
            Statement stmt1 = null;
            String query1 = "SELECT Dname, Lname, Dlocation FROM view5 LEFT OUTER JOIN DEPT_LOCATION on view5.Dnumber = DEPT_LOCATION.Dnumber";
            try{
                stmt1 = conn.createStatement();
                ResultSet rs = stmt1.executeQuery(query1);
                while (rs.next()){
                    String Dname = rs.getString("Dname");
                    String Lname = rs.getString("Lname");
                    String Dlocation = rs.getString("Dlocation");

                    Department D = new Department(Dname, Lname, Dlocation);

                    if (allDepartments.isEmpty())
                        allDepartments.add(D);
                    else {
                        Department lastdept = allDepartments.get(allDepartments.size()-1);
                        if (lastdept.Dname.equals(D.Dname)){
                            lastdept.dlocation.add(Dlocation);
                        }else
                            allDepartments.add(D);
                    }

                }

                try {
                    Mongo mongo = new Mongo("localhost", 27017);
                    DB db = mongo.getDB("DatabasesII");

                    for (Department D : allDepartments){
                        DBCollection collection = db.getCollection("Departments");
                        BasicDBObject document = new BasicDBObject();
                        document.put("Dname",D.Dname);
                        if (D.Lname != null)
                            document.put("Manager Name", D.Lname);

                        if (D.dlocation.get(0) != (null)) {
                            int len1 = D.dlocation.size();
                            BasicDBObject allLocations = new BasicDBObject();
                            for (int i = 0; i < len1; i++) {
                                document.put("Locations",D.dlocation);
                            }
                        }
                        collection.insert(document);
                        System.out.println("Inserting into MongoDB");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                if (stmt1 != null)
                    stmt1.close();
            }
        }catch (Exception e) {
            // handle any errors
            e.printStackTrace();

        }
    }
}
