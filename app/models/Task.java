package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 10/15/2016.
 */
@Entity
public class Task extends Model{
    @Id
    public Long id;

    @ManyToOne(optional=false)
    @JsonBackReference
    public Title title;

    @ManyToMany
    @JsonBackReference
    public List<User> users;

    @Constraints.Required
    public String description;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "task_parents", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    public List<Task> parents = new ArrayList<>();

    @ManyToMany(mappedBy = "parents")
    @JsonBackReference
    public List<Task> children = new ArrayList<>();

    public static Finder<Long, Task> find = new Finder<Long,Task>(Task.class);
}
