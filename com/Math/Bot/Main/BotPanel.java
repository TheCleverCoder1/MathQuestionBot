package com.Math.Bot.Main;

import com.Math.Bot.Main.Screens.*;
import com.Math.Bot.Main.Screens.Menu;
import com.Math.Bot.Main.Utils.Editable.Editable;
import com.Math.Bot.Main.Utils.Editable.EditableHandler;
import com.Math.Bot.Main.Utils.FontHandler.FontHandler;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Input.KeyHandler;
import com.Math.Bot.Main.Utils.Input.MouseHandler;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.TextHandler.TextHandler;

import javax.swing.*;
import java.awt.*;

// Optimized State 3
public class BotPanel extends JPanel implements Runnable {
    // Current Instance
    public static BotPanel bp;

    // Window Variables
    private JFrame frame;
    private Thread thread;
    public short sWidth = 1200;
    public short sHeight = 800;

    /*
     * Use Ctrl + Esc to de-active or stop
     * Not implemented yet.
     */
    private boolean active = true; // if the loop is active

    // Utils
    public KeyHandler kh;
    public MouseHandler mh;

    // Window State
    public byte cState;
    public final byte numOfStates = 2;
    public final byte MENU_STATE = 0;
    public final byte ALGEBRA_SOLVER_STATE = 1;

    // Screens
    private HomeMenu homeMenu;
    private AlgebraSolverMenu algebraSolverMenu;

    // Debug
    public boolean debug;
    public Debugger debugger;

    public BotPanel(){
        BotPanel.bp = this;
        init();
    }

    // Initializing
    private void initializeWindow(){
        // Initializing Panel
        setPreferredSize(new Dimension(sWidth, sHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        setFocusable(true);
        setOpaque(true);
        addKeyListener(kh);
        addMouseListener(mh);
        addMouseWheelListener(mh);
        addMouseMotionListener(mh);

        // Initializing Frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setTitle("Math Questions Bot 0.0.1");
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
    private void initializeResources(){
        FontHandler.init();
        ImageHandler.LoadImages();
        TextHandler.init();
    }
    private void initializeUtilities(){
        kh = new KeyHandler();
        mh = new MouseHandler();
        debugger = new Debugger();
    }
    private void initializeScreens(){
        homeMenu = new HomeMenu();
        algebraSolverMenu = new AlgebraSolverMenu();
    }
    private void init(){
        long cTotalTime = System.nanoTime();
        initializeResources();
        initializeUtilities();
        initializeScreens();
        initializeWindow();
        System.out.println("Initialization Completed in " + ((System.nanoTime() - cTotalTime)/1_000_000_000D) + " (secs).\n\n\n\n");
        cState = MENU_STATE;
        start();
    }
    private void start(){
        // Starting the Loop
        thread = new Thread(this);
        thread.start();
    }

    // Main Loop
    @Override public void run() {
        long lTime = System.nanoTime();
        final long dTime = 1_000_000_000L / 60; // 60 is the FPS
        while (active) {
            // FPS Handling
            if (lTime + dTime > System.nanoTime()) {
                update();
                repaint();

                // Resetting the lastTime Variable
                lTime = System.nanoTime();
            }
        }
    }

    // Updating
    private void updateScreens(){
        Menu screen;
        switch (cState){
            case MENU_STATE -> screen = homeMenu;
            case ALGEBRA_SOLVER_STATE -> screen = algebraSolverMenu;
            default -> {return;}
        }
        screen.update();

        // Giving the Editables to editable handler.
        EditableHandler.setEditable(
                new Editable[][]{
                        screen.getButtons(),
                        screen.getTextWriters()
                }
        );
    }
    private void update(){
        updateScreens();
        debugger.update();
    }

    // Drawing
    private void drawScreens(){
        switch (cState){
            case MENU_STATE -> homeMenu.draw();
            case ALGEBRA_SOLVER_STATE -> algebraSolverMenu.draw();
        }
    }
    @Override public void paintComponent(Graphics _g){
        // Initializing Graphics
        super.paintComponent(_g);
        Graphics2D g = (Graphics2D) _g;
        Sprite.g = g;

        // Drawing
        drawScreens();
        debugger.draw();

        // Disposing the Graphics
        g.dispose();
    }

    // Some Time Saving Stuff to Understand Well
    public String getEditableString(byte editableConstant){
        return (editableConstant == Debugger.BUTTON ? "BUTTON" : (editableConstant == Debugger.TEXT_BOX ? "TEXT BOX" : "NuLL"));
    }
}