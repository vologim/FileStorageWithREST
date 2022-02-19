
package com.mikhail_golovackii.filestoragewithrest.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "user_name")
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserFile> files;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void addFile(UserFile file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }
    
    public void deleteEvent(UserFile file) {
        if (files != null) {
            files.remove(file);
        }
    }
}
