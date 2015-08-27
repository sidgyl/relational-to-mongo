import java.util.ArrayList;

/**
 * Created by sidharthgoyal on 4/28/14.
 */
public class Employee {
    public String Lname;
    public String Salary;
    public String Dname;
    public ArrayList <String> Pname = new ArrayList<String>();
    public ArrayList <String> hours= new ArrayList<String>();
    public ArrayList <String> dependent= new ArrayList<String>();
    public ArrayList <String> relation= new ArrayList<String>();

    public Employee(String a,String b,String c,String d,String e,String f,String g){
        Lname=a;
        Salary=b;
        Dname=c;
        if (d==null){
            d="none";
        }
        if (e==null){
            e="none";
        }
        if (f==null){
            f="none";
        }
        if (g==null){
            g="none";
        }
        Pname.add(d);
        hours.add(e);
        dependent.add(f);
        relation.add(g);
    }

}
