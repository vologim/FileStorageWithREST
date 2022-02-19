
package com.mikhail_golovackii.filestoragewithrest.service;

import com.mikhail_golovackii.filestoragewithrest.dto.UserFileDTO;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;

public interface UserFileService extends BaseService<UserFileDTO, Long>{

    public UserFile getUserFileByFileName(String name);
    
}
