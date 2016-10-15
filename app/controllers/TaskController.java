package controllers;

import models.Task;
import models.Title;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Patrick on 10/15/2016.
 */
public class TaskController extends Controller {

    public Result createTask(long titleId, long parentId, long childId,String description) {
        Title title = Title.find.byId(titleId);
        if (title == null) {
            return badRequest("Title is needed for creating a task");
        }
        Task parent = Task.find.byId(parentId);
        Task child = Task.find.byId(childId);
        Task task = new Task();
        task.description = description;
        task.title = title;
        task.save();
        if (parent !=null) {
            task.parents.add(parent);
            parent.children.add(task);
            parent.save();
        }
        if (child !=null) {
            task.children.add(child);
            child.parents.add(task);
            child.save();
        }
        task.save();

        return ok(Json.toJson("Created Tasked Successfully"));
    }

    public Result updateTask(long taskId,String description ) {
        Task task = Task.find.byId(taskId);
        if (task != null) {
            task.description = description;
            task.save();
        } else {
            badRequest(Json.toJson("The task doesn't exist"));
        }
        return ok(Json.toJson("Updated Parent Successfully"));
    }

    public Result updateParent(long taskId,long taskOldParentId, long taskNewParentId ) {
        Task task = Task.find.byId(taskId);
        if (task != null) {
            Task taskOldParent = Task.find.byId(taskOldParentId);
            if (taskOldParent != null &&  task.parents.contains(taskOldParent)) {
                task.parents.remove(taskOldParent);
            } else {
                if (taskOldParentId > 1)
                    return badRequest("Task old you want to delete doesn't exist");
            }
            Task taskNewParent = Task.find.byId(taskNewParentId);
            if (taskNewParent != null &&!task.parents.contains(taskNewParent) ) {
                task.parents.add(taskNewParent);
            } else {
                if (taskNewParentId > 1)
                    return badRequest("Task new you want to add already there");
            }
            task.save();
        }
        return ok(Json.toJson("Updated Parent Successfully"));
    }

    public Result updateChild(long taskId,long taskOldChildId, long taskNewChildId ) {
        Task task = Task.find.byId(taskId);
        if (task != null) {
            Task taskOldChild = Task.find.byId(taskOldChildId);
            if (taskOldChild != null && task.children.contains(taskOldChild)) {
                task.children.remove(taskOldChild);
            } else {
                if (taskOldChildId > 1)
                    return badRequest("Task old you want to delete doesn't exist");
            }
            Task taskNewChild = Task.find.byId(taskNewChildId);
            if (taskNewChild != null && !task.children.contains(taskNewChild)) {
                task.children.add(taskNewChild);
            } else {
                if (taskNewChildId > 1)
                    return badRequest("Task new you want to add already there");
            }
            task.save();
        }
        return ok(Json.toJson("Updated Child Successfully"));
    }

    public Result finishTask(long userId, long taskId) {
        Task task = Task.find.byId(taskId);
        User user = User.find.byId(userId);
        if (task != null && user!=null) {
            if (task.users.contains(user)) {
                return ok(Json.toJson("Task was marked already"));
            } else {
                if (!user.tasks.contains(task)) {
                    user.tasks.add(task);
                    user.save();
                }
            }
            task.save();
            return ok(Json.toJson("Task marked as done"));
        }
        return internalServerError("Something went wrong");
    }

    public Result getTask(long userId, long taskId) {
        User user = User.find.byId(userId);
        Task task = Task.find.byId(taskId);
        HashMap result = new HashMap();
        if (user != null) {
            if (user.tasks.contains(task)) {
                result.put("done", true);
            } else {
                result.put("done", false);
            }
        }
        result.put("description", task.description);
        result.put("title", task.title.name);
        List listOfParents = new ArrayList();
        for(Task parent:task.parents) {
            HashMap hashMap = new HashMap();
            hashMap.put("id", parent.id);
            hashMap.put("description", parent.description);
            listOfParents.add(hashMap);
        }
        result.put("parent", listOfParents);

        List listOfChildren = new ArrayList();
        for(Task child:task.children) {
            HashMap hashMap = new HashMap();
            hashMap.put("id", child.id);
            hashMap.put("description", child.description);
            listOfChildren.add(hashMap);
        }
        result.put("children", listOfChildren);
        return ok(Json.toJson(result));
    }

}
