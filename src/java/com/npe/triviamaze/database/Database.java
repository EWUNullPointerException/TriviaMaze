package com.npe.triviamaze.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database
{
    private static final String FILE_PATH = "src/java/com/npe/triviamaze/database/";
    private Statement _statement;
    private Connection _conn;

    public Database()
    {
        _statement = null;
        _conn = null;
    }

    /**
     * Gets 'count', or as many as it has, number of RANDOM multiple choice questions from the category specified
     * 
     * @param cat
     *            The category to grab the questions from
     * @param count
     *            The number of questions to grab
     * @return A 2d array of Strings. First dim is the amount of questions Second dim is the
     *         question itself in the format of [question, option1,...,option4, answer]
     */
    public String[][] getMultiChoice(String cat, int count)
    {
        String sql = "SELECT `question`,`option1`,`option2`,`option3`,`option4`,`answer` FROM `multiplechoice` ORDER BY RANDOM() LIMIT "
                + count;

        return runQuery(cat, sql);
    }

    /**
     * Gets 'count', or as many as it has, number of RANDOM true false questions from the category specified
     * 
     * @param cat
     *            The category to grab the questions from
     * @param count
     *            The number of questions to grab
     * @return A 2d array of Strings. First dim is the amount of questions Second dim is the
     *         question itself in the format of [question, option1, option2, answer]
     */
    public String[][] getTrueFalse(String cat, int count)
    {
        String sql = "SELECT `question`,`option1`,`option2`,`answer` FROM `truefalse` ORDER BY RANDOM() LIMIT "
                + count;

        return runQuery(cat, sql);
    }

    /**
     * Gets 'count', or as many as it has, number of RANDOM short answer questions from the category specified
     * 
     * @param cat
     *            The category to grab the questions from
     * @param count
     *            The number of questions to grab
     * @return A 2d array of Strings. First dim is the amount of questions Second dim is the
     *         question itself in the format of [question, answer]
     */
    public String[][] getShortAnswer(String cat, int count)
    {
        String sql = "SELCT `question`,`answer` FROM `shortanswer` FROM `shortanswer` ORDER BY RANDOM() LIMIT "
                + count;

        return runQuery(cat, sql);
    }

    private String[][] runQuery(String cat, String sql)
    {
        ResultSet rs = null;
        String[][] ret = null;
        try
        {
            Connection c = openConnection(cat);
            _statement = c.createStatement();

            rs = _statement.executeQuery(sql);

            if(rs != null)
            {
                ret = parseResult(rs);
            }

            closeConnection(c);
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

        return ret;
    }

    private String[][] parseResult(ResultSet rs) throws SQLException
    {
        ArrayList<String[]> ret = new ArrayList<String[]>();
        ResultSetMetaData md = rs.getMetaData();
        int count = md.getColumnCount();

        String[] row = null;

        while(rs.next())
        {
            row = new String[count];
            for(int i = 0; i < count; i++)
                row[i] = rs.getString(i + 1);

            ret.add(row);
        }

        return ret.toArray(new String[0][0]);
    }

    private Connection openConnection(String cat) throws ClassNotFoundException, SQLException
    {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH + cat + ".db");
        return c;
    }

    private void closeConnection(Connection c) throws SQLException
    {
        c.close();
    }
}