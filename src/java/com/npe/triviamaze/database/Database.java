package com.npe.triviamaze.database;

import java.sql.*;

import com.npe.triviamaze.game.triviaitem.*;

public class Database
{
    private Statement _statement;
    private Connection _conn;

    public Database()
    {
        _statement = null;
        _conn = null;
    }

    /**Gets 'count', or as many as it has, number of RANDOM questions from the category specified
     * @param cat
     * The category to grab the questions from
     * @param count
     * The number of questions to grab
     * @return
     * A 2d array of Strings.
     * First dim is the amount of questions
     * Second dim is the question itself in the format of [question, option1,...,option4, answer]
     */
    public String[][] getMultiChoice(String cat, int count)
    {
        try
        {
            Connection c = openConnection(cat);
            _statement = c.createStatement();
            String sql = "SELECT * FROM table ORDER BY RANDOM() LIMIT " + count;
            _statement.executeQuery(sql);
            
            closeConnection(c);
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        
        String[][] ret = new String[count][];

        return ret;
    }

    private Connection openConnection(String cat) throws ClassNotFoundException, SQLException
    {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + cat + ".db");
        return c;
    }

    private void closeConnection(Connection c) throws SQLException
    {
        c.close();
    }
}