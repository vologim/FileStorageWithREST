
package com.mikhail_golovackii.filestoragewithrest.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUploadFile;
    
    @Column(name = "date_last_modified")
    private LocalDateTime dateLastModifiedFile;

    public Event() {
    }

    public Event(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.dateUploadFile = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getDateLastModifiedFile() {
        return dateLastModifiedFile;
    }

    public void setDateLastModifiedFile(LocalDateTime dateLastModifiedFile) {
        this.dateLastModifiedFile = dateLastModifiedFile;
    }

    public LocalDateTime getDateUploadFile() {
        return dateUploadFile;
    }

    public void setDateUploadFile(LocalDateTime dateUploadFile) {
        this.dateUploadFile = dateUploadFile;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", dateUploadFile=" + dateUploadFile + ", dateLastModifiedFile=" + dateLastModifiedFile + '}';
    }

}
