package org.itmo.caselab.service.dto.mapper;

import org.itmo.caselab.core.pojo.File;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FileMapperTest {

    private FileMapper fileMapper;

    @BeforeEach
    public void setUp() {
        fileMapper = new FileMapper();
    }

    @Test
    public void testToFile() {

        File expectedFile = File.builder()
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        CreateFileRequest createFileRequest = CreateFileRequest.builder()
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();


        File actualFile = fileMapper.toFile(createFileRequest);

        assertEquals(expectedFile, actualFile);
    }

    @Test
    public void testToShowFileResponse() {

        ShowFileResponse expectedResponse = ShowFileResponse.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        File file = File.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();


        ShowFileResponse actualResponse = fileMapper.toShowFileResponse(file);



        assertEquals(expectedResponse, actualResponse);
    }
}