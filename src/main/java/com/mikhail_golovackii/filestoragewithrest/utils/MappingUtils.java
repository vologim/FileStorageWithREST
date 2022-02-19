
package com.mikhail_golovackii.filestoragewithrest.utils;

import com.mikhail_golovackii.filestoragewithrest.dto.UserDTO;
import com.mikhail_golovackii.filestoragewithrest.dto.UserFileDTO;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;

public class MappingUtils {

    public UserDTO mapToUserDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFiles(entity.getFiles());
        dto.setQuantityFiles(entity.getFiles().size());
        return dto;
    }
    
    public User mapToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setFiles(dto.getFiles());
        return user;
    }
    
    public UserFileDTO mapToUserFileDto(UserFile entity) {
        UserFileDTO dto = new UserFileDTO();
        dto.setId(entity.getId());
        dto.setFileName(entity.getFileName());
        return dto;
    }
    
    public UserFile mapToUserFile(UserFileDTO dto) {
        UserFile uf = new UserFile();
        uf.setId(dto.getId());
        uf.setFileName(dto.getFileName());
        uf.setFilePath(dto.getFilePath());
        return uf;
    }
    
}
