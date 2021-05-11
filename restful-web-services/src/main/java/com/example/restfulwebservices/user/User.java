package com.example.restfulwebservices.user;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min=2, message = "Name should have min 2 chars")
    private String name;
    @Past(message = "Date should be older than today")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {
    }

    public User(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

//    @Override public String toString() {
//        return "User{" + "id=" + id + ", name='" + name + '\'' + ", birthDate=" + birthDate + '}';
//    }
}
