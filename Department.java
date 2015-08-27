import java.util.ArrayList;

/**
 * Created by sidharthgoyal on 4/28/14.
 */
public class Department {
    public String Dname;
    public String Lname;
    public ArrayList <String> dlocation = new ArrayList <String>();

    public Department(String a, String b, String c){
        Dname = a;
        Lname = b;
        dlocation.add(c);
    }

}
