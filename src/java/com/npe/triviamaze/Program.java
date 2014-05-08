package com.npe.triviamaze;

import javax.swing.*;
import java.awt.Dimension;

public class Program
{
    public static void main(String[] args)
    {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI()
    {
        // Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setTitle("TriviaMaze");
        frame.setSize(new Dimension(299, 302));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar mainMenu = new JMenuBar();
        frame.setJMenuBar(mainMenu);
        
        JMenuItem mnuItmFile = new JMenuItem("File");
        mnuItmFile.setMnemonic('F');
        mainMenu.add(mnuItmFile);

        // Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
}
