/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Circle.monte_dela_maharantos;

import java.awt.Panel;
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
    static int i = 1;
    static int counter_seq=1;
    static int counter_par=1;
    static double avg_Error;
    static double avg_ErrorSEQ;
    static double error;
    static double errorSeq;
  
  
    DefaultTableModel Table = new DefaultTableModel();

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
    void update_avgErrorSeq(){
    
    avg_ErrorSEQ=errorSeq/counter_seq;
    avgErrorseq.setText(String.valueOf(avg_ErrorSEQ));
    counter_seq++;
    
    }
    void update_avgErrorpar(){
    
    avg_Error=error/counter_par;
    avgError.setText(String.valueOf(avg_Error));
    counter_par++;
    
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

        if (pointsStr.isEmpty()) {
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

        String[] col = {"Run #", "Version", "Pi Value", "RunTime (ms)"};
        Table.setColumnIdentifiers(col);

        Table.addRow(new Object[]{
            String.valueOf(i),
            version,
            String.format("%.6f", estimatePi_Result),
            String.format("%d", runTime)
        });

        jTable1.setModel(Table);
        i++;
    }

    void montePanel() {
        MonteCarloPiScatterFrame frame = new MonteCarloPiScatterFrame();
        MonteCarloPiScatterFrame frame2 = new MonteCarloPiScatterFrame();
        switch (type) {
            case "par":

                frame.setParallelOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_par);
                break;

            case "seq":
                frame.setSequentialOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_seq);
                break;

            case "both":
                frame.setParallelOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_par);
                frame2.setSequentialOnly(config.getTotalPoints(), config.getNumTasks(), config.getNumThreads(), pi_seq);
                break;

        }

        jPanel6.add(frame);
        if (type.equals("both")) {
            jPanel7.add(frame2);
        }

    }

    void fill_Table(double seqResult, long seqTime,
            double parResult, long parTime) {

        String[] columnNames = {
            "Run #",
            "Version",
            "Pi Value",
            "RunTime (ms)"
        };
        Table.setColumnIdentifiers(columnNames);

        Table.addRow(new Object[]{
            String.valueOf(i),
            "Sequential",
            String.format("%.6f", seqResult),
            String.format("%d", seqTime)
        });

        Table.addRow(new Object[]{
            String.valueOf(i),
            "Parallel",
            String.format("%.6f", parResult),
            String.format("%d", parTime)
        });

        jTable1.setModel(Table);
        i++;
    }

    public void initHome(String type) {
        switch (type) {
            case "par":
                Run_par.setVisible(true);
                Run_suq.setVisible(false);
                Both.setVisible(false);
                 number_of_theards.setVisible(true);
                number_of_tasks.setVisible(true);
                jLabel3.setVisible(true);
                jLabel4.setVisible(true);


                break;
            case "seq":
                Run_suq.setVisible(true);
                number_of_theards.setVisible(false);
                number_of_tasks.setVisible(false);
                jLabel3.setVisible(false);
                jLabel4.setVisible(false);
                Both.setVisible(false);
                Run_par.setVisible(false);

                break;
            case "both":
                Both.setVisible(true);
                Run_suq.setVisible(false);
                Run_par.setVisible(false);
                 number_of_theards.setVisible(true);
                number_of_tasks.setVisible(true);
                jLabel3.setVisible(true);
                jLabel4.setVisible(true);

                break;

            default:
                throw new AssertionError();
        }
        setVisible(true);
        jFrame2.setVisible(false);

    }

    public PiExperimentRunner_form() {
        initComponents();
        setVisible(false);

        jFrame2.setResizable(false);
        jFrame2.setSize(430, 550);
        jFrame2.setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setResizable(false);
        jFrame1.setResizable(false);
        jFrame1.setSize(1000, 900);
        jFrame1.setLocationRelativeTo(null);
        setSize(370, 430);

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
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        avgError = new javax.swing.JTextField();
        avgErrorseq = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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
        jButton3 = new javax.swing.JButton();

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Average error p");

        avgError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgErrorActionPerformed(evt);
            }
        });

        avgErrorseq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgErrorseqActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Average error S");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avgErrorseq)
                            .addComponent(avgError))))
                .addGap(27, 27, 27))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(avgError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(avgErrorseq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(291, 291, 291))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 756, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 102, 51));
        jPanel5.setForeground(new java.awt.Color(255, 51, 51));

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

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton3)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(number_of_theards, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(number_of_tasks, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(number_of_points))))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Run_par, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Run_suq, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Both, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(number_of_points, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addContainerGap(182, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
       double par_Error=Math.abs(Math.PI-parallel_Resultt);
       error=error+par_Error;
        update_avgErrorpar();
    
    
        fill_Table(parallel_Resultt, runTime_parallel, "parallel");
        montePanel();

    }//GEN-LAST:event_Run_parActionPerformed

    private void Run_suqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Run_suqActionPerformed
        if (!validator("love is love")) {
            return;
        }
        SimulationConfig con = new SimulationConfig(Integer.parseInt(number_of_points.getText()), 0, 0);
        config = con;
        long startTime = System.currentTimeMillis();
        double sequntial_Resultt = PiExperiment_sequntial(Integer.parseInt(number_of_points.getText()), 0, 0);
        long endTime = System.currentTimeMillis();
        long runTime_sequntial = endTime - startTime;
        pi_seq = sequntial_Resultt;
        run_seq = runTime_sequntial;
        type = "seq";
        setVisible(false);
        jFrame1.setVisible(true);
        double Seq_Error=Math.abs(Math.PI-sequntial_Resultt);
        errorSeq=errorSeq+Seq_Error;
        update_avgErrorSeq();
        fill_Table(sequntial_Resultt, runTime_sequntial, "sequntial");
        montePanel();
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
         double par_Error=Math.abs(Math.PI-parallel_Resultt);
         double seq_Error=Math.abs(Math.PI-sequntial_Resultt);
       error=error+par_Error;
       errorSeq=errorSeq+seq_Error;
        update_avgErrorpar();
        update_avgErrorSeq();
        fill_Table(sequntial_Resultt, runTime_sequntial, parallel_Resultt, runTime_parallel);
        montePanel();


    }//GEN-LAST:event_BothActionPerformed

    private void number_of_theardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number_of_theardsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number_of_theardsActionPerformed

    private void number_of_pointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number_of_pointsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number_of_pointsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setVisible(true);
        jFrame1.setVisible(false);
        number_of_points.setText("");
        number_of_tasks.setText("");
        number_of_theards.setText("");
        jPanel6.removeAll();
          jPanel7.removeAll();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        setVisible(false);
        jFrame2.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void avgErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avgErrorActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_avgErrorActionPerformed

    private void avgErrorseqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avgErrorseqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_avgErrorseqActionPerformed

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
    private javax.swing.JTextField avgError;
    private javax.swing.JTextField avgErrorseq;
    private javax.swing.JButton both_button;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField number_of_points;
    private javax.swing.JTextField number_of_tasks;
    private javax.swing.JTextField number_of_theards;
    private javax.swing.JButton seq_button;
    // End of variables declaration//GEN-END:variables
}
