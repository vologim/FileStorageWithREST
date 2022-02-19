
package com.mikhail_golovackii.filestoragewithrest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "file_user")
@Data
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "file_path")
    private String filePath;

    public UserFile() {
    }

    public UserFile(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

}
