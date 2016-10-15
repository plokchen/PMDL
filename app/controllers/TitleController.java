package controllers;

import models.Task;
import models.Title;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Patrick on 10/15/2016.
 */
public class TitleController  extends Controller {

    public Result indexTitles() {
        List<Title> titleList =  Title.find.all();
        List<HashMap> list = new ArrayList<>();
        for(Title title:titleList) {
            HashMap hashMap = new HashMap();
            hashMap.put("id", title.id);
            hashMap.put("name", title.name);
            list.add(hashMap);
        }
        return ok(Json.toJson(list));
    }

    public Result getTitle(long titleId) {
        return ok(Json.toJson(Title.find.byId(titleId)));
    }

    public Result createTitle(String titleString) {
        Title title = new Title();
        title.name = titleString;
        title.save();
        Task from = new Task();
        from.description = titleString + " start";
        from.save();
        Task to = new Task();
        to.description = titleString + " end";
        to.save();
        from.title = title;
        from.children.add(to);
        from.save();
        to.title = title;
        to.parents.add(to);
        to.save();
        title.tasks.add(from);
        title.tasks.add(to);
        title.save();
        return ok(Json.toJson("Created New Article."));
    }

    public Result updateTitle(String titleString) {
        Title title = new Title();
        title.name = titleString;
        title.save();
        return ok(Json.toJson("Update title correctly."));
    }
}
