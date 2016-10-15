package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Patrick on 10/15/2016.
 */
@Entity
public class User extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @Constraints.Required
    public String password;

    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    public List<Task> tasks;

    @ManyToMany
    @JsonBackReference
    public List<Title> titles;

    public static Finder<Long, User> find = new Finder<Long,User>(User.class);
}
