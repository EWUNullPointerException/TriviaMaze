package com.npe.triviamaze.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.npe.triviamaze.database.Database;

public class Game
{
    private final Maze maze;
    private final Player player;

    public Game()
    {
        this(1, 1);
    }

    public Game(int rows, int cols)
    {
        maze = new Maze(rows, cols);
        player = new Player(maze.getStart());
        Database db = new Database();
        String[][] blah = db.getMultiChoice("CompSci", 3);
    }

    public Player getPlayer()
    {
        return player;
    }

    public boolean isGameWon()
    {
        return player.getLocation().equals(maze.getGoal());
    }

    public boolean canMove(Direction direction)
    {
        Location loc = player.getLocation();
        return maze.canMove(loc, direction);
    }

    public boolean directionOpen(Direction direction)
    {
        return maze.directionOpen(player.getLocation(), direction);
    }

    public boolean move(Direction direction)
    {
        Location loc = player.getLocation();
        boolean moved = maze.canMove(loc, direction);

        if(moved)
        {
            player.move(direction);
        }
        return moved;
    }

    public Maze getMaze()
    {
        return maze;
    }

    public String[] getQuestion(Direction direction)
    {
        return maze.getQuestion(player.getLocation(), direction);
    }

    public void answerQuestion(String answer, Direction direction)
    {
        maze.answerQuestion(player.getLocation(), direction, answer);
    }

    public boolean isGameOver()
    {
        int[][] cost = new int[maze.rows + 2][maze.cols + 2];
        for(int row = 0; row < cost.length; row++)
            for(int col = 0; col < cost[row].length; col++)
                cost[row][col] = Integer.MAX_VALUE;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(player.getLocation(), 0));
        cost[player.getLocation().row][player.getLocation().col] = 0;
        boolean lost = true;
        Location goal = maze.getGoal();
        // dijkstra's algorithm
        while(!queue.isEmpty())
        {
            Node cur = queue.poll();
            if(cur == null)
                continue;

            if(cur.loc.equals(goal))
            {
                lost = false;
                break;
            }
            // Up
            if(cur.cost + 1 < cost[cur.loc.row - 1][cur.loc.col])
            {
                if(maze.canMove(cur.loc, Direction.Up))
                {
                    cost[cur.loc.row - 1][cur.loc.col] = cur.cost + 1;
                    Location tmp = new Location(cur.loc.row - 1, cur.loc.col);
                    queue.add(new Node(tmp, cur.cost + 1));
                }
            }
            // Down
            if(cur.cost + 1 < cost[cur.loc.row + 1][cur.loc.col])
            {
                if(maze.canMove(cur.loc, Direction.Down))
                {
                    cost[cur.loc.row + 1][cur.loc.col] = cur.cost + 1;
                    Location tmp = new Location(cur.loc.row + 1, cur.loc.col);
                    queue.add(new Node(tmp, cur.cost + 1));
                }
            }
            // Left
            if(cur.cost + 1 < cost[cur.loc.row][cur.loc.col - 1])
            {
                if(maze.canMove(cur.loc, Direction.Left))
                {
                    cost[cur.loc.row][cur.loc.col - 1] = cur.cost + 1;
                    Location tmp = new Location(cur.loc.row, cur.loc.col - 1);
                    queue.add(new Node(tmp, cur.cost + 1));
                }
            }
            // Right
            if(cur.cost + 1 < cost[cur.loc.row][cur.loc.col + 1])
            {
                if(maze.canMove(cur.loc, Direction.Right))
                {
                    cost[cur.loc.row][cur.loc.col + 1] = cur.cost + 1;
                    Location tmp = new Location(cur.loc.row, cur.loc.col + 1);
                    queue.add(new Node(tmp, cur.cost + 1));
                }
            }
        }
        return lost;
    }

    private static class Node implements Comparable<Node>
    {
        public int cost;
        public Location loc;

        public Node(Location location, int cost)
        {
            this.loc = location;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node arg0)
        {
            return this.cost - arg0.cost;
        }

    }
}
