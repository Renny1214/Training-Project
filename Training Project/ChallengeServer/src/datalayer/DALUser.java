package datalayer;

import java.sql.*;
import components.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextField;

public class DALUser {

    private Connection con = null;

    public DALUser() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://desktop-jbckjcd\\sqlexpress:57367;database=project;userName=sa;password=renny");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void register(User user) {
        try {

            PreparedStatement ps = con.prepareStatement("insert into Users values(?,?,?)");
            ps.setString(1, user.Name);
            ps.setString(2, user.UserId);
            ps.setString(3, user.Password);

            ps.execute();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void changePassword(User user)
    {
        try{
            PreparedStatement ps = con.prepareStatement("update Users set Password=? where UserNo=?");
            ps.setString(1,user.Password);
            ps.setInt(2,user.UserNo );
            ps.execute();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public components.User authenticate(User user) {
        User feedback = new User();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Users where UserId = ? and Password = ? ");
            ps.setString(1, user.UserId);
            ps.setString(2, user.Password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                feedback.UserNo = rs.getInt("UserNo");
                feedback.Name = rs.getString("Name");
                feedback.UserId = rs.getString("UserId");
                feedback.Password = rs.getString("Password");
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return feedback;
    }//Authenticate

    public void closeConnection() {
        try {
            con.close();
            con = null;

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
