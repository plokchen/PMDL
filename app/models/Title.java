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
public class Title extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy="title")
    @JsonManagedReference
    public List<Task> tasks;

    @ManyToMany(mappedBy="titles")
    @JsonBackReference
    public List<User> users;

    public static Finder<Long, Title> find = new Finder<Long,Title>(Title.class);
}
