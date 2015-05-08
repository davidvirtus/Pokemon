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
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
    
    
    private Statement estado;
    private ResultSet resultadoConsulta;
    private Connection conexion;
    
    //creo el sonido
    Clip sonidoBoton;
    
    
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
            jLabel2.setText(p.specie);
            jLabel6.setText(p.habitat);
            jLabel8.setText(p.color);
            jLabel10.setText(p.weight);
            jLabel12.setText(p.height);
            jLabel13.setText(p.Id);
        }
        else{
            jLabel1.setText("No hay datos!");
            jLabel2.setText("No hay datos!");
            jLabel6.setText("No hay datos!");
            jLabel8.setText("No hay datos!");
            jLabel10.setText("No hay datos!");
            jLabel12.setText("No hay datos!");
            jLabel13.setText("");
        }
    }
    private void reproduceSonido(){
        try{
            sonidoBoton = AudioSystem.getClip();
            sonidoBoton.open(AudioSystem.getAudioInputStream(getClass().getResource("boton.wav")));;
        }catch (Exception ex){
            
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
        
        //pongo un color de fondo para el JFrame
        this.getContentPane().setBackground(Color.orange);
        
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
                p.habitat = resultadoConsulta.getString(15);
                p.color = resultadoConsulta.getString(13);
                p.weight = resultadoConsulta.getString(11);
                p.height = resultadoConsulta.getString(10);
                p.Id = resultadoConsulta.getString(1);
                
                listaPokemons.put(resultadoConsulta.getString(1), p);//en la lista cogemos la columna 1 y el valor p (pokemon)
            }
            
        }
        catch(Exception e){
            
        }
        //////////////////////////////////////////////////////
        
        //llamo al método que reproduce el sonido
        reproduceSonido();
        
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(java.awt.Color.white);

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

        jButton1.setFont(new java.awt.Font("Gurmukhi MT", 3, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("Izquierda");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Gurmukhi MT", 3, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("Derecha");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel2.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel3.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setText("Especie:");

        jLabel4.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 0));
        jLabel4.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Habitat:");

        jLabel6.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel7.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Color:");

        jLabel8.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 0));
        jLabel9.setText("Peso:");

        jLabel10.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 102, 0));

        jLabel11.setFont(new java.awt.Font("SignPainter", 3, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Altura:");

        jLabel12.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 102, 0));

        jLabel13.setFont(new java.awt.Font("Luminari", 3, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 51));

        jLabel14.setBackground(new java.awt.Color(153, 255, 153));
        jLabel14.setFont(new java.awt.Font("Wide Latin", 3, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 255, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("POKEDEX");
        jLabel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.red, null, java.awt.Color.red));
        jLabel14.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 3, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        contador--;
        if (contador < 0){contador = 0;}
        dibujaElPokemonQueEstaEnLaPosicion(contador);
        
        //hago que el sonido se reinicie para que vuelva a sonar al pulsar el
        //botón de nuevo
        sonidoBoton.setFramePosition(0);
        
        //reproduzco el sonido
        sonidoBoton.start();
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        contador++;
        if (contador > 507){contador = 0;}
        dibujaElPokemonQueEstaEnLaPosicion(contador);
        Pokemon p = listaPokemons.get(String.valueOf(contador+1));//Convertimos a String el int con valueOf
        if (p != null){
            jLabel1.setText(p.nombre);
            jLabel13.setText(p.Id);
        }
        else{
            jLabel1.setText("No hay datos!");
        }
        //hago que el sonido se reinicie para que vuelva a sonar al pulsar el
        //botón de nuevo
        sonidoBoton.setFramePosition(0);
        
        //reproduzco el sonido
        sonidoBoton.start();
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
