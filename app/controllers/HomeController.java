package controllers;

import models.Task;
import models.Title;
import models.User;
import play.libs.Json;
import play.mvc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public Result index() {
        return ok("Your new application is ready.");
    }

    public Result indexUsers() {
        return ok(Json.toJson(User.find.all()));
    }

    public Result indexTasks() {
        return ok(Json.toJson(Task.find.all()));
    }
}
