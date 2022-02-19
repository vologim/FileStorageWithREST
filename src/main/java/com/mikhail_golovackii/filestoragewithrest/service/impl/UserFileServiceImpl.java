package com.mikhail_golovackii.filestoragewithrest.service.impl;

import com.mikhail_golovackii.filestoragewithrest.dto.UserFileDTO;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.repository.UserFileRepository;
import com.mikhail_golovackii.filestoragewithrest.repository.impl.UserFileRepositoryImpl;
import com.mikhail_golovackii.filestoragewithrest.service.UserFileService;
import com.mikhail_golovackii.filestoragewithrest.utils.MappingUtils;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class UserFileServiceImpl implements UserFileService {

    private UserFileRepository repository;
    private final MappingUtils mappingUtils = new MappingUtils();

    public UserFileServiceImpl() {
        this.repository = new UserFileRepositoryImpl();
    }

    public UserFileServiceImpl(UserFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveElement(UserFileDTO elem) {
        repository.save(mappingUtils.mapToUserFile(elem));
    }

    @Override
    public void updateElement(UserFileDTO elem) {
        repository.update(mappingUtils.mapToUserFile(elem));
    }

    @Override
    public UserFileDTO getElementById(Long id) {
        return mappingUtils.mapToUserFileDto(repository.getById(id));
    }

    @Override
    public List<UserFileDTO> getAllElements() {
        return repository.getAll().stream().map(mappingUtils::mapToUserFileDto).collect(Collectors.toList());
    }

    @Override
    public void deleteElement(UserFileDTO elem) {
        deleteFileFromStorage(mappingUtils.mapToUserFile(elem));
        repository.delete(mappingUtils.mapToUserFile(elem));
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
