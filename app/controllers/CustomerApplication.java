package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.db.DB;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SqlMapper;

import java.sql.*;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Metadata class gets the metadata for the table Created by achau1 on 11/23/14.
 */
public class CustomerApplication {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result listRestaurants() throws SQLException {
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

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getRestaurant(long id) throws SQLException {
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Restaurant WHERE restaurant_id=" + id);
        ArrayNode convert = null;
        try {
            convert = SqlMapper.convert(resultSet);
        } finally {
            statement.close();
        }
        BodyParser bp = new BodyParser.Json();

        return ok(convert);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result doSignUp() throws SQLException {
        JsonNode json = request().body().asJson();
        String email = json.findPath("email").textValue();
        int phoneNumber = json.findPath("phone_number").asInt();
        String password = json.findPath("password").textValue();
        if(email == null) {
            return badRequest("Missing parameter [email]");
        }  else if( password == null ) {
            return badRequest("Missing parameter [password]");
        } else {
            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();
            String s = "insert into Customer (first_name, last_name, email, country, setting_id, password_hash, birthday_date, gender, facebook_id, phone_number) values ('', '', '" + email + "', '', 84, '" + password + "', null, '', null," + phoneNumber + ");";
            System.out.println(s);
            int execute = statement.executeUpdate(s, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            java.math.BigDecimal serColVar = null;
            while (rs.next()) {
                serColVar = rs.getBigDecimal(1);
            }
            rs.close();                           // Close ResultSet
            statement.close();
//            execute.next();
            return ok(String.valueOf(serColVar));
        }
    }

    public static Result updateUser() {
        return play.mvc.Results.TODO;
    }

    public static Result profile() {
        return play.mvc.Results.TODO;
    }
}
