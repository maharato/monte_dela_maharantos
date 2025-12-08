/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Circle.monte_dela_maharantos;

import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author Ahmed
 */
public class PiExperimentRunner_form extends javax.swing.JFrame {

    SimulationConfig config;

    double pi_seq = 0;
    double pi_par = 0;
    long run_seq = 0;
    long run_par = 0;
    String type;

    double PiExperiment_sequntial(int totalpoints, int numTasks, int numthreads) {
        SimulationConfig config = new SimulationConfig(totalpoints, numTasks, numthreads);
        SequentialPiEstimator seq = new SequentialPiEstimator();
        double estimatePi_Result = seq.estimatePi(config);
        return estimatePi_Result;
    }

    double PiExperiment_parallel(int totalpoints, int numTasks, int numthreads) {
        SimulationConfig config = new SimulationConfig(totalpoints, numTasks, numthreads);
        ParallelPiEstimator par = new ParallelPiEstimator();
        double estimatePi_Result = par.estimatePi(config);
        return estimatePi_Result;
    }

    boolean validator() {

        String pointsStr = number_of_points.getText().trim();
        String tasksStr = number_of_tasks.getText().trim();
        String threadsStr = number_of_theards.getText().trim();

        if (pointsStr.isEmpty() || tasksStr.isEmpty() || threadsStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "❌ All fields must be filled!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!pointsStr.matches("\\d+") || !tasksStr.matches("\\d+") || !threadsStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "❌ Please enter valid positive numbers only!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Integer.parseInt(pointsStr) <= 0
                || Integer.parseInt(tasksStr) <= 0
                || Integer.parseInt(threadsStr) <= 0) {
            JOptionPane.showMessageDialog(this,
                    "❌ All values must be greater than zero!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
     boolean validator(String Seq) {

        String pointsStr = number_of_points.getText().trim();
        String tasksStr = number_of_tasks.getText().trim();
        String threadsStr = number_of_theards.getText().trim();

        if (pointsStr.isEmpty() ) {
            JOptionPane.showMessageDialog(this,
                    "❌ All fields must be filled!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!pointsStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "❌ Please enter valid positive numbers only!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Integer.parseInt(pointsStr) <= 0) {
            JOptionPane.showMessageDialog(this,
                    "❌ All values must be greater than zero!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    void fill_Table(double estimatePi_Result, long runTime, String version) {

        DefaultTableModel Table = new DefaultTableModel();
        String[] col = {"Run #", "Version", "Pi Value", "RunTime (ms)"};
        Table.setColumnIdentifiers(col);

        Table.addRow(new Object[]{
            "1",
            version,
            String.format("%.6f", estimatePi_Result),
            String.format("%d", runTime)
        });

        jTable1.setModel(Table);
    }

    void fill_Table(double seqResult, long seqTime,
            double parResult, long parTime) {

        DefaultTableModel Table = new DefaultTableModel();

        String[] columnNames = {
            "Run #",
            "Version",
            "Pi Value",
            "RunTime (ms)"
        };
        Table.setColumnIdentifiers(columnNames);

        Table.addRow(new Object[]{
            "1",
            "Sequential",
            String.format("%.6f", seqResult),
            String.format("%d", seqTime)
        });

        Table.addRow(new Object[]{
            "2",
            "Parallel",
            String.format("%.6f", parResult),
            String.format("%d", parTime)
        });

        jTable1.setModel(Table);
    }
    public void initHome(String type){
        switch (type) {
            case "par":

                Run_suq.setVisible(false);
                Both.setVisible(false);

                break;
            case "seq":

             
                
                number_of_theards.setVisible(false);
                number_of_tasks.setVisible(false);
                jLabel3.setVisible(false);
                jLabel4.setVisible(false);
                Both.setVisible(false);
                Run_par.setVisible(false);

                break;
            case "both":

                Run_suq.setVisible(false);
                
                break;

            default:
                throw new AssertionError();
        }
        setVisible(true);
    
    }

    public PiExperimentRunner_form() {
        initComponents();
        setVisible(false);
     
        jFrame2.setResizable(false);
        jFrame2.setSize(700, 600);
        jFrame2.setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setResizable(false);
        jFrame1.setResizable(false);
        jFrame1.setSize(1000, 900);
        jFrame1.setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jFrame2 = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        Parr_button = new javax.swing.JButton();
        seq_button = new javax.swing.JButton();
        both_button = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        number_of_points = new javax.swing.JTextField();
        number_of_theards = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Both = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        number_of_tasks = new javax.swing.JTextField();
        Run_suq = new javax.swing.JButton();
        Run_par = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Pi_value", "RunTime"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(204, 255, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("DRAW");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 255, 0));
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 153, 153));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setText("Table");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(161, 161, 161)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(254, 254, 254)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parr_button.setText("Run par");
        Parr_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Parr_buttonActionPerformed(evt);
            }
        });

        seq_button.setText("run seq");
        seq_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seq_buttonActionPerformed(evt);
            }
        });

        both_button.setText("run both");
        both_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                both_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(seq_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(both_button, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(Parr_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(Parr_button)
                .addGap(60, 60, 60)
                .addComponent(seq_button)
                .addGap(85, 85, 85)
                .addComponent(both_button)
                .addContainerGap(199, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setForeground(new java.awt.Color(0, 102, 102));

        number_of_points.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number_of_pointsActionPerformed(evt);
            }
        });

        number_of_theards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number_of_theardsActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("number of points");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("number of theards");

        Both.setText("Run both");
        Both.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BothActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("number of tasks");

        number_of_tasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number_of_tasksActionPerformed(evt);
            }
        });

        Run_suq.setText("Run suquential");
        Run_suq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Run_suqActionPerformed(evt);
            }
        });

        Run_par.setText("Run parallel");
        Run_par.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Run_parActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(153, 204, 0));
        jSeparator1.setForeground(new java.awt.Color(102, 255, 102));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("MONTE CARLO ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Both, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Run_suq, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(Run_par, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(number_of_points, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(number_of_theards, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(number_of_tasks, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(number_of_points, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(number_of_theards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(number_of_tasks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(Run_suq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Both)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Run_par)
                .addContainerGap(337, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        MonteCarloPiScatterFrame frame = new MonteCarloPiScatterFrame();
        switch (type) {
            case "par":

                frame.setParallelOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_par);
                break;

            case "seq":
                frame.setSequentialOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_seq);
                break;

            case "both":
                frame.setConfig(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_seq, pi_par);
                break;

        }

        frame.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setVisible(true);
        jFrame1.setVisible(false);
        number_of_points.setText("");
        number_of_tasks.setText("");
        number_of_theards.setText("");


    }//GEN-LAST:event_jButton2ActionPerformed

    private void seq_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seq_buttonActionPerformed
        // TODO add your handling code here:
         initHome("seq");
    }//GEN-LAST:event_seq_buttonActionPerformed

    private void both_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_both_buttonActionPerformed
        // TODO add your handling code here:
         initHome("both");
    }//GEN-LAST:event_both_buttonActionPerformed

    private void Parr_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Parr_buttonActionPerformed
        // TODO add your handling code here:
        initHome("par");
    }//GEN-LAST:event_Parr_buttonActionPerformed

    private void Run_parActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Run_parActionPerformed
        // TODO add your handling code here:
        if (!validator()) {
            return;
        }
        SimulationConfig con = new SimulationConfig(Integer.parseInt(number_of_points.getText()), Integer.parseInt(number_of_tasks.getText()), Integer.parseInt(number_of_theards.getText()));

        config = con;
        long startTime = System.currentTimeMillis();
        double parallel_Resultt = PiExperiment_parallel(Integer.parseInt(number_of_points.getText()), Integer.parseInt(number_of_tasks.getText()), Integer.parseInt(number_of_theards.getText()));
        long endTime = System.currentTimeMillis();
        long runTime_parallel = endTime - startTime;
        pi_par = parallel_Resultt;
        run_par = runTime_parallel;
        type = "par";
        setVisible(false);
        jFrame1.setVisible(true);
        fill_Table(parallel_Resultt, runTime_parallel, "parallel");

    }//GEN-LAST:event_Run_parActionPerformed

    private void Run_suqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Run_suqActionPerformed
        if (!validator("love is love")) {
            return;
        }
        SimulationConfig con = new SimulationConfig(Integer.parseInt(number_of_points.getText()),0,0);
        config = con;
        long startTime = System.currentTimeMillis();
        double sequntial_Resultt = PiExperiment_sequntial(Integer.parseInt(number_of_points.getText()), 0,0);
        long endTime = System.currentTimeMillis();
        long runTime_sequntial = endTime - startTime;
        pi_seq = sequntial_Resultt;
        run_seq = runTime_sequntial;
        type = "seq";
        setVisible(false);
        jFrame1.setVisible(true);
        fill_Table(sequntial_Resultt, runTime_sequntial, "sequntial");
    }//GEN-LAST:event_Run_suqActionPerformed

    private void number_of_tasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number_of_tasksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number_of_tasksActionPerformed

    private void BothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BothActionPerformed

        if (!validator()) {
            return;
        }
        SimulationConfig con = new SimulationConfig(Integer.parseInt(number_of_points.getText()), Integer.parseInt(number_of_tasks.getText()), Integer.parseInt(number_of_theards.getText()));
        config = con;
        long startTime = System.currentTimeMillis();
        double sequntial_Resultt = PiExperiment_sequntial(Integer.parseInt(number_of_points.getText()), Integer.parseInt(number_of_tasks.getText()), Integer.parseInt(number_of_theards.getText()));
        long endTime = System.currentTimeMillis();
        long runTime_sequntial = endTime - startTime;
        long startTime2 = System.currentTimeMillis();
        double parallel_Resultt = PiExperiment_parallel(Integer.parseInt(number_of_points.getText()), Integer.parseInt(number_of_tasks.getText()), Integer.parseInt(number_of_theards.getText()));
        long endTime2 = System.currentTimeMillis();
        long runTime_parallel = endTime2 - startTime2;
        pi_seq = sequntial_Resultt;
        pi_par = parallel_Resultt;
        run_seq = runTime_sequntial;
        run_par = runTime_parallel;
        type = "both";
        setVisible(false);
        jFrame1.setVisible(true);
        fill_Table(sequntial_Resultt, runTime_sequntial, parallel_Resultt, runTime_parallel);

    }//GEN-LAST:event_BothActionPerformed

    private void number_of_theardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number_of_theardsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number_of_theardsActionPerformed

    private void number_of_pointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number_of_pointsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number_of_pointsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PiExperimentRunner_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PiExperimentRunner_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PiExperimentRunner_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PiExperimentRunner_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PiExperimentRunner_form().jFrame2.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Both;
    private javax.swing.JButton Parr_button;
    private javax.swing.JButton Run_par;
    private javax.swing.JButton Run_suq;
    private javax.swing.JButton both_button;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField number_of_points;
    private javax.swing.JTextField number_of_tasks;
    private javax.swing.JTextField number_of_theards;
    private javax.swing.JButton seq_button;
    // End of variables declaration//GEN-END:variables
}
