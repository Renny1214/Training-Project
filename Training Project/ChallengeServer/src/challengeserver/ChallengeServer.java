package challengeserver;

import components.Challenge;
import java.io.*;
import java.net.*;
import components.User;
import java.util.*;
import components.Command;

public class ChallengeServer {

    public static void main(String[] args) {
        ArrayList<User> LoggedUsers = new ArrayList<User>();

        System.out.println("Server started ... ");

        try {
            ServerSocket serversocket = new ServerSocket(4400);
            while (true) {

                Socket clientsocket = serversocket.accept();
                ObjectInputStream ois = new ObjectInputStream(clientsocket.getInputStream());

                Command cmd = (Command) ois.readObject();

                if (cmd.CommandName.equals("Register")) {

                    User obj = (User) cmd;

                    datalayer.DALUser objDAL = new datalayer.DALUser();
                    objDAL.register(obj);
                    objDAL.closeConnection();

                } else if (cmd.CommandName.equals("Login")) {

                    User obj = (User) cmd;

                    datalayer.DALUser objDAL = new datalayer.DALUser();
                    User user = objDAL.authenticate(obj);

                    if (user.UserNo > 0) {
                        LoggedUsers.add(user);
                    }

                    ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
                    oos.writeObject(user);

                } else if (cmd.CommandName.equals("ChangePassword")) {
                    User obj = (User) cmd;

                    datalayer.DALUser objDAL = new datalayer.DALUser();
                    objDAL.changePassword(obj);
                    objDAL.closeConnection();
                } else if (cmd.CommandName.equals("GetUsers")) {
                    User obj = (User) cmd;

                    ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
                    oos.writeObject(LoggedUsers);
                } else if (cmd.CommandName.equals("SendChallenge")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objChal = new datalayer.DALChallenge();
                    objChal.sendchallenge(obj);
                    
                    ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
                    oos.writeObject(obj);
                } else if (cmd.CommandName.equals("Reject")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objChal = new datalayer.DALChallenge();
                    objChal.RejectChallenge(obj);
                } else if (cmd.CommandName.equals("AnyChallenge")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objChal = new datalayer.DALChallenge();
                    Challenge feedback = objChal.AnyChallenge(obj);
                    ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
                    oos.writeObject(feedback);
                }else if(cmd.CommandName.equals("Accept")){
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objChal = new datalayer.DALChallenge();
                    objChal.AcceptChallenge(obj);
                }else if(cmd.CommandName.equals("CheckChallengeStatus"))
                {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objChal = new datalayer.DALChallenge();
                    Challenge feedback = objChal.CheckChallenge(obj);
                    ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
                    oos.writeObject(feedback);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
