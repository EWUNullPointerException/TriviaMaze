package com.npe.triviamaze;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.npe.triviamaze.game.Direction;
import com.npe.triviamaze.game.Game;
import com.npe.triviamaze.game.Location;
import com.npe.triviamaze.game.Maze;
import com.npe.triviamaze.game.Player;
import com.npe.triviamaze.game.Room;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class Program {
	private static final class GUIDraw implements PaintListener {
		private Maze maze;
		private Player player;
		private GC gfx;

		private int roomWidth, roomHeight;
		private int xStart, yStart;
		private Color prevFore, prevBack;

		public void paintControl(PaintEvent e) {
			if (userGame == null)
				return;

			if (maze == null)
				maze = userGame.getMaze();
			if (player == null)
				player = userGame.getPlayer();

			storeSettings(e);

			drawBorder();
			drawMaze();
			drawPlayer();
		}

		private void storeSettings(PaintEvent e) {
			gfx = e.gc;
			gfx.setLineWidth(2);
			prevFore = gfx.getForeground();
			prevBack = gfx.getBackground();
			roomWidth = mazeFrame.getBounds().width / maze.cols;
			roomHeight = mazeFrame.getBounds().height / maze.rows;
			xStart = mazeFrame.getBounds().x;
			yStart = mazeFrame.getBounds().y;
		}

		private void drawPlayer() {
			int offset = 20;
			Location loc = player.getLocation();
			Rectangle rect = new Rectangle(xStart + (loc.col - 1) * roomWidth
					+ offset / 2, yStart + (loc.row - 1) * roomHeight + offset
					/ 2, roomWidth - offset, roomHeight - offset);
			gfx.setBackground(blue);
			gfx.fillRectangle(rect);
			gfx.setBackground(prevBack);
		}

		private void drawMaze() {
			Room r = null;
			for (int row = 1, col; row <= maze.rows; row++) {
				for (col = 1; col <= maze.cols; col++) {
					r = maze.getRoom(row, col);
					if (r == null)
						continue;

					// Right Door
					if (r.isDoorOpen(Direction.Right))
						gfx.setForeground(green);
					else if (r.isDoorLocked(Direction.Right))
						gfx.setForeground(red);
					else
						gfx.setForeground(black);

					int x = xStart + roomWidth * col, y = yStart + roomHeight
							* (row - 1), x2 = xStart + roomWidth * col, y2 = yStart
							+ roomHeight * row;

					gfx.drawLine(x, y, x2, y2);
					gfx.setForeground(prevFore);

					// Bottom Door
					if (r.isDoorOpen(Direction.Down))
						gfx.setForeground(green);
					else if (r.isDoorLocked(Direction.Down))
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

		private void drawBorder() {
			Rectangle bounds = mazeFrame.getBounds();
			int vertOffset = 11, horzOffset = 11;
			// top line
			gfx.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y);
			// bottom line
			gfx.drawLine(bounds.x, bounds.y + bounds.height - vertOffset,
					bounds.x + bounds.width, bounds.y + bounds.height
							- vertOffset);
			// left line
			gfx.drawLine(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height);
			// right line
			gfx.drawLine(bounds.x + bounds.width - horzOffset, bounds.y,
					bounds.x + bounds.width - horzOffset, bounds.y
							+ bounds.height);

		}
	}

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
	private static Composite gameFrame;
	private static Canvas mazeFrame;
	private static Composite questionFrame;
	private static Label questionLbl;
	private static Composite answerFrame;

	private static Color red;
	private static Color green;
	private static Color blue;
	private static Color black;
	private static Label lblOptions;

	public static void main(String[] args) {

		Display display = new Display();
		shell = new Shell(display);
		shell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (userGame == null) return;
				
				if (e.keyCode == SWT.ARROW_RIGHT) {
					userGame.moveRight();
				}
				if (e.keyCode == SWT.ARROW_DOWN) {
					userGame.moveDown();
				}
				if (e.keyCode == SWT.ARROW_LEFT) {
					userGame.moveLeft();
				}
				if (e.keyCode == SWT.ARROW_UP) {
					userGame.moveUp();
				}
				
				mazeFrame.redraw();
				if(userGame.beenWon()) {
					System.out.println("You win!");
				}
			}
		});

		shell.setSize(608, 497);
		shell.setLayout(new FormLayout());

		setColors(display);
		init();
		gameFrame.setVisible(false);

		shell.open();
		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch()) {
				// if there are currently no other OS event to process
				// sleep until the next OS event is available
				display.sleep();
			}
		}

		// disposes all associated windows and their components
		display.dispose();
	}

	private static void setColors(Display d) {
		red = d.getSystemColor(SWT.COLOR_RED);
		green = d.getSystemColor(SWT.COLOR_GREEN);
		blue = d.getSystemColor(SWT.COLOR_BLUE);
		black = d.getSystemColor(SWT.COLOR_BLACK);
	}

	private static void init() {
		mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);

		gameMenu = new MenuItem(mainMenu, SWT.CASCADE);
		gameMenu.setText("&Game");

		Menu menu_1 = new Menu(gameMenu);
		gameMenu.setMenu(menu_1);

		// Start normal game
		startGameMenuItem = new MenuItem(menu_1, SWT.NONE);
		startGameMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				userGame = new Game(5, 5);
				gameFrame.setVisible(true);
			}
		});
		startGameMenuItem.setText("&Start Game");

		// Endless Mode
		endlessModeMenuItem = new MenuItem(menu_1, SWT.NONE);
		endlessModeMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO
				System.out.println("Endless Mode started");
			}
		});
		endlessModeMenuItem.setText("&Endless Mode");

		exitMenuItem = new MenuItem(menu_1, SWT.NONE);
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		exitMenuItem.setText("E&xit");

		helpMenu = new MenuItem(mainMenu, SWT.CASCADE);
		helpMenu.setText("&Help");

		Menu menu_2 = new Menu(helpMenu);
		helpMenu.setMenu(menu_2);

		howToPlayMenuItem = new MenuItem(menu_2, SWT.NONE);
		howToPlayMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO
				System.out.println("How to play");
			}
		});
		howToPlayMenuItem.setText("&How To Play");

		aboutMenuItem = new MenuItem(menu_2, SWT.NONE);
		aboutMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		customQuestionsMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO
				System.out.println("Custom Questions");
			}
		});
		customQuestionsMenuItem.setText("&Custom Questions");

		leaderBoardMenuItem = new MenuItem(menu_3, SWT.NONE);
		leaderBoardMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		fd_questionFrame.left = new FormAttachment(100, -130);
		fd_questionFrame.right = new FormAttachment(100, -10);
		fd_questionFrame.top = new FormAttachment(0, 10);
		fd_questionFrame.bottom = new FormAttachment(0, 133);
		questionFrame.setLayoutData(fd_questionFrame);

		questionLbl = new Label(questionFrame, SWT.NONE);
		questionLbl.setSize(119, 30);
		questionLbl.setText("Question:\r\nThis statement is false.");

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
		fd_answerFrame.left = new FormAttachment(mazeFrame, 10);
		fd_answerFrame.right = new FormAttachment(100, -10);
		fd_answerFrame.top = new FormAttachment(100, -167);
		fd_answerFrame.bottom = new FormAttachment(100, -10);
		answerFrame.setLayoutData(fd_answerFrame);

		Button btnTrue = new Button(answerFrame, SWT.RADIO);
		btnTrue.setEnabled(false);
		btnTrue.setBounds(10, 26, 90, 16);
		btnTrue.setText("True");

		Button btnFalse = new Button(answerFrame, SWT.RADIO);
		btnFalse.setEnabled(false);
		btnFalse.setText("False");
		btnFalse.setBounds(10, 48, 90, 16);

		lblOptions = new Label(answerFrame, SWT.NONE);
		lblOptions.setBounds(10, 10, 55, 15);
		lblOptions.setText("Options");
	}
}
