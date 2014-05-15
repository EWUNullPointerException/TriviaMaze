package com.npe.triviamaze;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Program
{
    private static final int WIDTH = 361;
    private static final int HEIGHT = 348;
    private static Shell shell;
    private static Button btnExit;
    private static Button btnEndlessMode;
    private static Button btnLeaderBoard;
    private static Button btnStart;
    private static Button btnCustomQuestions;
    private static FormData fd_btnNewButton_1;
    private static FormData fd_btnEndlessMode;

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
        //init();
        shell.setSize(418, 384);
        shell.setLayout(new FormLayout());
        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);

        MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
        mntmFile.setText("Game");

        Menu menu_1 = new Menu(mntmFile);
        mntmFile.setMenu(menu_1);

        MenuItem mntmNewGame = new MenuItem(menu_1, SWT.NONE);
        mntmNewGame.addSelectionListener(new SelectionAdapter()
        {
            private Game userGame;

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                userGame = new Game();
                System.out.println("Maze generated");
            }
        });
        mntmNewGame.setText("Start Game");

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

        MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
        mntmHelp.setText("Help");

        Menu menu_2 = new Menu(mntmHelp);
        mntmHelp.setMenu(menu_2);

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

        MenuItem mntmExtras = new MenuItem(menu, SWT.CASCADE);
        mntmExtras.setText("Extras");

        Menu menu_3 = new Menu(mntmExtras);
        mntmExtras.setMenu(menu_3);

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

       // startButton();
       // endlessModeButton();
       // leaderBoardButton();
       // customQuestionsButton();

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

    private static void endlessModeButton()
    {
        // Stacy
        btnEndlessMode = new Button(shell, SWT.NONE);
        btnEndlessMode.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // Begin endless mode here
                System.out.println("Endless mode started");
            }
        });
        fd_btnEndlessMode = new FormData();
        fd_btnEndlessMode.top = new FormAttachment(btnExit, 0, SWT.TOP);
        btnEndlessMode.setLayoutData(fd_btnEndlessMode);
        btnEndlessMode.setText("Endless Mode");
    }

    private static void startButton()
    {
        // Stacy
        btnStart = new Button(shell, SWT.NONE);
        btnStart.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // Generate a maze
                Maze userMaze = new Maze();
                System.out.println("Maze generated");
            }
        });
        fd_btnNewButton_1 = new FormData();
        fd_btnNewButton_1.top = new FormAttachment(btnExit, 0, SWT.TOP);
        fd_btnNewButton_1.left = new FormAttachment(btnExit, 6);
        btnStart.setLayoutData(fd_btnNewButton_1);
        btnStart.setText("Start Game");
    }

    private static void leaderBoardButton()
    {
        // Stacy
        btnLeaderBoard = new Button(shell, SWT.NONE);
        fd_btnEndlessMode.right = new FormAttachment(btnLeaderBoard, -6);
        btnLeaderBoard.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // Display the leaderboard
                System.out.println("Display the leader board of high scores");
            }
        });
        FormData fd_btnLeaderBoard = new FormData();
        fd_btnLeaderBoard.top = new FormAttachment(btnExit, 0, SWT.TOP);
        fd_btnLeaderBoard.left = new FormAttachment(0, 203);
        btnLeaderBoard.setLayoutData(fd_btnLeaderBoard);
        btnLeaderBoard.setText("Leader Board");

    }

    private static void customQuestionsButton()
    {
        {
            btnCustomQuestions = new Button(shell, SWT.NONE);
            btnCustomQuestions.addSelectionListener(new SelectionAdapter()
            {
                @Override
                public void widgetSelected(SelectionEvent e)
                {
                    //Add custom questions to the database
                    System.out.println("Custom Questions in the database");
                }
            });
            FormData fd_btnNewButton = new FormData();
            fd_btnNewButton.top = new FormAttachment(btnExit, 0, SWT.TOP);
            fd_btnNewButton.left = new FormAttachment(btnLeaderBoard, 6);
            btnCustomQuestions.setLayoutData(fd_btnNewButton);
            btnCustomQuestions.setText("Custom Questions");
        }
    }

    private static void init()
    {
        // Dan

        btnExit = new Button(shell, SWT.PUSH);
        FormData fd_exitBtn = new FormData();
        fd_exitBtn.top = new FormAttachment(0);
        fd_exitBtn.left = new FormAttachment(0);
        btnExit.setLayoutData(fd_exitBtn);

        // Adds functionality to the button by adding a listener for
        // being clicked, or as SWT has defined it, for "selection"
        btnExit.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                // Not sure if this conditional is needed as it seems
                // to work as expected without it.
                // if(event.type == SWT.Selection)
                shell.dispose();
            }
        });
        btnExit.setText("Exit");
        // Resize the control before moving it.
        btnExit.pack();

    }
}
