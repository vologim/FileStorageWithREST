package com.mikhail_golovackii.filestoragewithrest.repository;

import com.mikhail_golovackii.filestoragewithrest.model.UserFile;

public interface UserFileRepository extends BaseRepository<UserFile, Long>{

    public UserFile getUserFileByFileName(String name);

}
