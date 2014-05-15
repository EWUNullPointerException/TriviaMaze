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

    public static void main(String[] args)
    {
        Display display = new Display();
        shell = new Shell(display);

        // Dan
        // must be called before setSize is called to make sure
        // the components are placed correctly for some odd reason.
        // This makes it almost impossible to set anything relative
        // to the size of the window so I've moved width and height
        // to finals
        shell.setSize(418, 384);
        shell.setLayout(new FormLayout());

        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);

        Menu menu_1 = gameMenu(menu);
        startGame(menu_1);
        endlessMode(menu_1);
        exit(menu_1);

        Menu menu_2 = helpMenu(menu);
        howToPlay(menu_2);
        aboutGame(menu_2);

        Menu menu_3 = extrasMenu(menu);
        customQuestions(menu_3);
        leaderBoard(menu_3);

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

    private static Menu gameMenu(Menu menu)
    {
        MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
        mntmFile.setText("Game");

        Menu menu_1 = new Menu(mntmFile);
        mntmFile.setMenu(menu_1);
        return menu_1;
    }

    private static void startGame(Menu menu_1)
    {
        MenuItem mntmNewGame = new MenuItem(menu_1, SWT.NONE);
        mntmNewGame.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                userGame = new Game();
                System.out.println("Maze generated");
            }
        });
        mntmNewGame.setText("Start Game");
    }

    private static void endlessMode(Menu menu_1)
    {
        MenuItem mntmEndlessMode = new MenuItem(menu_1, SWT.NONE);
        mntmEndlessMode.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // Begin endless mode here
                System.out.println("Endless mode started");
            }
        });
        mntmEndlessMode.setText("Endless Mode");
    }

    private static void exit(Menu menu_1)
    {
        MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
        mntmExit.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                shell.dispose();
            }
        });
        mntmExit.setText("Exit");
    }

    private static Menu helpMenu(Menu menu)
    {
        MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
        mntmHelp.setText("Help");

        Menu menu_2 = new Menu(mntmHelp);
        mntmHelp.setMenu(menu_2);
        return menu_2;
    }

    private static void howToPlay(Menu menu_2)
    {
        MenuItem mntmHowToPlay = new MenuItem(menu_2, SWT.NONE);
        mntmHowToPlay.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.println("How to play");
            }
        });
        mntmHowToPlay.setText("How To Play");
    }

    private static void aboutGame(Menu menu_2)
    {
        MenuItem mntmAbout = new MenuItem(menu_2, SWT.NONE);
        mntmAbout.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.println("About game");
            }
        });
        mntmAbout.setText("About");
    }

    private static Menu extrasMenu(Menu menu)
    {
        MenuItem mntmExtras = new MenuItem(menu, SWT.CASCADE);
        mntmExtras.setText("Extras");
        Menu menu_3 = new Menu(mntmExtras);
        mntmExtras.setMenu(menu_3);
        return menu_3;
    }

    private static void customQuestions(Menu menu_3)
    {
        MenuItem mntmCustomQuestions = new MenuItem(menu_3, SWT.NONE);
        mntmCustomQuestions.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.println("Custom questions");
            }
        });
        mntmCustomQuestions.setText("Custom Questions");
    }

    private static void leaderBoard(Menu menu_3)
    {
        MenuItem mntmLeaderBoard = new MenuItem(menu_3, SWT.NONE);
        mntmLeaderBoard.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.println("Leader Board");
            }
        });
        mntmLeaderBoard.setText("Leader Board");
    }
}
