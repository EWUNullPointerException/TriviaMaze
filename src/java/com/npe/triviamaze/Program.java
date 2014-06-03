package com.npe.triviamaze;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.npe.triviamaze.game.Direction;
import com.npe.triviamaze.game.Game;
import com.npe.triviamaze.game.Location;
import com.npe.triviamaze.game.Maze;
import com.npe.triviamaze.game.Player;
import com.npe.triviamaze.game.Room;

public class Program
{
    private static final class KeyPressedEvent extends KeyAdapter implements Listener
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(userGame == null)
                return;

            if(!canMove)
            {
                if(e.keyCode == SWT.CR)
                {
                    submitAnswerBtn.notifyListeners(SWT.Selection, new Event());
                }
                return;
            }

            Direction direction = null;
            if(e.keyCode == SWT.ARROW_RIGHT)
            {
                direction = Direction.Right;
            }
            else if(e.keyCode == SWT.ARROW_DOWN)
            {
                direction = Direction.Down;
            }
            else if(e.keyCode == SWT.ARROW_LEFT)
            {
                direction = Direction.Left;
            }
            else if(e.keyCode == SWT.ARROW_UP)
            {
                direction = Direction.Up;
            }
            else
            {
                return;
            }

            answerTxt.setText("");

            if(!userGame.canMove(direction))
            {
                return;
            }
            if(userGame.directionOpen(direction))
            {
                userGame.move(direction);
                mazeFrame.redraw();
                checkGameWon();
                checkGameLost();
            }
            else
            {
                canMove = false;
                dir = direction;
                StringBuilder tmp = new StringBuilder();
                String[] xs = userGame.getQuestion(direction);
                for(int i = 0; i < xs.length; i++)
                    tmp.append(xs[i] + "\n");
                questionLbl.setText(tmp.toString());
                answerTxt.setFocus();
            }
        }

        @Override
        public void handleEvent(Event event)
        {
            KeyEvent e = new KeyEvent(event);
            keyPressed(e);
        }
    }

    private static final class GUIDraw implements PaintListener
    {

        private GC gfx;

        private int roomWidth, roomHeight;
        private int xStart, yStart;
        private Color prevFore, prevBack;

        private int vertOffset = 11, horzOffset = 11;

        public void paintControl(PaintEvent e)
        {
            if(userGame == null)
                return;

            if(maze == null)
                maze = userGame.getMaze();
            if(player == null)
                player = userGame.getPlayer();

            storeSettings(e);

            drawBorder(roomWidth * maze.cols, roomHeight * maze.rows);
            drawMaze();
            drawPlayer();
        }

        private void storeSettings(PaintEvent e)
        {
            gfx = e.gc;
            gfx.setLineWidth(2);
            prevFore = gfx.getForeground();
            prevBack = gfx.getBackground();
            roomWidth = (mazeFrame.getBounds().width - horzOffset) / maze.cols;
            roomHeight = (mazeFrame.getBounds().height - vertOffset) / maze.rows;
            xStart = mazeFrame.getBounds().x;
            yStart = mazeFrame.getBounds().y;
        }

        private void drawPlayer()
        {
            int offset = 20;
            Location loc = player.getLocation();
            Rectangle rect = new Rectangle(xStart + (loc.col - 1) * roomWidth + offset / 2, yStart
                    + (loc.row - 1) * roomHeight + offset / 2, roomWidth - offset, roomHeight
                    - offset);
            gfx.setBackground(blue);
            gfx.fillRectangle(rect);
            gfx.setBackground(prevBack);
        }

        private void drawMaze()
        {
            Room r = null;
            for(int row = 1; row <= maze.rows; row++)
            {
                for(int col = 1; col <= maze.cols; col++)
                {
                    r = maze.getRoom(row, col);
                    if(r == null)
                        continue;

                    // Right Door
                    if(r.isDoorOpen(Direction.Right))
                        gfx.setForeground(green);
                    else if(r.isDoorLocked(Direction.Right))
                        gfx.setForeground(red);
                    else
                        gfx.setForeground(black);

                    int x = xStart + roomWidth * col, y = yStart + roomHeight * (row - 1);
                    int x2 = xStart + roomWidth * col, y2 = yStart + roomHeight * row;

                    gfx.drawLine(x, y, x2, y2);
                    gfx.setForeground(prevFore);

                    // Bottom Door
                    if(r.isDoorOpen(Direction.Down))
                        gfx.setForeground(green);
                    else if(r.isDoorLocked(Direction.Down))
                        gfx.setForeground(red);
                    else
                        gfx.setForeground(black);

                    x = xStart + roomWidth * (col - 1);
                    y = yStart + roomHeight * row;
                    x2 = xStart + roomWidth * col;
                    y2 = yStart + roomHeight * row;

                    gfx.drawLine(x, y, x2, y2);
                    gfx.setForeground(prevFore);
                }
            }
        }

        private void drawBorder(int width, int height)
        {
            Rectangle bounds = mazeFrame.getBounds();
            // top line
            gfx.drawLine(bounds.x, bounds.y, bounds.x + width, bounds.y);
            // left line
            gfx.drawLine(bounds.x, bounds.y, bounds.x, bounds.y + height);

            // bottom line
            // gfx.drawLine(bounds.x, bounds.y + bounds.height - vertOffset, bounds.x +
            // bounds.width,
            // bounds.y + bounds.height - vertOffset);
            // right line
            // gfx.drawLine(bounds.x + bounds.width - horzOffset, bounds.y, bounds.x + bounds.width
            // - horzOffset, bounds.y + bounds.height);

        }
    }

    private static Shell shell;
    private static Game userGame;
    private static Menu mainMenu;
    private static MenuItem gameMenu;
    private static MenuItem startGameMenuCascade;
    private static MenuItem computerSci;
    private static MenuItem movies;
    private static MenuItem endlessModeMenuItem;
    private static MenuItem exitMenuItem;
    private static MenuItem helpMenu;
    private static MenuItem howToPlayMenuItem;
    private static MenuItem aboutMenuItem;
    private static MenuItem sourceMenuItem;
    private static MenuItem extrasMenu;
    private static MenuItem customQuestionsMenuItem;
    private static MenuItem leaderBoardMenuItem;
    private static Composite gameFrame;
    private static Canvas mazeFrame;
    private static Composite questionFrame;
    private static Label questionLbl;
    private static Composite answerFrame;
    private static String dbChoice;

    private static Color red;
    private static Color green;
    private static Color blue;
    private static Color black;

    private static Player player;
    private static Maze maze;
    private static boolean canMove;
    private static Direction dir;
    private static Text answerTxt;
    private static Button submitAnswerBtn;

    public static void main(String[] args)
    {

        Display display = new Display();
        display.addFilter(SWT.KeyDown, new KeyPressedEvent());
        shell = new Shell(display);

        shell.setSize(608, 497);
        shell.setLayout(new FormLayout());

        setColors(display);
        init();
        gameFrame.setVisible(false);

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

    private static void setColors(Display d)
    {
        red = d.getSystemColor(SWT.COLOR_RED);
        green = d.getSystemColor(SWT.COLOR_GREEN);
        blue = d.getSystemColor(SWT.COLOR_BLUE);
        black = d.getSystemColor(SWT.COLOR_BLACK);
    }

    private static void init()
    {
        mainMenu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(mainMenu);

        gameMenu = new MenuItem(mainMenu, SWT.CASCADE);
        gameMenu.setText("&Game");

        Menu menu_1 = new Menu(gameMenu);
        gameMenu.setMenu(menu_1);

        startGameMenuCascade = new MenuItem(menu_1, SWT.CASCADE);
        startGameMenuCascade.setText("&Start Game");

        Menu menu = new Menu(startGameMenuCascade);
        startGameMenuCascade.setMenu(menu);

        computerSci = new MenuItem(menu, SWT.NONE);
        computerSci.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.print("Comp Sci");
                dbChoice = "CS";
                userGame = new Game(5, 5, "CompSci");
                player = userGame.getPlayer();
                maze = userGame.getMaze();
                mazeFrame.redraw();
                gameFrame.setVisible(true);
                canMove = true;
                questionLbl.setText("");
                dir = null;
            }
        });
        computerSci.setText("&Computer Science");

        movies = new MenuItem(menu, SWT.NONE);
        movies.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                System.out.print("Movies");
                dbChoice = "Movies";
                userGame = new Game(5, 5, "Movies");
                player = userGame.getPlayer();
                maze = userGame.getMaze();
                mazeFrame.redraw();
                gameFrame.setVisible(true);
                canMove = true;
                questionLbl.setText("");
                dir = null;
            }
        });
        movies.setText("&Movies");

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
                MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
                dialog.setText("Playing Instructions");
                dialog.setMessage("Use the keyboard arrows to move within the maze. \n Correct answers to trivia questions will unlock doors. \n The goal is to reach the exit, which is the lower right corner.");
                dialog.open();
            }
        });
        howToPlayMenuItem.setText("&How To Play");

        aboutMenuItem = new MenuItem(menu_2, SWT.NONE);
        aboutMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                dialog.setText("About");
                dialog.setMessage("Computer Science Trivia Maze Version 1.0 \n Coded by Stefan Bostain, Stacy Carlson, and Dan Watt");
                dialog.open();
            }
        });
        aboutMenuItem.setText("&About");

        sourceMenuItem = new MenuItem(menu_2, SWT.NONE);
        sourceMenuItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                dialog.setText("Sources");
                dialog.setMessage("Building Java Programs: A Back to Basics Approach \n By Stuart Reges and Marty Stepp \n\n Data Structures with Java \n By John R. Hubbard \n\n www.imdb.com");
                dialog.open();
            }
        });
        sourceMenuItem.setText("&Sources");

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
                // TODO
                System.out.println("Leader board");
            }
        });
        leaderBoardMenuItem.setText("&Leader Board");

        gameFrame = new Composite(shell, SWT.NONE);
        gameFrame.setLayout(new FormLayout());
        FormData fd_gameFrame = new FormData();
        fd_gameFrame.top = new FormAttachment(0, 10);
        fd_gameFrame.left = new FormAttachment(0, 10);
        fd_gameFrame.right = new FormAttachment(100, -10);
        fd_gameFrame.bottom = new FormAttachment(100, -10);
        gameFrame.setLayoutData(fd_gameFrame);

        questionFrame = new Composite(gameFrame, SWT.NONE);
        FormData fd_questionFrame = new FormData();
        fd_questionFrame.left = new FormAttachment(100, -170);
        fd_questionFrame.right = new FormAttachment(100, -10);
        fd_questionFrame.top = new FormAttachment(0, 10);
        fd_questionFrame.bottom = new FormAttachment(0, 133);
        questionFrame.setLayoutData(fd_questionFrame);

        questionLbl = new Label(questionFrame, SWT.WRAP | SWT.HORIZONTAL);
        questionLbl.setLocation(0, 0);
        questionLbl.setSize(160, 123);

        mazeFrame = new Canvas(gameFrame, SWT.NONE);
        FormData fd_mazeFrame = new FormData();
        fd_mazeFrame.left = new FormAttachment(0, 10);
        fd_mazeFrame.top = new FormAttachment(0, 10);
        fd_mazeFrame.bottom = new FormAttachment(100, -10);
        fd_mazeFrame.right = new FormAttachment(questionFrame, -10);
        mazeFrame.setLayoutData(fd_mazeFrame);
        mazeFrame.addPaintListener(new GUIDraw());

        answerFrame = new Composite(gameFrame, SWT.NONE);
        FormData fd_answerFrame = new FormData();
        fd_answerFrame.top = new FormAttachment(questionFrame, 6);
        fd_answerFrame.left = new FormAttachment(questionFrame, 0, SWT.LEFT);
        fd_answerFrame.right = new FormAttachment(100, -10);
        fd_answerFrame.bottom = new FormAttachment(100, -123);
        answerFrame.setLayoutData(fd_answerFrame);

        submitAnswerBtn = new Button(answerFrame, SWT.NONE);
        submitAnswerBtn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                if(dir == null)
                    return;
                userGame.answerQuestion(answerTxt.getText(), dir);
                userGame.move(dir);
                mazeFrame.redraw();
                canMove = true;
                dir = null;
                answerTxt.setText("");
                questionLbl.setText("");
                shell.setFocus();
                checkGameWon();
                checkGameLost();
            }
        });
        submitAnswerBtn.setBounds(0, 132, 160, 25);
        submitAnswerBtn.setText("Submit");

        answerTxt = new Text(answerFrame, SWT.BORDER);
        answerTxt.setBounds(0, 0, 160, 45);

    }

    private static void checkGameWon()
    {
        if(userGame == null)
            return;
        if(userGame.isGameWon())
        {
            MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
            dialog.setText("Congratulations!");
            dialog.setMessage("You win! \n Play again?");
            int returnCode = dialog.open();
            if(returnCode == SWT.YES)
            {
                // User hit yes
                if(dbChoice.equals("CS"))
                {
                    computerSci.notifyListeners(SWT.Selection, new Event());
                }
                else
                {
                    movies.notifyListeners(SWT.Selection, new Event());
                }
            }
            else
            {
                gameFrame.setVisible(false);
                userGame = null;
                player = null;
                maze = null;
                dbChoice = null;
            }
        }
    }

    private static void checkGameLost()
    {
        if(userGame == null)
            return;
        if(userGame.isGameOver())
        {
            MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
            dialog.setText("Sorry!");
            dialog.setMessage("Game Over! \n Play again?");
            int returnCode = dialog.open();
            if(returnCode == SWT.YES)
            {
                // User hit yes
                if(dbChoice.equals("CS"))
                {
                    computerSci.notifyListeners(SWT.Selection, new Event());
                }
                else
                {
                    movies.notifyListeners(SWT.Selection, new Event());
                }
            }
            else
            {
                gameFrame.setVisible(false);
                userGame = null;
                player = null;
                maze = null;
                dbChoice = null;
            }
        }
    }
}
