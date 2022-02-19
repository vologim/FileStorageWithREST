
package com.mikhail_golovackii.filestoragewithrest.dto;

import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
    
    private Long id;
    private String name;
    private List<UserFile> files;
    private int quantityFiles;
    
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
