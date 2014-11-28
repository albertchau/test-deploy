package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import play.Logger;
import play.db.DB;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SqlMapper;
import views.html.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application extends Controller {


    public static Result index() throws SQLException {
//        Logger.info("testing testing");
        return ok(index.render("Your new application is READY."));
    }

    public static Result helloWorld() throws SQLException {
        String rtn = "";
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Students LIMIT 25");
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

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getRestaurants() throws SQLException {
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Restaurant LIMIT 10");
        ArrayNode convert = null;
        try {
            convert = SqlMapper.convert(resultSet);
        } finally {
            statement.close();
        }
        return ok(convert);
    }

    public static Result getRestaurant(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result createUser() {
        return play.mvc.Results.TODO;
    }
}
