
package com.mikhail_golovackii.filestoragewithrest.service.impl;

import com.mikhail_golovackii.filestoragewithrest.dto.UserDTO;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.repository.UserRepository;
import com.mikhail_golovackii.filestoragewithrest.repository.impl.UserRepositoryImpl;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import com.mikhail_golovackii.filestoragewithrest.utils.MappingUtils;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private final MappingUtils mappingUtils = new MappingUtils();

    public UserServiceImpl() {
        this.repository = new UserRepositoryImpl();
    }

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void saveElement(UserDTO elem) {
        repository.save(mappingUtils.mapToUser(elem));
    }

    @Override
    public void updateElement(UserDTO elem) {
        repository.update(mappingUtils.mapToUser(elem));
    }

    @Override
    public UserDTO getElementById(Long id) {
        return mappingUtils.mapToUserDto(repository.getById(id));
    }

    @Override
    public List<UserDTO> getAllElements() {
        return repository.getAll().stream().map(mappingUtils::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteElement(UserDTO elem) {
        deleteUserFiles(mappingUtils.mapToUser(elem));
        repository.delete(mappingUtils.mapToUser(elem));
    }

    private void deleteUserFiles(User user) {
        List<UserFile> files = user.getFiles();
        for (UserFile userFile : files) {
            String filePath = userFile.getFilePath() + userFile.getFileName();
            File file = new File(filePath);
            file.delete();
        }
    }

}
