
package com.mikhail_golovackii.filestoragewithrest.dto;

import lombok.Data;

@Data
public class UserFileDTO {

    private Long id;
    private String fileName;
    private String filePath;
}
