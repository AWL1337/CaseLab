package org.itmo.caselab.service.dto.mapper;

import org.itmo.caselab.core.pojo.File;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toFile(CreateFileRequest createFileRequest) {
        return File.builder()
                .title(createFileRequest.getTitle())
                .description(createFileRequest.getDescription())
                .fileBase64(createFileRequest.getFileBase64())
                .creationDate(createFileRequest.getCreationDate())
                .build();
    }

    public ShowFileResponse toShowFileResponse(File file) {
        return ShowFileResponse.builder()
                .id(file.getId())
                .title(file.getTitle())
                .description(file.getDescription())
                .fileBase64(file.getFileBase64())
                .creationDate(file.getCreationDate())
                .build();
    }
}
