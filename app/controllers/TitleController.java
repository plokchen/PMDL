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

    public Result createTitle(String titleString) {
        Title title = new Title();
        title.name = titleString;
        title.save();
        Task from = new Task();
        from.title = title;
        from.description = titleString + " start";
        from.save();
        Task to = new Task();
        to.description = titleString + " end";
        to.title = title;
        to.save();
        from.children.add(to);
        from.save();
        to.parents.add(from);
        to.save();
        title.tasks.add(from);
        title.tasks.add(to);
        title.save();
        return ok(Json.toJson("Created New Article."));
    }

    public Result getTitle(long titleId) {
        HashMap titleMap = new HashMap();
        Title title = Title.find.byId(titleId);
        if (title == null) {
            return badRequest(Json.toJson("Title doesn't exist"));
        }
        titleMap.put("id", title.id);
        titleMap.put("name", title.name);
        List tasksList = new ArrayList();
        for (Task task:title.tasks) {
            HashMap taskHash = new HashMap();
            taskHash.put("task_id", task.id);
            taskHash.put("task_description", task.description);
            List parentsList = new ArrayList();
            for(Task parentTask:task.parents){
                parentsList.add(parentTask.id);
            }
            taskHash.put("task_parents", parentsList);
            List childrenList = new ArrayList();
            for(Task childTask:task.children){
                childrenList.add(childTask.id);
            }
            taskHash.put("task_children", childrenList);
            tasksList.add(taskHash);
        }
        titleMap.put("tasks",tasksList);
        return ok(Json.toJson(titleMap));
    }

    public Result updateTitle(long titleId, String titleString) {
        Title title = Title.find.byId(titleId);
        if (title == null) {
            return badRequest(Json.toJson("Title doesn't exist"));
        }
        title.name = titleString;
        title.save();
        return ok(Json.toJson("Update title correctly."));
    }
}
