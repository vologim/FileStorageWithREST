
package com.mikhail_golovackii.filestoragewithrest.service;

import com.mikhail_golovackii.filestoragewithrest.model.UserFile;

public interface UserFileService extends BaseService<UserFile, Long>{

    public UserFile getUserFileByFileName(String name);
    
}
