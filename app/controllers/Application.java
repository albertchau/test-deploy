package controllers;

import play.*;
import play.db.DB;
import play.mvc.*;

import views.html.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application extends Controller {


    public static Result index() throws SQLException {
        Logger.info("testing testing");
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Students LIMIT 20");
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        } finally {
            statement.close();
        }
        return ok(index.render("Your new application is ready."));
    }

    public static Result helloWorld() throws SQLException {
        String rtn = "";
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Students LIMIT 20");
        try {
            while (resultSet.next()) {
                for (int i = 1; i < 6; i++) {
                    rtn += resultSet.getString(i) + "; ";
                }
                rtn += "\n";
            }
        } finally {
            statement.close();
        }
        Logger.info("hello world - testing testing");
        return ok(rtn);
    }
}
