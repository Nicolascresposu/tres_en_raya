//TO-DO
//O no se marca que es mi turno correctamente o no me deja mandar
//No se marcan los 0008 que se mandan
//Cuando actuo como cliente se guarda la ip, pero no el nombre. Se anade a la lista correctaemnte. 
//IGNORAR TODO LO DE ARRIBA, enfocate en esto:
//
//El presionar O para iniciar no te deja en realidad iniciar como O, creo que el comando no se esta mandando correctametne
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.upb.tresenraya;

import edu.upb.tresenraya.Comando.AceptacionConexion;
import edu.upb.tresenraya.Comando.CerrarYBorrar;
import edu.upb.tresenraya.aula.InterfazCompra;
import edu.upb.tresenraya.Comando.Comando;
import edu.upb.tresenraya.Comando.GodmodeMarcarSimbolo;
import edu.upb.tresenraya.Comando.MarcarSimbolo;
import edu.upb.tresenraya.Comando.NuevaPartida;
import edu.upb.tresenraya.Comando.RechazoConexion;
import edu.upb.tresenraya.Comando.SolicitudConexion;
import edu.upb.tresenraya.Contacto.Contacto;
import edu.upb.tresenraya.Contacto.Contactos;
import edu.upb.tresenraya.Contacto.MyCollection;
import edu.upb.tresenraya.aula.PatronIterator;
import edu.upb.tresenraya.db.ConexionDb;
import edu.upb.tresenraya.mediador.Mediador;
import edu.upb.tresenraya.server.ServidorJuego;
import javax.swing.JLabel;
import edu.upb.tresenraya.mediador.OnMessageListener;
import edu.upb.tresenraya.logicaTicTacToe;
import edu.upb.tresenraya.server.SocketClient;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
/**
 *
 * @author rlaredo
 */
public class TresEnRayaUI extends javax.swing.JFrame implements OnMessageListener, ActionListener{

    private ServidorJuego servidorJuego;
    private InterfazCompra interfazCompra;
    private logicaTicTacToe logica = new logicaTicTacToe();
    private SocketClient socketClient;
    public boolean esMiTurno;
    public boolean godmode=false;
    
