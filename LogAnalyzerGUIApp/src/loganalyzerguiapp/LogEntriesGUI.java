package loganalyzerguiapp;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.me.loganalyzerlibrary.FileTransferClientLib;
import org.me.loganalyzerlibrary.HttpRequestLog;
import org.me.loganalyzerlibrary.LogAnalyzerLibClass;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author nasaam
 */
public class LogEntriesGUI extends javax.swing.JFrame implements Runnable{

    /**
     * Creates new form LogEntriesGUI
     */
    public LogAnalyzerLibClass analyzer;
    public String logFilePath;
    public String serverUrl;
    public boolean serverUrlIsIP;
    public boolean serverUrlIsHostname;
    public LogEntriesGUI(String logFilePath) {
        initComponents();
        this.logFilePath=logFilePath;
        serverUrl=null;
        serverUrlIsIP=false;
        serverUrlIsHostname=false;
    }
    public void setLogFilePath(String logFilePath){
        this.logFilePath=logFilePath;
    }
    public void analyzeLog(){
        if(logFilePath!=null){
            analyzer=new LogAnalyzerLibClass(logFilePath);
            analyzer.parseLogData();
        }
    }
    public void setLogSummaries(){
        setEntries();
        setMethodSummaries();
        setRemoteHostSummaries();
        setResponseCodeSummaries();
    }
    public void setEntries(){
        DefaultTableModel dtm=(DefaultTableModel)jTable1.getModel();
        dtm.setRowCount(0);
        ArrayList<HttpRequestLog> entries=analyzer.logEntries;
        for(int i=0;i<entries.size();i++){
            String [] entry=entries.get(i).getLogEntry();
            dtm.addRow(entry);
        }
        System.out.println("Number of entries: "+entries.size());
    }
    public void setMethodSummaries(){
        String[]head={"HEAD",analyzer.numberOfHeadRequest+""};
        String[]get={"GET",analyzer.numberOfGetRequest+""};
        String[]post={"POST",analyzer.numberOfPostRequest+""};
        String[]patch={"PATCH",analyzer.numberOfPatchRequest+""};
        String[]put={"PUT",analyzer.numberOfPutRequest+""};
        String[]delete={"DELETE",analyzer.numberOfDeleteRequest+""};
        String[]trace={"TRACE", analyzer.numberOfTraceRequest+""};
        String[]options={"OPTIONS",analyzer.numberOfOptionsRequest+""};
        String[]connect={"CONNECT",analyzer.numberOfConnectRequest+""};
        String[]other={"MISCELLANEOUS",analyzer.numberOfOtherRequest+""};
        DefaultTableModel dtm=(DefaultTableModel)jTable2.getModel();
        dtm.setRowCount(0);
        dtm.addRow(head);
        dtm.addRow(get);
        dtm.addRow(post);
        dtm.addRow(patch);
        dtm.addRow(put);
        dtm.addRow(delete);
        dtm.addRow(trace);
        dtm.addRow(options);
        dtm.addRow(connect);
        dtm.addRow(other);
    }
    public void setRemoteHostSummaries(){
        DefaultTableModel dtm=(DefaultTableModel)jTable3.getModel();
        dtm.setRowCount(0);
        HashMap<String,Double[]> responseData=analyzer.responseDataVector;
        Set<String>responseDataKeys=responseData.keySet();
        Iterator<String> remoteHosts=responseDataKeys.iterator();
        while(remoteHosts.hasNext()){
            String host=remoteHosts.next();
            Double[] value=responseData.get(host);
            String[] vector={host,value[2]+"",value[0]+"",value[1]+""}; 
            dtm.addRow(vector);
        }
    }
    public void setResponseCodeSummaries(){
        DefaultTableModel dtm=(DefaultTableModel)jTable4.getModel();
        dtm.setRowCount(0);
        HashMap<Integer, Integer> responseCode=analyzer.responseCodeVector;
        Set<Integer> keys=responseCode.keySet();
        Iterator<Integer>codes=keys.iterator();
        while(codes.hasNext()){
            Integer code=codes.next();
            Integer totalRequest=responseCode.get(code);
            String[] vector={code+"",totalRequest+""};
            dtm.addRow(vector);
        }
    }
    public void run(){
        if(serverUrl!=null){
            JFileChooser chooser=new JFileChooser();
            File file = new File("access.log");
            chooser.setSelectedFile(file);
            int result=chooser.showSaveDialog(null);
            if(result==JFileChooser.APPROVE_OPTION){
                File selectedFile = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(null,"Downloading file.");
                FileTransferClientLib.connectForFileTransfer(selectedFile.getAbsolutePath(),serverUrl);
                System.out.println("Approved: "+selectedFile.getAbsolutePath());
            }
        }
        else{
           JOptionPane.showMessageDialog(null,"Set Remote Server URL.");
        }
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Equicksales Apache Access Log Analyzer");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/equicks.jfif") );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Summaries"));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Entries"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Remote Host", "DateTime", "Method", "Requested Resource", "Protocol", "Response Code", "Resource Size", "Referer", "User Agent"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Method Summaries"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Method", "Total Requests"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Remote Host Summaries"));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Remote Host", "Total Requests", "Total Object Size", "Average Object Size"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Response Code Summaries"));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Response Code", "Total Requests"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton1.setText("Analyze Log Data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(591, 591, 591))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jMenu1.setText("Models");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem5.setText("Create Models");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Settings");

        jMenuItem1.setText("Add Local Log File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Set Remote Server URL");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("Set Log File For Analysis");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Download");

        jMenuItem4.setText("Download Remote Server Log");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       String result = JOptionPane.showInputDialog(null, "Enter Remote Server URL:", "Input", JOptionPane.QUESTION_MESSAGE);
       if(result!=null){
           String ipv4Regex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
           Pattern IPV4_PATTERN = Pattern.compile(ipv4Regex); 
           String hostnameRegex = "^((?!-)[A-Za-z0-9-]{1,63}(?<!\\.))(\\.(?!-)[A-Za-z0-9-]{1,63})*\\.[A-Za-z]{2,}$";
           Pattern hostname_PATTERN=Pattern.compile(hostnameRegex);
            if(IPV4_PATTERN.matcher(result).matches()){
                System.out.println("Valid IP");
                serverUrl=result;
                serverUrlIsIP=true;
                serverUrlIsHostname=false;
            }
            else if((hostname_PATTERN.matcher(result).matches())){
                System.out.println("Valid Hostname");
                serverUrl=result;
                serverUrlIsIP=false;
                serverUrlIsHostname=true;
            }
            else{
                 JOptionPane.showMessageDialog(null,"Invalid URL. URL must be a valid IPV4 Address or a valid Hostname"); 
            }
       }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(logFilePath!=null){
            
            analyzeLog();
            setLogSummaries();
        }
        else{
           JOptionPane.showMessageDialog(null,"Set Log file for analysis.");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //add local log file;
        JFileChooser chooser= new JFileChooser();
        int result=chooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectedFile=chooser.getSelectedFile();
            logFilePath=selectedFile.getAbsolutePath();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // set log file
        JFileChooser chooser= new JFileChooser();
        int result=chooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectedFile=chooser.getSelectedFile();
            logFilePath=selectedFile.getAbsolutePath();
        }   
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
           Thread thread = new Thread(this, "MyWorkerThread");
           thread.start();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        System.out.println("Starting models GUI");
        ModelGUI models= new ModelGUI(analyzer);
        models.setVisible(true);
        System.out.println("Showing models GUI");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
            java.util.logging.Logger.getLogger(LogEntriesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogEntriesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogEntriesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogEntriesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogEntriesGUI(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
