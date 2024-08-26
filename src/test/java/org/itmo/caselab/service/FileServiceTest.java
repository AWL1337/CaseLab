package org.itmo.caselab.service;

import org.itmo.caselab.core.exceptions.NotFoundException;
import org.itmo.caselab.core.pojo.File;
import org.itmo.caselab.core.repositories.FileRepository;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.itmo.caselab.service.dto.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileService fileService;

    @Test
    public void testCreate() {

        String title = "Test Title";
        String description = "Test Description";
        String fileBase64 = "dGVzdCBmaWxl";
        Date creationDate = new Date();

        CreateFileRequest createFileRequest = CreateFileRequest.builder()
                .title(title)
                .description(description)
                .fileBase64(fileBase64)
                .creationDate(creationDate)
                .build();

        File file = File.builder()
                .title(title)
                .description(description)
                .fileBase64(fileBase64)
                .creationDate(creationDate)
                .build();

        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(1L)
                .title(title)
                .description(description)
                .fileBase64(fileBase64)
                .creationDate(creationDate)
                .build();

        when(fileMapper.toFile(any(CreateFileRequest.class))).thenReturn(file);
        when(fileRepository.save(any(File.class))).thenReturn(file);
        when(fileMapper.toShowFileResponse(any(File.class))).thenReturn(showFileResponse);


        ShowFileResponse response = fileService.create(createFileRequest);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(title, response.getTitle());
        assertEquals(description, response.getDescription());

        verify(fileMapper).toFile(createFileRequest);
        verify(fileRepository).save(file);
        verify(fileMapper).toShowFileResponse(file);
    }

    @Test
    public void testGetFile() {

        Long fileId = 1L;
        File file = File.builder()
                .id(fileId)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date())
                .build();

        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(fileId)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date())
                .build();

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
        when(fileMapper.toShowFileResponse(any(File.class))).thenReturn(showFileResponse);


        ShowFileResponse response = fileService.getFile(fileId);


        assertNotNull(response);
        assertEquals(fileId, response.getId());
        assertEquals("Test Title", response.getTitle());

        verify(fileRepository).findById(fileId);
        verify(fileMapper).toShowFileResponse(file);
    }

    @Test
    public void testGetFileNotFound() {

        Long fileId = 1L;

        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> fileService.getFile(fileId));

        verify(fileRepository).findById(fileId);
        verify(fileMapper, never()).toShowFileResponse(any(File.class));
    }

    @Test
    public void testGetAllFiles() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        File file = File.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date())
                .build();

        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date())
                .build();

        Page<File> filePage = new PageImpl<>(Collections.singletonList(file));

        when(fileRepository.findAll(pageRequest)).thenReturn(filePage);
        when(fileMapper.toShowFileResponse(any(File.class))).thenReturn(showFileResponse);


        List<ShowFileResponse> responses = fileService.getAllFiles(pageRequest);


        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());

        verify(fileRepository).findAll(pageRequest);
        verify(fileMapper).toShowFileResponse(file);
    }
}
