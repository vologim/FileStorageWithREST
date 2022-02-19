
package com.mikhail_golovackii.filestoragewithrest.service.impl;

import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.repository.UserRepository;
import com.mikhail_golovackii.filestoragewithrest.repository.impl.UserRepositoryImpl;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import java.io.File;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl() {
        this.repository = new UserRepositoryImpl();
    }

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void saveElement(User elem) {
        repository.save(elem);
    }

    @Override
    public void updateElement(User elem) {
        repository.update(elem);
    }

    @Override
    public User getElementById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<User> getAllElements() {
        return repository.getAll();
    }

    @Override
    public void deleteElement(User elem) {
        deleteUserFiles(elem);
        repository.delete(elem);
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
