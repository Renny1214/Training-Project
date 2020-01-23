
package components;
import java.util.*;
import java.io.Serializable;

public class Questions implements Serializable {
    public int QuestionId;
    public String QuestionText;
    
    public ArrayList<Option> options = new ArrayList <Option>();
    
}
