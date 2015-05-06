package Pokemon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorgecisneros
 */
public class VentanaPokedex extends javax.swing.JFrame {
    
    private BufferedImage buffer;
    
    //creo la imágen del Pokemon
    private Image imagenPokemons;
    
    //creo un contador que me diga el número del pokemon que estoy pintando
    private int contador = 0;
    
    //les doy un ancho y un alto
    private int ancho = 200, alto = 200;
    
    //conectamos la base de datos
    
    
    private Statement estado;
    private ResultSet resultadoConsulta;
    private Connection conexion;
    
    
    //////////////////////////////////////////////////////////////////////////
    
    //hashmap para almacenar el resultado de la consulta
    
    HashMap <String,Pokemon> listaPokemons = new HashMap();

    /**
     * Creates new form VentanaPokedex
     */
    private void dibujaElPokemonQueEstaEnLaPosicion (int posicion){
        
        int fila = posicion / 31; // el entero de la división me da la fila
        
        int columna = posicion % 31; //el resto de la división me da la columna
        
        //apunto al buffer
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
    
        //borro lo que hubiera y lo pinto de negro
        g2.setColor(Color.black);
    
        g2.fillRect(0, 0, alto, ancho);
    
        //pinto la imágen y le doy las posiciones
        g2.drawImage(imagenPokemons,
                0,//el sitio donde se pinta
                0,//el sitio donde se pinta
                ancho,//cuanto mide de ancho
                alto,//cuanto mide de alto
                96*columna,//le digo donde quiero que empiece a pintar la imágen en x
                96*fila,//le digo donde quiero que empiece a pintar la imágen en y
                96*columna + 96,//hasta donde tiene que pintar
                96*fila + 96,//hasta donde tiene que pintar
                null);
        repaint();
        escribeDatos();
    }
    private void escribeDatos(){
        Pokemon p = listaPokemons.get(String.valueOf(contador+1));//Convertimos a String el int con valueOf
        if (p != null){
            jLabel1.setText(p.nombre);
        }
        else{
            jLabel1.setText("No hay datos!");
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        
        //apunto al jPanel
        Graphics2D g2 = (Graphics2D) jPanel1.getGraphics();
        
        //pinto la imágen
        g2.drawImage(buffer, 0, 0, alto, ancho, null);
    }
    //este es el constructor de la clase
    public VentanaPokedex() {
        initComponents();
        
        try {
            imagenPokemons = ImageIO.read(getClass().getResource("black-white.png"));
        } catch (IOException ex) {
            Logger.getLogger(VentanaPokedex.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //casteamos el buffer al jPanel poniéndolo delante entre paréntesis
        buffer = (BufferedImage) jPanel1.createImage(ancho, alto);
        
        //inicializo el buffer
        Graphics2D g2 = buffer.createGraphics();
        
        
        
        
        
        //conexión a la base de datos/////////////////////////
        try{
            Class.forName("com.mysql.jdbc.Driver");//cojo un conector que hace que java pueda acceder a una base de datos
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "");//root es el usuario
            estado = conexion.createStatement();
            resultadoConsulta = estado.executeQuery("Select * from pokemon");
            
            //cargo el resultado de la query en mi hashmap
            while (resultadoConsulta.next()){
                Pokemon p = new Pokemon();
                p.nombre = resultadoConsulta.getString(2);//coge el resultado de la columna que elijo (2)
                p.generation_id = resultadoConsulta.getInt(5);
                p.evolution_chain_id = resultadoConsulta.getInt(6);
                p.specie = resultadoConsulta.getString(12);
                
                listaPokemons.put(resultadoConsulta.getString(1), p);//en la lista cogemos la columna 1 y el valor p (pokemon)
            }
            
        }
        catch(Exception e){
            
        }
        //////////////////////////////////////////////////////
        
        //llamo al método
        dibujaElPokemonQueEstaEnLaPosicion(0);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jButton1.setText("Izquierda");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jButton2.setText("Derecha");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        contador--;
        if (contador < 0){contador = 0;}
        dibujaElPokemonQueEstaEnLaPosicion(contador);
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        contador++;
        if (contador > 507){contador = 0;}
        dibujaElPokemonQueEstaEnLaPosicion(contador);
        Pokemon p = listaPokemons.get(String.valueOf(contador+1));//Convertimos a String el int con valueOf
        if (p != null){
            jLabel1.setText(p.nombre);
        }
        else{
            jLabel1.setText("No hay datos!");
        }
    }//GEN-LAST:event_jButton2MousePressed

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
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPokedex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
