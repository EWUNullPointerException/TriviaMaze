package com.npe.triviamaze.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database
{
    private static final String FILE_PATH = "resources/databases/";
    private Statement _statement;

    // private Connection _conn;

    public Database()
    {
        _statement = null;
        // _conn = null;
    }

    /**
     * Gets 'count', or as many as it can, number of RANDOM multiple choice questions from the
     * category specified
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
     * Gets 'count', or as many as it can, number of RANDOM true false questions from the category
     * specified
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
     * Gets 'count', or as many as it can, number of RANDOM short answer questions from the category
     * specified
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

    /**
     * Grabs ALL questions from the specified category
     * 
     * @param cat
     *            The category to grab all the questions from
     * @return A 2d array of strings. The first dim is the number of questions. Second dim are the
     *         questions themselves. The questions returned are in the format [question,
     *         (option1,...,option4), answer].
     */
    public String[][] getAllCatQuestions(String cat)
    {
        ResultSet rs = null;
        ArrayList<String[]> ret = new ArrayList<String[]>();

        String[] queries = new String[] {
                "SELECT `question`,`option1`,`option2`,`option3`,`option4`,`answer` FROM `multiplechoice`",
                "SELECT `question`,`option1`,`option2`,`answer` FROM `truefalse`",
                "SELECT `question`,`answer` FROM `shortanswer`"};

        try
        {
            Connection c = openConnection(cat);
            _statement = c.createStatement();

            for(int i = 0; i < queries.length; i++)
            {
                rs = _statement.executeQuery(queries[i]);

                if(rs != null)
                    ret.addAll(parseResult(rs));
            }

            closeConnection(c);
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

        return ret.toArray(new String[0][0]);
    }
    
    /**
     * Returns ALL questions in the ENTIRE DATABASE. *** WARNING *** FOR EACH CATEGORY SPECIFIED, A DB CONNECTION MUST BE MADE. COULD BECOME VERY COSTLY!
     * @param cats
     * An array of categories to grabbed from
     * @return A 2d array of strings. The first dim is the number of questions. Second dim are the
     *         questions themselves. The questions returned are in the format [question,
     *         (option1,...,option4), answer].
     * 
     */
    public String[][] getAllQuestions(String[] cats)
    {
        ArrayList<String[]> ret = new ArrayList<String[]>();
        String[][] grabbed = null;
        
        for(int i =0, j; i < cats.length;i++)
        {
            grabbed = getAllCatQuestions(cats[i]);
            for(j = 0; j < grabbed.length; j++)
                ret.add(grabbed[j]);
        }
        
        return ret.toArray(new String[0][0]);
    }

    private String[][] runQuery(String cat, String sql)
    {
        ResultSet rs = null;
        ArrayList<String[]> ret = null;
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

        return ret.toArray(new String[0][0]);
    }

    private ArrayList<String[]> parseResult(ResultSet rs) throws SQLException
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

        return ret;
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