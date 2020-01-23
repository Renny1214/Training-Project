package challengeclient;

import java.net.*;
import components.User;
import java.io.*;

public class ChallengeClient {
    static String ApplicationName = "Challenge Master";
    static String IPAddress = "127.0.0.1";
    static int PortNo = 4400;
    static User LoggedUser;
    public static void main(String[] args) {
       Gateway obj = new Gateway();
       obj.setVisible(true);
      
    }

}
