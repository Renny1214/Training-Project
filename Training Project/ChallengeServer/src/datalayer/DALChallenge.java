package datalayer;

import components.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Statement;

public class DALChallenge {

    private Connection con = null;

    public DALChallenge() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://desktop-jbckjcd\\sqlexpress:57367;database=project;userName=sa;password=renny");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void RejectChallenge(Challenge obj) {
        try {
            PreparedStatement ps = con.prepareStatement("update Challenges set Status=2 where ChallengeId = ?");
            ps.setInt(1, obj.ChallengeId);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AcceptChallenge(Challenge obj) {
        try {
            PreparedStatement ps = con.prepareStatement("update Challenges set Status=1 where ChallengeId = ?");
            ps.setInt(1, obj.ChallengeId);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public components.Challenge CheckChallenge(Challenge obj) {
        Challenge feedback = new Challenge();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Challenges where ChallengeId = ? ");
            ps.setInt(1, obj.ChallengeId);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            if (rs.next() == true) {
                feedback.Status = rs.getInt("Status");
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return feedback;
    }

    public void sendchallenge(Challenge obj) {
        try {
            CallableStatement cs = con.prepareCall("{call AddChallenge(?,?,?,?,?)}");

            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setInt(2, obj.CategoryId);
            cs.setInt(3, obj.FromUserNo);
            cs.setInt(4, obj.ToUserNo);
            cs.setInt(5, obj.Status);
            cs.execute();

            obj.ChallengeId = cs.getInt(1);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public components.Challenge AnyChallenge(Challenge obj) {
        Challenge feedback = new Challenge();
        try {
            PreparedStatement ps = con.prepareStatement("select C.ChallengeId , C.ChallengeDate , C.FromUserNo , FU.Name as [FromUserName], C.ToUserNo,TU.Name as[ToUserName], C.Status,C.CategoryId , Cat.CategoryName\n"
                    + "From Challenges as [C] , Categories as [Cat] ,Users as [FU], Users as [TU]\n"
                    + "where C.CategoryId = Cat.CategoryId and C.FromUserNo = FU.UserNo and C.ToUserNo = TU.UserNo and C.ToUserNo = ? and Status = 0");
            ps.setInt(1, obj.ToUserNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == true) {
                feedback.ChallengeId = rs.getInt("ChallengeId");
                feedback.CategoryId = rs.getInt("CategoryId");
                feedback.FromUserNo = rs.getInt("FromUserNo");
                feedback.ChallengeDate = rs.getString("ChallengeDate");
                feedback.ToUserNo = rs.getInt("ToUserNo");
                feedback.Status = rs.getInt("Status");
                feedback.FromUserName = rs.getString("FromUserName");
                feedback.ToUserName = rs.getString("ToUserName");
                feedback.CategoryName = rs.getString("CategoryName");

            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return feedback;
    }

    public ArrayList<Questions> getQuestion(int CategoryId) {
        ArrayList<Questions> obj = new ArrayList<Questions>();

        try {
            PreparedStatement ps = con.prepareStatement("select top 5 * from Questions where CategoryId=?  order by newId(()");
            ps.setInt(1, CategoryId);
            ResultSet rs = ps.executeQuery();
            Questions ques;
            String IDS = "";
            while (rs.next() == true) {
                ques = new Questions();
                ques.QuestionId = rs.getInt("QuestionId");
                ques.QuestionText = rs.getString("QuestionText");
                obj.add(ques);
                IDS = IDS + ques.QuestionId + ",";
            }//while 

            rs.close();
            IDS = IDS.substring(0, IDS.length() - 1);

            Statement st = con.createStatement();
            rs = st.executeQuery("select * from Options where QuestionId in(" + IDS + ")");
            Option opt = null;
            int counter = -1;
            int QuestionId = 0;
            while (rs.next()) {
                opt = new Option();
                opt.OptionId = rs.getInt("OptionId");
                opt.OptionText = rs.getString("OptionText");
                opt.QuestionId = rs.getInt("QuestionId");
                if (opt.OptionId != QuestionId) {
                    counter++;
                    QuestionId = opt.QuestionId;

                }
            }
            rs.close();
            con.close();
        } catch (Exception E) {
            System.out.println(E);
        }
        return obj;
    }
}
