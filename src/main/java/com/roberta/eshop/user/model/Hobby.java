package com.roberta.eshop.user.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hobbies")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hobby_id")
    private Integer id;

    private String title;

    @ManyToMany(mappedBy = "hobbies", fetch = FetchType.EAGER)
    private List<User> users;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated;

    public Hobby() {
    }

    public Hobby(String title, List<User> users, Date created, Date updated) {
        this.title = title;
        this.users = users;
        this.created = created;
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
