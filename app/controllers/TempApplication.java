package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import play.db.DB;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.SqlMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static play.mvc.Results.ok;

/**
 * Metadata class gets the metadata for the table Created by achau1 on 11/23/14.
 */
public class TempApplication {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCustomerById(long id) throws SQLException {
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from wistapp.Customer WHERE id=" + id);
        ArrayNode convert = null;
        try {
            convert = SqlMapper.convert(resultSet);
        } finally {
            statement.close();
        }
        return ok(convert);
    }
}
