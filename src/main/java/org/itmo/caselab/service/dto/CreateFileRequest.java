package org.itmo.caselab.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateFileRequest {
    private String title;
    private String description;
    private String fileBase64;
    private Date creationDate;
}
