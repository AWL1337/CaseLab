package org.itmo.caselab.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itmo.caselab.core.exceptions.NotFoundException;
import org.itmo.caselab.service.FileService;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void testGetFile() throws Exception {

        Long fileId = 1L;
        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(fileId)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        when(fileService.getFile(fileId)).thenReturn(showFileResponse);


        mockMvc.perform(get("/api/file/{id}", fileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(fileId.intValue())))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.fileBase64", is("dGVzdCBmaWxl")));
    }

    @Test
    public void testGetFileNotFound() throws Exception {

        Long fileId = 1L;

        when(fileService.getFile(fileId)).thenThrow(new NotFoundException("File not found"));


        mockMvc.perform(get("/api/file/{id}", fileId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("File not found"));
    }

    @Test
    public void testCreateFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Long fileId = 1L;
        CreateFileRequest createFileRequest = CreateFileRequest.builder()
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(fileId)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        when(fileService.create(any(CreateFileRequest.class))).thenReturn(showFileResponse);


        mockMvc.perform(post("/api/file")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(createFileRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(fileId.toString()));
    }

    @Test
    public void testGetAllFiles() throws Exception {

        ShowFileResponse showFileResponse = ShowFileResponse.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .fileBase64("dGVzdCBmaWxl")
                .creationDate(new Date(1))
                .build();

        List<ShowFileResponse> responseList = Collections.singletonList(showFileResponse);

        when(fileService.getAllFiles(any(PageRequest.class))).thenReturn(responseList);


        mockMvc.perform(get("/api/file")
                        .param("page", "0")
                        .param("size", "5")
                        .param("asc", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Title")));
    }
}
