package org.itmo.caselab.service;


import lombok.RequiredArgsConstructor;
import org.itmo.caselab.core.exceptions.NotFoundException;
import org.itmo.caselab.core.pojo.File;
import org.itmo.caselab.core.repositories.FileRepository;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.itmo.caselab.service.dto.mapper.FileMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public ShowFileResponse create(CreateFileRequest createFileRequest) {
        File file = fileMapper.toFile(createFileRequest);
        return fileMapper.toShowFileResponse(fileRepository.save(file));
    }

    public ShowFileResponse getFile(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File not found"));
        return fileMapper.toShowFileResponse(file);
    }

    public List<ShowFileResponse> getAllFiles(PageRequest pageRequest) {
        return fileRepository.findAll(pageRequest).getContent()
                .stream().map(fileMapper::toShowFileResponse).toList();
    }
}
