package com.npe.triviamaze;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.npe.triviamaze.game.Game;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class Program
{
    private static Shell shell;
    private static Game userGame;
    private static Menu mainMenu;
    private static MenuItem gameMenu;
    private static MenuItem startGameMenuItem;
    private static MenuItem endlessModeMenuItem;
    private static MenuItem exitMenuItem;
    private static MenuItem helpMenu;
    private static MenuItem howToPlayMenuItem;
    private static MenuItem aboutMenuItem;
    private static MenuItem extrasMenu;
    private static MenuItem customQuestionsMenuItem;
    private static MenuItem leaderBoardMenuItem;

    public static void main(String[] args)
    {
        Display display = new Display();
        shell = new Shell(display);

        shell.setSize(418, 384);
        shell.setLayout(new FormLayout());

        init();

        shell.open();
        // run the event loop as long as the window is open
        while(!shell.isDisposed())
        {
            // read the next OS event queue and transfer it to a SWT event
            if(!display.readAndDispatch())
            {
                // if there are currently no other OS event to process
                // sleep until the next OS event is available
                display.sleep();
            }
        }

        // disposes all associated windows and their components
        display.dispose();
    }

    private static void init()
    {
        mainMenu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(mainMenu);

        gameMenu = new MenuItem(mainMenu, SWT.CASCADE);
        gameMenu.setText("&Game");

        Menu menu_1 = new Menu(gameMenu);
        gameMenu.setMenu(menu_1);

        // Start normal game
        startGameMenuItem = new MenuItem(menu_1, SWT.NONE);
        startGameMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // TODO
                System.out.println("Game started");
            }
        });
        startGameMenuItem.setText("&Start Game");

        // Endless Mode
        endlessModeMenuItem = new MenuItem(menu_1, SWT.NONE);
        endlessModeMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // TODO
                System.out.println("Endless Mode started");
            }
        });
        endlessModeMenuItem.setText("&Endless Mode");

        exitMenuItem = new MenuItem(menu_1, SWT.NONE);
        exitMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                shell.dispose();
            }
        });
        exitMenuItem.setText("E&xit");

        helpMenu = new MenuItem(mainMenu, SWT.CASCADE);
        helpMenu.setText("&Help");

        Menu menu_2 = new Menu(helpMenu);
        helpMenu.setMenu(menu_2);

        howToPlayMenuItem = new MenuItem(menu_2, SWT.NONE);
        howToPlayMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // TODO
                System.out.println("How to play");
            }
        });
        howToPlayMenuItem.setText("&How To Play");

        aboutMenuItem = new MenuItem(menu_2, SWT.NONE);
        aboutMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // TODO
                System.out.println("About game");
            }
        });
        aboutMenuItem.setText("&About");

        extrasMenu = new MenuItem(mainMenu, SWT.CASCADE);
        extrasMenu.setText("&Extras");
        Menu menu_3 = new Menu(extrasMenu);
        extrasMenu.setMenu(menu_3);

        customQuestionsMenuItem = new MenuItem(menu_3, SWT.NONE);
        customQuestionsMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // TODO
                System.out.println("Custom Questions");
            }
        });
        customQuestionsMenuItem.setText("&Custom Questions");

        leaderBoardMenuItem = new MenuItem(menu_3, SWT.NONE);
        leaderBoardMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                //TODO
                System.out.println("Leader board");
            }
        });
        leaderBoardMenuItem.setText("&Leader Board");

    }
}
