package com.mikhail_golovackii.filestoragewithrest.service.impl;

import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.repository.UserFileRepository;
import com.mikhail_golovackii.filestoragewithrest.repository.impl.UserFileRepositoryImpl;
import com.mikhail_golovackii.filestoragewithrest.service.UserFileService;
import java.io.File;
import java.util.List;

public class UserFileServiceImpl implements UserFileService {

    private UserFileRepository repository;

    public UserFileServiceImpl() {
        this.repository = new UserFileRepositoryImpl();
    }

    public UserFileServiceImpl(UserFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveElement(UserFile elem) {
        repository.save(elem);
    }

    @Override
    public void updateElement(UserFile elem) {
        repository.update(elem);
    }

    @Override
    public UserFile getElementById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<UserFile> getAllElements() {
        return repository.getAll();
    }

    @Override
    public void deleteElement(UserFile elem) {
        deleteFileFromStorage(elem);
        repository.delete(elem);
    }

    @Override
    public UserFile getUserFileByFileName(String name) {
        return repository.getUserFileByFileName(name);
    }

    private void deleteFileFromStorage(UserFile userFile) {
        String filePath = userFile.getFilePath() + userFile.getFileName();
        File file = new File(filePath);
        file.delete();
    }

}