    private final DefaultListModel<Contacto> contacModel = new DefaultListModel<>();
    private String jugadorBIP;
    private Connection baseDeDatos = ConexionDb.instancia().getConnection();
    /**
     * Creates new form TresEnRayaUI
     */
    public TresEnRayaUI() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/3raya.png")));
        Mediador.addListener(this);
        Contactos.getInstance();
        this.enviarMensaje.addActionListener(this);
        this.btnConectar.addActionListener(this);
//        this.enviarMensaje.setText("<b>Hola</>");
//        this.iconoDeIsaac.setIcon(new ImageIcon ());
        // Esto es para cuando se inicia el programa por primera vez, crear la conexion a la base de datos para 
        String SQLinicio = """
                           CREATE TABLE IF NOT EXISTS Cliente (
                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                           nombre TEXT,
                           ip TEXT NOT NULL,
                           UNIQUE(ip,nombre)     
                          );
                           """;
        try {
            Statement statement = baseDeDatos.createStatement();
            statement.execute(SQLinicio);
            
        } catch (SQLException ex) {
            Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jLContactos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    jLContactos.setSelectedIndex(jLContactos.locationToIndex(e.getPoint()));
                    menuListaContactos.show(jLContactos, e.getPoint().x, e.getPoint().y);
                }
            }
        });
        jLContactos.setCellRenderer(new ContactRenderer());
        jLContactos.setModel(contacModel);
        Contactos.getInstance();
        
        
        
        ButtonMapper buttonMapper = new ButtonMapper();
        setupJButtonKeyBindings();
        ButtonSimulator buttonSimulator = new ButtonSimulator(buttonMapper);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD7, grilla00);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD4, grilla01);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD1, grilla02);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD8, grilla10);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD0, grilla11);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD2, grilla12);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD9, grilla20);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD6, grilla21);
        buttonMapper.addButton(KeyEvent.VK_NUMPAD3, grilla22);
        
        addFocusListenerToButtons(grilla00, grilla01, grilla02, grilla10, grilla11, grilla12, grilla20, grilla21, grilla22);
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                buttonSimulator.simulateButtonPress(keyCode);  // Trigger button press
            }
        });
        setFocusable(true); // REQUIRED for KeyListener to work!
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    int keyCode = e.getKeyCode();
                    buttonSimulator.simulateButtonPress(keyCode);
                }
                return false; // Let other components process the event too
            }
        });
        refresh();
    }
    
    public JLabel getLabel(){
        return this.jlMessage;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuListaContactos2358928490840534 = new javax.swing.JPopupMenu();
        menuListaContactos = new javax.swing.JPopupMenu();
        jMenuItemRetar = new javax.swing.JMenuItem();
        ModificarNombreDeContacto = new javax.swing.JMenuItem();
        ModificarIp = new javax.swing.JMenuItem();
        EliminarDelRegistro = new javax.swing.JMenuItem();
        ForzarOlvido = new javax.swing.JMenuItem();
        menuDebug = new javax.swing.JMenu();
        jToolBar1 = new javax.swing.JToolBar();
        btnServer = new javax.swing.JButton();
        amsal = new javax.swing.JToggleButton();
        btnGodmode = new javax.swing.JButton();
        btnMandarNumeros = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        conexionContacto1 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLContactos = new javax.swing.JList<>();
        panel1 = new java.awt.Panel();
        grilla00 = new java.awt.Button();
        grilla10 = new java.awt.Button();
        grilla20 = new java.awt.Button();
        grilla21 = new java.awt.Button();
        grilla11 = new java.awt.Button();
        grilla01 = new java.awt.Button();
        grilla02 = new java.awt.Button();
        grilla12 = new java.awt.Button();
        grilla22 = new java.awt.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();
        jlMessage = new javax.swing.JLabel();
        mandarMensaje = new javax.swing.JTextField();
        enviarMensaje = new javax.swing.JButton();
        labelEsMiTurno = new javax.swing.JLabel();
        labelTurnoActual = new javax.swing.JLabel();
        btnReinicio = new javax.swing.JButton();
        btnRefreshContactoLista = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textoIP = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();

        jMenuItemRetar.setText("Retar");
        jMenuItemRetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRetarActionPerformed(evt);
            }
        });
        menuListaContactos.add(jMenuItemRetar);

        ModificarNombreDeContacto.setText("Modificar Nombre");
        ModificarNombreDeContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarNombreDeContactoActionPerformed(evt);
            }
        });
        menuListaContactos.add(ModificarNombreDeContacto);

        ModificarIp.setText("Modificar Ip");
        ModificarIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarIpActionPerformed(evt);
            }
        });
        menuListaContactos.add(ModificarIp);

        EliminarDelRegistro.setText("Eliminar");
        EliminarDelRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarDelRegistroActionPerformed(evt);
            }
        });
        menuListaContactos.add(EliminarDelRegistro);

        ForzarOlvido.setText("Forzar Olvido");
        ForzarOlvido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForzarOlvidoActionPerformed(evt);
            }
        });
        menuListaContactos.add(ForzarOlvido);

        menuDebug.setText("Debug");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tres en Raya");

        jToolBar1.setRollover(true);

        btnServer.setMnemonic('I');
        btnServer.setText("Iniciar Servidor");
        btnServer.setFocusable(false);
        btnServer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnServer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnServer);

        amsal.setText("AMSAL:");
        amsal.setToolTipText("Alterar Manualmente Simbolo Actual Local");
        amsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amsalActionPerformed(evt);
            }
        });
        jToolBar1.add(amsal);

        btnGodmode.setText("Godmode");
        btnGodmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGodmodeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGodmode);

        btnMandarNumeros.setText("Mandar numeros del 1 al 10");
        btnMandarNumeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMandarNumerosActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMandarNumeros);

        jButton1.setText("Compra Premium ahora");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        conexionContacto1.setText("Forzar Conexion a Contacto 1");
        conexionContacto1.setFocusable(false);
        conexionContacto1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        conexionContacto1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        conexionContacto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conexionContacto1ActionPerformed(evt);
            }
        });
        jToolBar1.add(conexionContacto1);

        jSplitPane1.setDividerLocation(0);

        jScrollPane1.setViewportView(jLContactos);

        jSplitPane1.setRightComponent(jScrollPane1);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(panel1);

        grilla00.setLabel("_");
        grilla00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla00ActionPerformed(evt);
            }
        });

        grilla10.setLabel("_");
        grilla10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla10ActionPerformed(evt);
            }
        });

        grilla20.setLabel("_");
        grilla20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla20ActionPerformed(evt);
            }
        });

        grilla21.setLabel("_");
        grilla21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla21ActionPerformed(evt);
            }
        });

        grilla11.setLabel("_");
        grilla11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla11ActionPerformed(evt);
            }
        });

        grilla01.setLabel("_");
        grilla01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla01ActionPerformed(evt);
            }
        });

        grilla02.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        grilla02.setLabel("_");
        grilla02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla02ActionPerformed(evt);
            }
        });

        grilla12.setLabel("_");
        grilla12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla12ActionPerformed(evt);
            }
        });

        grilla22.setLabel("_");
        grilla22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grilla22ActionPerformed(evt);
            }
        });

        chat.setColumns(20);
        chat.setRows(5);
        jScrollPane2.setViewportView(chat);

        jlMessage.setText("Inicio de chat");

        mandarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandarMensajeActionPerformed(evt);
            }
        });

        enviarMensaje.setText("Enviar");
        enviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeActionPerformed(evt);
            }
        });

        labelEsMiTurno.setText("Es mi turno? : ");

        labelTurnoActual.setText("Turno de:");

        btnReinicio.setText("Reiniciar tabla");
        btnReinicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReinicioActionPerformed(evt);
            }
        });

        btnRefreshContactoLista.setText("Refresh");
        btnRefreshContactoLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshContactoListaActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingresa la IP de un compañero");

        textoIP.setText("localhost");
        textoIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoIPActionPerformed(evt);
            }
        });

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mandarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviarMensaje))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(grilla02, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(grilla01, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(grilla00, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(grilla10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(grilla12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(grilla11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(37, 37, 37)
                                .addComponent(grilla20, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(grilla21, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(grilla22, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelEsMiTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReinicio)
                                    .addComponent(labelTurnoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(btnConectar))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(textoIP, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addGap(11, 11, 11)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefreshContactoLista)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSplitPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(grilla00, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(grilla10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(labelEsMiTurno))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(grilla01, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(grilla11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(14, 14, 14)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(grilla02, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(grilla12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(grilla21, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(grilla22, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTurnoActual)
                                    .addComponent(grilla20, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReinicio))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConectar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshContactoLista))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mandarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enviarMensaje))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServerActionPerformed
                if (servidorJuego == null) {
            try {
                servidorJuego = new ServidorJuego();
                servidorJuego.start();
                btnServer.setText("Servidor Iniciado");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnServerActionPerformed

    private void enviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarMensajeActionPerformed
        // TODO add your handling code here:
        //Deprecated method of answering requests.
//        if (mandarMensaje.getText().equals("y")) {
//            this.chat.setText(this.chat.getText()+System.lineSeparator()+"Conexiones pendientes aceptadas."+System.lineSeparator());
//            servidorJuego.client.send(("0003|Nicolas Crespo"+System.lineSeparator()).getBytes());
//            //esto es nulo wn
//            //socketClient.send(("0003|NICOLAS CRESPO H23".getBytes()));
//        }
//        else if (mandarMensaje.getText().equals("n")) {
//            this.chat.setText(this.chat.getText()+System.lineSeparator()+"Conexion rechazada y socketClient de SERVIDORJUEGO establecido a nulo.");
//            servidorJuego.client.send(("0002"+ System.lineSeparator()).getBytes()); 
//            servidorJuego = null; 
//            socketClient = null;
//            btnServer.setText("Iniciar Servidor");
////            socketClient.send(("0002".getBytes()));
            
//        }
//        else {
//        }
        chatAddText(mandarMensaje.getText());
        Contactos.getInstance().send(jugadorBIP,mandarMensaje.getText()+System.lineSeparator());
//            if (socketClient!=null) {
//                socketClient.send((mandarMensaje.getText() + System.lineSeparator()).getBytes());
//            }
//            if (servidorJuego!=null) {
//                servidorJuego.client.send((mandarMensaje.getText() + System.lineSeparator()).getBytes());
//            }
       mandarMensaje.setText("");
    }//GEN-LAST:event_enviarMensajeActionPerformed

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        // TODO add your handling code here:
        //ahora si en este esta el crear cliente
        if (socketClient == null) {
            try {
                //Socket(ip,puerto)
                socketClient = new SocketClient(new Socket(textoIP.getText(),1825));
                socketClient.start();
                SolicitudConexion solicitudAEnviar = new SolicitudConexion("Nicolas Crespo");
                Contactos.getInstance().onNewClient(socketClient);
                Contactos.getInstance().send(socketClient.getIp(), "0001|" + solicitudAEnviar.nombre + System.lineSeparator());
                
                //0001 es el codigo de solicitud enviada
                //Esta es la forma vieja de enviar el mensaje:
//                socketClient.send(solicitudAEnviar.getComando().getBytes());

//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Escriba un mensaje por la caja de texto.");
                chatAddText("Nueva conexion establecida con "+textoIP.getText()+" exitosamente!");
//                socketClient.send((br.readLine() + System.lineSeparator()).getBytes());
//                String[] args = {textoIP.getText()};
//                SocketClient.main(args);
//                jButton2.setText("Cliente Iniciado");
                labelTurnoActual.setText("Turno de: "+logica.turnoActual);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        jPanel1.setBackground(Color.green);
        if (interfazCompra == null) {
            try {
                interfazCompra = new InterfazCompra();
//                btnServer.setText("Servidor Iniciado");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void logicaBotonPresionado(int valorX, int valorY) {
//        Godmode off
        
        if (!godmode) {
            if (esMiTurno && logica.grilla[valorX][valorY]==null) {
                //Forma deprecada de mandar mensajes:
//                if (socketClient!=null) {
//                    socketClient.send(("0008|"+logica.turnoActual+"|"+valorX+"|"+valorY + System.lineSeparator()).getBytes());
//                }
//                if (servidorJuego!=null) {
//                    servidorJuego.client.send(("0008|"+logica.turnoActual+"|"+valorX+"|"+valorY + System.lineSeparator()).getBytes());
//                }
                String mensaje = "0008|"+logica.turnoActual+"|"+valorX+"|"+valorY;
                Contactos.getInstance().send(jugadorBIP,mensaje + System.lineSeparator());
                logica.setGrilla(valorX,valorY,logica.turnoActual);
                chatAddText(mensaje);
//                chat.setText(chat.getText()+Arrays.toString(logica.grilla)+System.lineSeparator());
                switch(valorX) {
                    case 0:
                        switch (valorY) {
                            case 0:
                                grilla00.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 1:
                                grilla01.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 2:
                                grilla02.setLabel(logica.grilla[valorX][valorY]);
                                break;
                        }
                        break;
                    case 1:
                        switch (valorY) {
                            case 0:
                                grilla10.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 1:
                                grilla11.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 2:
                                grilla12.setLabel(logica.grilla[valorX][valorY]);
                                break;
                        }
                        break;

                    case 2:
                        switch (valorY) {
                            case 0:
                                grilla20.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 1:
                                grilla21.setLabel(logica.grilla[valorX][valorY]);
                                break;
                            case 2:
                                grilla22.setLabel(logica.grilla[valorX][valorY]);
                                break;
                        }
                        break;
                }
                labelTurnoActual.setText("Turno de: "+logica.turnoActual);
                if (logica.winner!="")
                    victoria();
                esMiTurno=false;
                labelEsMiTurno.setText("Es mi turno? : "+esMiTurno);
        } else {System.out.println("No es mi turno.");}
            
        }
        //Godmode on
        if (godmode) {
            if (socketClient!=null) {
            socketClient.send(("0011|"+logica.turnoActual+"|"+valorX+"|"+valorY + System.lineSeparator()).getBytes());
        }
        if (servidorJuego!=null) {
            servidorJuego.client.send(("0011|"+logica.turnoActual+"|"+valorX+"|"+valorY + System.lineSeparator()).getBytes());
        }
        logica.godmodeSetGrilla(valorX,valorY,logica.turnoActual);
        labelTurnoActual.setText("Turno de: "+logica.turnoActual);
        if (logica.winner!="")
            victoria();
        }
        
    }
    private void grilla00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla00ActionPerformed
        // TODO add your handling code here:
        int valorX = 0;
        int valorY = 0;
        logicaBotonPresionado(valorX,valorY);
//        grilla00.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla00ActionPerformed
    
//    grilla00.addActionListener( buttonPressed);
    
    private void grilla01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla01ActionPerformed
        int valorX = 0;
        int valorY = 1;
        logicaBotonPresionado(valorX,valorY);
//        grilla01.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla01ActionPerformed

    private void grilla02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla02ActionPerformed
        int valorX = 0;
        int valorY = 2;
        logicaBotonPresionado(valorX,valorY);
//        grilla02.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla02ActionPerformed

    private void grilla12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla12ActionPerformed
        int valorX = 1;
        int valorY = 2;
        logicaBotonPresionado(valorX,valorY);
//        grilla12.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla12ActionPerformed

    private void grilla10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla10ActionPerformed
        int valorX = 1;
        int valorY = 0;
        logicaBotonPresionado(valorX,valorY);
//        grilla10.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla10ActionPerformed

    private void grilla11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla11ActionPerformed
        int valorX = 1;
        int valorY = 1;
        logicaBotonPresionado(valorX,valorY);
//        grilla11.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla11ActionPerformed

    private void grilla20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla20ActionPerformed
        int valorX = 2;
        int valorY = 0;
        logicaBotonPresionado(valorX,valorY);
//        grilla20.setLabel(logica.grilla[valorX][valorY]);
    }//GEN-LAST:event_grilla20ActionPerformed

    private void grilla21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla21ActionPerformed
        int valorX = 2;
        int valorY = 1;
        logicaBotonPresionado(valorX,valorY);
    }//GEN-LAST:event_grilla21ActionPerformed

    private void grilla22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grilla22ActionPerformed
        int valorX = 2;
        int valorY = 2;
        logicaBotonPresionado(valorX,valorY);
    }//GEN-LAST:event_grilla22ActionPerformed

    private void textoIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoIPActionPerformed

    private void mandarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandarMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandarMensajeActionPerformed

    private void reiniciarGrilla() {
        //Quiza deberia tener un atributo String turnoOponente y hacer el setturnoactual basado en eso
        grilla00.setLabel("_");
        grilla01.setLabel("_");
        grilla02.setLabel("_");
        grilla10.setLabel("_");
        grilla11.setLabel("_");
        grilla12.setLabel("_");
        grilla20.setLabel("_");
        grilla21.setLabel("_");
        grilla22.setLabel("_");
        Color defaultColor = new java.awt.Button().getBackground();
        grilla00.setBackground(defaultColor);
        grilla01.setBackground(defaultColor);
        grilla02.setBackground(defaultColor);
        grilla10.setBackground(defaultColor);
        grilla11.setBackground(defaultColor);
        grilla12.setBackground(defaultColor);
        grilla20.setBackground(defaultColor);
        grilla21.setBackground(defaultColor);
        grilla22.setBackground(defaultColor);
        
        
        
        logica.setTurnoActual("X");
        logica.grilla=new String[3][3];
        logica.winner="";
        //Mandamos solicitud de nueva partida
        
//        servidorJuego.client.send(();
//        logica.setGrilla(0, 0, "_");
//        logica.setGrilla(0, 1, "_");
//        logica.setGrilla(0, 2, "_");
//        logica.setGrilla(1, 0, "_");
//        logica.setGrilla(1, 1, "_");
//        logica.setGrilla(1, 2, "_");
//        logica.setGrilla(2, 0, "_");
//        logica.setGrilla(2, 1, "_");
//        logica.setGrilla(2, 2, "_");
    }
    private void btnReinicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReinicioActionPerformed
        // TODO add your handling code here:
        reiniciarGrilla();
        Contactos.getInstance().send(jugadorBIP, "0007"+System.lineSeparator());
    }//GEN-LAST:event_btnReinicioActionPerformed

    private void btnMandarNumerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMandarNumerosActionPerformed
        MyCollection iterator = new MyCollection();
        iterator.addItem("0");
        iterator.addItem("1");
        iterator.addItem("2");
        iterator.addItem("3");
        iterator.addItem("4");
        iterator.addItem("5");
        iterator.addItem("6");
        iterator.addItem("7");
        iterator.addItem("8");
        iterator.addItem("9");
        iterator.addItem("10");
        
//        while (iterator.hasNext()) {
//            servidorJuego.client.send( ( iterator.getNext()+System.lineSeparator() ).getBytes());
//        }
        if (socketClient!=null) {
                while (iterator.hasNext()) {
                socketClient.send( ( iterator.getNext()+System.lineSeparator() ).getBytes());
        }
            }
            if (servidorJuego!=null) {
                while (iterator.hasNext()) {
                servidorJuego.client.send( ( iterator.getNext()+System.lineSeparator() ).getBytes());
        }
            }
        
    }//GEN-LAST:event_btnMandarNumerosActionPerformed

    private void jMenuItemRetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRetarActionPerformed
        // TODO add your handling code here:
//        JPanelJuego jPanelJuego = new JPanelJuego();
//        jPanelJuego.setVisible(true);
//        JFrame ventanaSecundaria = new JFrame("Ventana Secundaria");
//        ventanaSecundaria.getContentPane().add(jPanelJuego);
//        ventanaSecundaria.setSize(400, 400); // Establece el tamaño de la ventana
//        ventanaSecundaria.setLocationRelativeTo(null); // Centra la ventana en la pantalla
//        ventanaSecundaria.setVisible(true);
//Todo lo comentado era para la ventana secundaria del profe
//        System.out.println("Deberia enviar un 0007 o algo? lol");
        int selectedIndex = jLContactos.getSelectedIndex();
        retar(selectedIndex);
            
        
        
    }//GEN-LAST:event_jMenuItemRetarActionPerformed

    private void EliminarDelRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarDelRegistroActionPerformed
                // Get the selected contact
        int selectedIndex = jLContactos.getSelectedIndex();
        if (selectedIndex != -1) { // Ensure an item is selected
            Contacto selectedContact = contacModel.getElementAt(selectedIndex);

            // Remove the contact from the model
            contacModel.remove(selectedIndex);
            //Remove from DB ig
            
                                String SQLAdd = """
                           DELETE FROM Cliente WHERE ip = ?;
                           """;
                    try {   
                        PreparedStatement preparedStatement = baseDeDatos.prepareStatement(SQLAdd);
                        preparedStatement.setString(1, selectedContact.getIp());
                        
                        //Esto es de tipo statement
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Record deleted successfully!");
                        } else {
                            System.out.println("Record either didn't exist or something else went wrong.");
                        }
                        
//                        baseDeDatos.createStatement().execute(preparedStatement);

                    } catch (SQLException ex) {
                        Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

            // Remove the contact from the Contactos instance
            Contactos.getInstance().removeClient(selectedContact.getIp());

            // Optionally, notify the user that the contact has been deleted
            JOptionPane.showMessageDialog(this, "Contacto eliminado: " + selectedContact.getName(), "Contacto Eliminado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún contacto.", "Error", JOptionPane.ERROR_MESSAGE);
        }           
    }//GEN-LAST:event_EliminarDelRegistroActionPerformed

    private void ModificarNombreDeContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarNombreDeContactoActionPerformed
        int selectedIndex = jLContactos.getSelectedIndex();
        if (selectedIndex != -1) { // Ensure an item is selected
            Contacto selectedContact = contacModel.getElementAt(selectedIndex);

            // Prompt the user for a new name
            String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre para el contacto:", selectedContact.getName());

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) { // Ensure the input is valid
                // Update the contact's name
                selectedContact.setName(nuevoNombre.trim());

                // Refresh the list to reflect the changes
                contacModel.set(selectedIndex, selectedContact);

                // Notify the user that the contact has been updated
                JOptionPane.showMessageDialog(this, "Nombre del contacto actualizado: " + selectedContact.getName(), "Contacto Modificado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún contacto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ModificarNombreDeContactoActionPerformed

    private void ModificarIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarIpActionPerformed
        //Esta parte fue hecha con ayuda de deepseek, mismo para el cambio de Nombre.
        int selectedIndex = jLContactos.getSelectedIndex();
        if (selectedIndex != -1) { // Ensure an item is selected
            Contacto selectedContact = contacModel.getElementAt(selectedIndex);

            // Prompt the user for a new name
            String nuevaIp = JOptionPane.showInputDialog(this, "Ingrese la nueva ip para el contacto:", selectedContact.getIp());

            if (nuevaIp != null && !nuevaIp.trim().isEmpty()) { // Ensure the input is valid
                // Update the contact's name
                selectedContact.setIp(nuevaIp.trim());

                // Refresh the list to reflect the changes
                contacModel.set(selectedIndex, selectedContact);

                // Notify the user that the contact has been updated
                JOptionPane.showMessageDialog(this, "IP del contacto actualizado: " + selectedContact.getName() + ". Recuerda que esto no actualiza la conexion actual en caso que hubieras actualizado ese contacto.", "Contacto Modificado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún contacto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ModificarIpActionPerformed

    private void amsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amsalActionPerformed
        logica.setTurnoActual(logica.cambiaTurnos(logica.turnoActual));
        amsal.setText("AMSAL: " + logica.turnoActual);
    }//GEN-LAST:event_amsalActionPerformed

    private void ForzarOlvidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForzarOlvidoActionPerformed
        int selectedIndex = jLContactos.getSelectedIndex();
        if (selectedIndex != -1) { // Ensure an item is selected
            Contacto selectedContact = contacModel.getElementAt(selectedIndex);
            //Hardcoded name I gave myself on client connection is "Nicolas Crespo"
            Contactos.getInstance().send(selectedContact.getIp(), "0010" + "|" + "Nicolas Crespo");
        }
            
            
    }//GEN-LAST:event_ForzarOlvidoActionPerformed

    private void btnGodmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGodmodeActionPerformed
        if (godmode) {
            godmode=false;
            btnGodmode.setBackground(new javax.swing.JButton().getBackground());
        } else {
            godmode = true;
            btnGodmode.setBackground(Color.red);
        }
        
    }//GEN-LAST:event_btnGodmodeActionPerformed

    private void btnRefreshContactoListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshContactoListaActionPerformed
        refresh();
    }//GEN-LAST:event_btnRefreshContactoListaActionPerformed

    private void conexionContacto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conexionContacto1ActionPerformed
        retar(0);
    }//GEN-LAST:event_conexionContacto1ActionPerformed

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
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TresEnRayaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem EliminarDelRegistro;
    private javax.swing.JMenuItem ForzarOlvido;
    private javax.swing.JMenuItem ModificarIp;
    private javax.swing.JMenuItem ModificarNombreDeContacto;
    private javax.swing.JToggleButton amsal;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnGodmode;
    private javax.swing.JButton btnMandarNumeros;
    private javax.swing.JButton btnRefreshContactoLista;
    private javax.swing.JButton btnReinicio;
    private javax.swing.JButton btnServer;
    private javax.swing.JTextArea chat;
    private javax.swing.JButton conexionContacto1;
    private javax.swing.JButton enviarMensaje;
    private java.awt.Button grilla00;
    private java.awt.Button grilla01;
    private java.awt.Button grilla02;
    private java.awt.Button grilla10;
    private java.awt.Button grilla11;
    private java.awt.Button grilla12;
    private java.awt.Button grilla20;
    private java.awt.Button grilla21;
    private java.awt.Button grilla22;
    private javax.swing.JButton jButton1;
    private javax.swing.JList<Contacto> jLContactos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItemRetar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel jlMessage;
    private javax.swing.JLabel labelEsMiTurno;
    private javax.swing.JLabel labelTurnoActual;
    private javax.swing.JTextField mandarMensaje;
    private javax.swing.JMenu menuDebug;
    private javax.swing.JPopupMenu menuListaContactos;
    private javax.swing.JPopupMenu menuListaContactos2358928490840534;
    private java.awt.Panel panel1;
    private javax.swing.JTextField textoIP;
    // End of variables declaration//GEN-END:variables

    
            
    @Override
    public void onMessage(String msg) {
        chatAddText(msg);
//        this.chat.setText(this.chat.getText()+System.lineSeparator()+msg);
        try {
            Thread.sleep(400);
            //Deprecated function to parse the name inside a request. Now we handle it via Command.
//        try {
//            SolicitudConexion solicitud = new SolicitudConexion();
//            solicitud.parsear(msg);
//            
//            if (solicitud.nombre!=null) {
//            this.chat.setText(this.chat.getText()+System.lineSeparator()+"El usuario "+solicitud.nombre+" ha intentado conectarse. Aceptas? y/n.");
//        } else {
//                
//            }
//            
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (InterruptedException ex) {
            Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClose() {
        System.out.println("UI: Cayo el cliente");        
    }
    
    public void onButtonGreen() {
//        jPanel1.setBackground(Color.green);
//        this.btnClase.setText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("a: "+e.getActionCommand());
    }

    private void victoria() {
        if (logica.winner=="X") {
            grilla00.setBackground(Color.orange);
            grilla01.setBackground(Color.orange);
            grilla02.setBackground(Color.orange);
            grilla10.setBackground(Color.orange);
            grilla11.setBackground(Color.orange);
            grilla12.setBackground(Color.orange);
            grilla20.setBackground(Color.orange);
            grilla21.setBackground(Color.orange);
            grilla22.setBackground(Color.orange);
        }
        if (logica.winner=="O") {
            grilla00.setBackground(Color.cyan);
            grilla01.setBackground(Color.cyan);
            grilla02.setBackground(Color.cyan);
            grilla10.setBackground(Color.cyan);
            grilla11.setBackground(Color.cyan);
            grilla12.setBackground(Color.cyan);
            grilla20.setBackground(Color.cyan);
            grilla21.setBackground(Color.cyan);
            grilla22.setBackground(Color.cyan);
        }
    }
    
    private int findContactoIndexInModel(String name, String ip) {
    for (int i = 0; i < contacModel.size(); i++) {
        Contacto contacto = contacModel.getElementAt(i);
        if ((name != null && contacto.getName().equals(name)) || 
            (ip != null && contacto.getIp().equals(ip))) {
//            return contacto; // Return the matching Contacto
            return i;
        }
    }
        return -1; // No matching Contacto found
    }
    private Contacto findContactoInModel(String name, String ip) {
    for (int i = 0; i < contacModel.size(); i++) {
        Contacto contacto = contacModel.getElementAt(i);
        if ((name != null && contacto.getName().equals(name)) || 
            (ip != null && contacto.getIp().equals(ip))) {
            return contacto; // Return the matching Contacto
        }
    }
    return null; // No matching Contacto found
    }

    public void onMessage(Object c) {
        return;
    }

    @Override
    public void onMessage(Comando c) {
        // tambien se puede hacer (c instanceof MarcarSimbolo)
        //GodmodeMarcarSimbolo (NO tiene limitaciones de turno)
        if (c.getClass()==MarcarSimbolo.class) {
            MarcarSimbolo c2 = (MarcarSimbolo) c;
            logica.setGrilla(c2.valorX, c2.valorY, c2.simbolo);
//            chatAddText(c.comando);
            //Cambiamos el turno
//            logica.setTurnoActual(logica.cambiaTurnos(logica.turnoActual));
//            esMiTurno=true;
            labelTurnoActual.setText("Turno de: "+logica.turnoActual);
            //Todo esto es si NO es mi turno HUEVON
            if (!esMiTurno) {
                switch(c2.valorX) {
                case 0:
                    switch (c2.valorY) {
                        case 0:
                            grilla00.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla01.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla02.setLabel(c2.simbolo);
                            break;
                    }
                    break;
                case 1:
                    switch (c2.valorY) {
                        case 0:
                            grilla10.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla11.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla12.setLabel(c2.simbolo);
                            break;
                    }
                    break;
                    
                case 2:
                    switch (c2.valorY) {
                        case 0:
                            grilla20.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla21.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla22.setLabel(c2.simbolo);
                            break;
                    }
                    break;
            }
//            logica.setTurnoActual(logica.cambiaTurnos(logica.turnoActual));
                if (logica.winner!="")
                    victoria();
                esMiTurno=true;
                labelEsMiTurno.setText("Es mi turno? : "+esMiTurno);
            }
            
        }
        if (c.getClass()==GodmodeMarcarSimbolo.class) {
            GodmodeMarcarSimbolo c2 = (GodmodeMarcarSimbolo) c;
            logica.godmodeSetGrilla(c2.valorX,c2.valorY,c2.simbolo);
//            chat.setText(chat.getText()+System.lineSeparator()+c.comando);
            labelTurnoActual.setText("Turno de: "+logica.turnoActual);
            switch(c2.valorX) {
                case 0:
                    switch (c2.valorY) {
                        case 0:
                            grilla00.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla01.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla02.setLabel(c2.simbolo);
                            break;
                    }
                    break;
                case 1:
                    switch (c2.valorY) {
                        case 0:
                            grilla10.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla11.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla12.setLabel(c2.simbolo);
                            break;
                    }
                    break;
                    
                case 2:
                    switch (c2.valorY) {
                        case 0:
                            grilla20.setLabel(c2.simbolo);
                            break;
                        case 1:
                            grilla21.setLabel(c2.simbolo);
                            break;
                        case 2:
                            grilla22.setLabel(c2.simbolo);
                            break;
                    }
                    break;
            }
//            logica.setTurnoActual(logica.cambiaTurnos(logica.turnoActual));
            if (logica.winner!="")
                victoria();
            
                
                    
        }
        if (c.getClass()==SolicitudConexion.class) {//0001
            SolicitudConexion solicitudConexion = (SolicitudConexion) c;
            this.jugadorBIP = solicitudConexion.getIp();
            int n = JOptionPane.showConfirmDialog(this, solicitudConexion.nombre + " te ha solicitado",
                    "Aceptas?",
                    JOptionPane.YES_NO_OPTION);
            switch (n) {
                case JOptionPane.YES_OPTION ->{
                    //Cuando contesta si
                    contacModel.addElement(new Contacto(solicitudConexion.nombre,solicitudConexion.ip, true));
                    System.out.println("ip en TERUI es: "+c.getIp());
                    Contactos.getInstance().send(c.ip, new AceptacionConexion("Nicolas Crespo").getComando()+System.lineSeparator());          
                    String SQLAdd = """
                           INSERT OR IGNORE INTO Cliente (nombre, ip) VALUES (?, ?);
                           """;
                    try {   
                        PreparedStatement preparedStatement = baseDeDatos.prepareStatement(SQLAdd);
                        preparedStatement.setString(1, solicitudConexion.nombre);
                        preparedStatement.setString(2, c.ip);
                        
                        //Esto es de tipo statement
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Record inserted successfully!");
                        } else {
                            System.out.println("Record already exists. No insertion performed.");
                        }
                        
//                        baseDeDatos.createStatement().execute(preparedStatement);

                    } catch (SQLException ex) {
                        Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                case JOptionPane.NO_OPTION ->
                    Contactos.getInstance().send(c.ip, new RechazoConexion().getComando()+System.lineSeparator());
                default -> {
                }
            }
            return;
//            SolicitudConexionUI scui = new SolicitudConexionUI(solicitudConexion.nombre);
            //Error falso
        }
        if (c.getClass()==AceptacionConexion.class) {//0003
            AceptacionConexion c2 = (AceptacionConexion) c;
            contacModel.addElement(new Contacto(c2.nombre,c2.ip, true));
            jugadorBIP = c2.ip;
            String SQLAdd = """
                   INSERT OR IGNORE INTO Cliente (nombre, ip) VALUES (?, ?);
                   """;
            try {   
                PreparedStatement preparedStatement = baseDeDatos.prepareStatement(SQLAdd);
                preparedStatement.setString(1, c2.nombre);
                preparedStatement.setString(2, c2.ip);

                //Esto es de tipo statement
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Record already exists. No insertion performed.");
                }

//                        baseDeDatos.createStatement().execute(preparedStatement);

            } catch (SQLException ex) {
                Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (c.getClass()==NuevaPartida.class) {
            NuevaPartida c2 = (NuevaPartida) c;
            reiniciarGrilla();
        }
        if (c.getClass() == CerrarYBorrar.class) {
            System.out.println("Ejecutando Cerrar y Borrar");
            CerrarYBorrar c2 = (CerrarYBorrar) c;
            
            socketClient.closeConnection();
            servidorJuego.client.closeConnection();
            //fallback de emergencia porque ALGO ESTA MAL CON EL contacList
            jLContactos.removeAll();
            contacModel.remove(findContactoIndexInModel(c2.nombre,c2.ip));
//            System.exit(0);
        }
        if (c.getClass() == MarcarSimbolo.class) {
            
        }
        return;
    }
    public void chatAddText(String newText) {
        chat.setText(chat.getText()+newText+System.lineSeparator());
    }
    public void refresh() {
        String SQLAdd = "SELECT * FROM Cliente";
            try {   
                PreparedStatement preparedStatement = baseDeDatos.prepareStatement(SQLAdd);
                //Esto es de tipo statement
//                ResultSet resultSet = preparedStatement.getResultSet();
                int ranOnce = 0;
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ranOnce+=1;
                    String ip = resultSet.getString("ip");
                    String nombre = resultSet.getString("nombre");
                    contacModel.addElement(new Contacto(nombre,ip, true));
//                    try {
//                        SocketClient socketClient = new SocketClient(new Socket(ip,1825));
//                    } catch (IOException ex) {
//                        Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    Contactos.getInstance().onNewClient(socketClient);
                    System.out.println("Contactos refrescados exitosamente");
                }
                if (ranOnce==0) {
                    contacModel.clear();
                    System.out.println("La lista estaba vacia. Vaciando...");
                }
                
//                if (rowsAffected > 0) {
//                    System.out.println("Record inserted successfully!");
//                } else {
//                    System.out.println("Record already exists. No insertion performed.");
//                }

//                        baseDeDatos.createStatement().execute(preparedStatement);

            } catch (SQLException ex) {
                Logger.getLogger(TresEnRayaUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    private void addFocusListenerToButtons(java.awt.Button... buttons) {
    for (java.awt.Button button : buttons) {
        button.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                TresEnRayaUI.this.requestFocusInWindow();
            }
        });
        }
    }

    private void retar(int selectedIndex) {
        Object[] options = {"X", "O"};

        // Show the dialog and get the user's choice
        int choice = JOptionPane.showOptionDialog(
            this, // Parent component (the current frame)
            "Seleccione X o O", // Message to display
            "Selección de Símbolo", // Title of the dialog
            JOptionPane.DEFAULT_OPTION, // Option type
            JOptionPane.QUESTION_MESSAGE, // Message type
            null, // Icon (null for default)
            options, // Array of options (buttons)
            options[0] // Default selected option
        );

        // Handle the user's choice
        // Si, hay muchas lineas repetidas porque si no me da miedo que el else haga algo.
        if (choice == 0) {
            System.out.println("El usuario seleccionó X");
            logica.turnoActual="X";
            Contactos.getInstance().send(jugadorBIP, "0004|X"+System.lineSeparator());
            esMiTurno=true;
            labelEsMiTurno.setText("Es mi turno? : TRUE");
        } else if (choice == 1) {
            System.out.println("El usuario seleccionó O");
            logica.turnoActual="O";
            Contactos.getInstance().send(jugadorBIP, "0004|O"+System.lineSeparator());
            esMiTurno=true;
            labelEsMiTurno.setText("Es mi turno? : TRUE");
        } else {
            System.out.println("El usuario cerró el diálogo sin seleccionar");
        }
    }
    private void bindKeyToJButton(JButton button, String actionKey, int keyCode, int modifiers) {
        // Get the input and action maps
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = button.getActionMap();

        // Create the key stroke
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);

        // Map the key stroke to the action
        inputMap.put(keyStroke, actionKey);

        // Define what happens when the key is pressed
        actionMap.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick(); // Simulate button click
                // Or call your method directly:
                // handleUndo(); etc.
            }
        });
    }
    private void setupJButtonKeyBindings() {
    
    bindKeyToJButton(btnServer, "ctrl H", KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK);
    
    bindKeyToJButton(btnConectar, "ctrl C", KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
    
    bindKeyToJButton(btnReinicio, "ctrl R", KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
    
    bindKeyToJButton(btnRefreshContactoLista, "shift R", KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK);
    
    bindKeyToJButton(enviarMensaje, "ctrl ENTER", KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
    
    bindKeyToJButton(conexionContacto1, "ctrl 1", KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK);
    
    // Add more bindings as needed...
    }
}
