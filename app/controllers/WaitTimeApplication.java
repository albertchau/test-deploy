package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import java.util.Random;

import static play.mvc.Results.ok;
/**
 * Metadata class gets the metadata for the table Created by achau1 on 11/23/14.
 */
public class WaitTimeApplication {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getWaitTime(long restaurant_id, long party_size) {
        Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(30);
        ObjectNode rtn = Json.newObject();
        rtn.put("waitTime",randomInt);
        return ok(rtn);
    }
}
