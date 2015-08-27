/**
 * Created by sidharthgoyal on 4/3/14.
 */
import java.sql.*;
import java.lang.*;
import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.*;

public class MySQLconect {


    public static int checkExistence(String input, ArrayList <String> A ){
        for (String s : A)
            if (s.equals(input))
                return 1;

        return 0;
    }

    public static void main(String[] args) {
        ArrayList <Employee> allEmployees = new ArrayList<Employee>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project2", "root", "");
            Statement stmt = null;
            String query = "select Lname, Salary, Dname, Pname, Hours, Dependent_name, Relationship from view3 left outer JOIN DEPENDENT on view3.SSN = DEPENDENT.Essn";
            try{
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    String Lname = rs.getString("Lname");
                    String Salary = rs.getString("Salary");
                    String Dname = rs.getString("Dname");
                    String Pname = rs.getString("Pname");
                    String Hours = rs.getString("Hours");
                    String Dependent = rs.getString("Dependent_name");
                    String relation = rs.getString("Relationship");

                    if (Dependent == null)
                        Dependent = "none";
                    if (relation == null)
                        relation = "none";

                    Employee E = new Employee(Lname,Salary,Dname,Pname,Hours,Dependent,relation);

                    if (allEmployees.isEmpty())
                        allEmployees.add(E);
                    else {

                        Employee lastEmpl = allEmployees.get(allEmployees.size()-1);
                        if (lastEmpl.Lname.equals(E.Lname)){
                            if (checkExistence(Pname, lastEmpl.Pname)==0){
                                lastEmpl.Pname.add(Pname);
                                lastEmpl.hours.add(Hours);
                            }
                            System.out.println("name: "+ Lname);
                            System.out.println("Pname: "+ Pname);
                            System.out.println("Dependent: " + Dependent);
                            if (checkExistence(Dependent, lastEmpl.dependent)==0){
                                lastEmpl.dependent.add(Dependent);
                                lastEmpl.relation.add(relation);
                            }

                        }else
                            allEmployees.add(E);
                    }

                }

                try {
                    Mongo mongo = new Mongo("localhost", 27017);
                    DB db = mongo.getDB("DatabasesII");



                    for (Employee E : allEmployees){
                        DBCollection collection = db.getCollection("Employees");
                        BasicDBObject document = new BasicDBObject();
                        document.put("Lname", E.Lname);
                        document.put("Salary", Integer.parseInt(E.Salary));
                        document.put("Depatment Name", E.Dname);

                        int len = E.Pname.size();
                        BasicDBList allProjects = new BasicDBList();
                        for (int i=0; i<len; i++){
                            BasicDBObject projectDetail = new BasicDBObject();
                            projectDetail.put("Pname", E.Pname.get(i));
                            projectDetail.put("Hours", Integer.parseInt(E.hours.get(i)));
                            allProjects.add(projectDetail);

                        }
                        document.put("Projects", allProjects);

                        BasicDBList allDependents = new BasicDBList();
                        if (!E.dependent.get(0).equals("none")) {
                            int len1 = E.dependent.size();
                            for (int i = 0; i < len1; i++) {
                                BasicDBObject projectDetail1 = new BasicDBObject();
                                projectDetail1.put("Name", E.dependent.get(i));
                                projectDetail1.put("Relation", E.relation.get(i));
                                allDependents.add(projectDetail1);
                            }
                            document.put("dependent", allDependents);
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
                if (stmt != null)
                    stmt.close();
            }
        } catch (Exception e) {
            // handle any errors
            e.printStackTrace();

        }
    }
}

