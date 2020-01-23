
package timer;

import java.util.TimerTask;
import javax.swing.JOptionPane;

public class form1 extends javax.swing.JFrame {

    class innerclass extends TimerTask
    {
        
        public void run()
        {
            JOptionPane.showMessageDialog(null, "Hello world");
        }
        
    }
    
    
    public form1() {
        initComponents();
        
        innerclass obj = new innerclass();
        java.util.Timer timer = new java.util.Timer();
        
        timer.schedule(obj, 2000, 2000);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
