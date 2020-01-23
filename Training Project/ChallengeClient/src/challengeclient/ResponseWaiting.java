package challengeclient;

import components.Challenge;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class ResponseWaiting extends javax.swing.JDialog {

    Challenge SentChallenge = null;

    class innerclass extends TimerTask {

        public void run() {
            Challenge challenge = new Challenge();
            challenge.ToUserNo = ChallengeClient.LoggedUser.UserNo;
            challenge.CommandName = "CheckChallengeStatus";
            try {
                Socket socket = new Socket(ChallengeClient.IPAddress, ChallengeClient.PortNo);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(challenge);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Challenge feedback = (Challenge) ois.readObject();

                //socket.close();
                if (feedback.Status == 1) {
                    jLabel1.setText("Your Challenge has been accepted");
                } else if (feedback.Status == 2) {
                    jLabel1.setText("Your challenge has been rejected");
                }
               // socket.close();

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public ResponseWaiting(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void start(Challenge challenge) {
        ResponseWaiting.innerclass obj = new ResponseWaiting.innerclass();
        java.util.Timer timer = new java.util.Timer();

        try {
            Socket socket = new Socket(ChallengeClient.IPAddress, ChallengeClient.PortNo);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(challenge);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            SentChallenge = (Challenge) ois.readObject();

        } catch (Exception E) {
            System.out.println(E);
        }

        this.setVisible(true);
        timer.schedule(obj, 5000, 2000);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Wait for response from opponent");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel1)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel1)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
