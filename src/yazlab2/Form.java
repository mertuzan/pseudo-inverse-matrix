/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author merth
 */
public class Form extends javax.swing.JFrame {

    public Form() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    ArrayList islemler=new ArrayList();
    double[][] transpoz;
    double[][] matris;
    double[][] carpim;
    double[][] ters;
    double[][] tmp;
    double[][] sozde_ters;
    int satir;
    int sutun;
    int toplama_sayisi = 0;
    int carpma_sayisi = 0;

    void getMatris(double t[][], double m[][], int r, int c) {
        transpoz = t;
        matris = m;
        satir = r;
        sutun = c;
        carpim = new double[r][r];
        ters = new double[r][r];
        tmp= new double [r][r];
        ilk_carpim();
        ters();
        son_carpim();
        tablo(satir,sutun);
    }

    void ilk_carpim() {
        DecimalFormat df = new DecimalFormat("#.#");
        String randstr;
        for (int i = 0; i < satir; i++) {
            for (int k = 0; k < satir; k++) {
                for (int j = 0; j < sutun; j++) {
                    carpim[i][k] += transpoz[i][j] * matris[j][k];
                    toplama_sayisi++;
                    carpma_sayisi++;
                }
                toplama_sayisi--;
            }
        }
        
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < satir; j++) {
                randstr = df.format(carpim[i][j]);
                randstr = randstr.replace(',', '.');
                carpim[i][j] = Double.valueOf(randstr);
            }
        }

    }

    void ters() {
        int c1=1;
        int c2=1;
        DecimalFormat df = new DecimalFormat("#.###");
        String randstr;
        if (satir == 1) {
            randstr = df.format(1/carpim[0][0]);
            carpma_sayisi++;
            randstr = randstr.replace(',', '.');
            ters[0][0] = Double.valueOf(randstr);
            

        }else if(satir >= 2){
            double[][] birim=new double[satir][satir];
            double[][] temp=new double[satir][satir];
            double tmp1;
            double tmp2;
            
            for (int i = 0; i < satir; i++) {
                for (int j = 0; j < satir; j++) {
                    temp[i][j]=carpim[i][j];
                }
            }
         
            for (int i = 0; i < satir; i++) {
                for (int j = 0; j < satir; j++) {
                    if(i==j)
                        birim[i][j]=1;
                    else
                        birim[i][j]=0;
                }
            }
            for (int i = 0; i < satir; i++) {
                c1=1;
                c2=1;
                tmp1=temp[i][i];
                for (int j = 0; j < satir; j++) {
                     temp[i][j]=temp[i][j]/tmp1;
                     if(c1==1){
                        islemler.add(i+".satir "+tmp1+" ile bölünür");
                        c1=0; 
                     }
                     
                     birim[i][j]=birim[i][j]/tmp1;
                     carpma_sayisi=carpma_sayisi+2;
                }
                for (int k = 0; k < satir; k++) {
                    if(k!=i){
                        tmp2=temp[k][i];
                    for (int j = 0; j < satir; j++) {
                        if(c2==1){
                            islemler.add(i+".satir "+tmp2+" ile carpilir "+k+".satirdan cıkarılır");
                            c2=0;
                        }
                        temp[k][j]=temp[k][j]-(temp[i][j]*tmp2);
                        birim[k][j]=birim[k][j]-(birim[i][j]*tmp2);
                        carpma_sayisi=carpma_sayisi+2;
                        toplama_sayisi=toplama_sayisi+2;
                        }
                    }
                }
            }
            
            try{
            for (int i = 0; i < satir; i++) {
                for (int j = 0; j < satir; j++) {
                    randstr = df.format(birim[i][j]);
                    randstr = randstr.replace(',', '.');
                    ters[i][j] = Double.valueOf(randstr);
                }
            }
            }catch(Exception e){
                System.out.println("Girilen matrisin tersi bulunmamaktadir.");
            }
            
        }
        
    }
    
    void son_carpim(){
        sozde_ters=new double[satir][sutun];
        DecimalFormat df = new DecimalFormat("#.##");
        String randstr;
        for (int i = 0; i < satir; i++) {
            for (int k = 0; k < sutun; k++) {
                for (int j = 0; j < satir; j++) {
                    sozde_ters[i][k] += ters[i][j] * transpoz[j][k];
                    toplama_sayisi++;
                    carpma_sayisi++;
                }
                toplama_sayisi--;
            }
        }
        
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                randstr = df.format(sozde_ters[i][j]);
                randstr = randstr.replace(',', '.');
                sozde_ters[i][j] = Double.valueOf(randstr);
            }
        }
    }
    
    private void tablo(int r, int c) {
        DefaultTableModel t = new DefaultTableModel();
        for (int i = 0; i < c; i++) {
            t.addColumn("");
        }
        for (int i = 0; i < r; i++) {
           t.addRow(new String[]{""});
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                t.setValueAt(transpoz[i][j], i, j);
                
            }
        }
        tablotranspoz.setModel(t);
        
        t = new DefaultTableModel();
        for (int i = 0; i < r; i++) {
            t.addColumn("");
        }
        for (int i = 0; i < c; i++) {
           t.addRow(new String[]{""});
        }
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                t.setValueAt(matris[i][j], i, j);
                
            }
        }
        tablomatris.setModel(t);
        
        t = new DefaultTableModel();
        for (int i = 0; i < r; i++) {
            t.addColumn("");
        }
        for (int i = 0; i < r; i++) {
           t.addRow(new String[]{""});
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                t.setValueAt(carpim[i][j], i, j);
                
            }
        }
        tablocarpim.setModel(t);
        
        t = new DefaultTableModel();
        for (int i = 0; i < r; i++) {
            t.addColumn("");
        }
        for (int i = 0; i < r; i++) {
           t.addRow(new String[]{""});
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                t.setValueAt(ters[i][j], i, j);
                
            }
        }
        tabloters.setModel(t);
        
        t = new DefaultTableModel();
        for (int i = 0; i < c; i++) {
            t.addColumn("");
        }
        for (int i = 0; i < r; i++) {
           t.addRow(new String[]{""});
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                t.setValueAt(sozde_ters[i][j], i, j);
                
            }
        }
        tablosozde.setModel(t);
        
        String str="<html>";
        for (int i = 0; i < islemler.size(); i++) {
            str=str+islemler.get(i);
            str=str+"<br>";
        }
        str=str+"</html>";
        
        labelislemler.setText(str);
        labeltoplama.setText(""+toplama_sayisi);
        labelcarpma.setText(""+carpma_sayisi);
        labelboyutlar.setText(""+sutun+"x"+satir);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablomatris = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablotranspoz = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablocarpim = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabloters = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablosozde = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        labelboyutlar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labeltoplama = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelcarpma = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        labelislemler = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Computational Complexity");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Matris A"));

        tablomatris.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablomatris);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Transpoz A^T"));

        tablotranspoz.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablotranspoz);

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Çarpım A^T*A"));

        tablocarpim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablocarpim);

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ters (A^T*A)^-1"));

        tabloters.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tabloters);

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Sözde Ters (A^T*A)^-1*A^T"));

        tablosozde.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tablosozde);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Matris Boyutları:"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelboyutlar, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labelboyutlar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Toplama sayısı:"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltoplama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labeltoplama, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Çarpma sayısı:"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelcarpma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(labelcarpma, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("Gauss Jordan Ters Alma İşlemleri"));

        labelislemler.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setViewportView(labelislemler);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelboyutlar;
    private javax.swing.JLabel labelcarpma;
    private javax.swing.JLabel labelislemler;
    private javax.swing.JLabel labeltoplama;
    private javax.swing.JTable tablocarpim;
    private javax.swing.JTable tablomatris;
    private javax.swing.JTable tablosozde;
    private javax.swing.JTable tabloters;
    private javax.swing.JTable tablotranspoz;
    // End of variables declaration//GEN-END:variables
}
