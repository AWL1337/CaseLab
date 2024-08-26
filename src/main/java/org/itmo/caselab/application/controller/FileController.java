package org.itmo.caselab.application.controller;

import lombok.RequiredArgsConstructor;
import org.itmo.caselab.service.FileService;
import org.itmo.caselab.service.dto.CreateFileRequest;
import org.itmo.caselab.service.dto.ShowFileResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<ShowFileResponse> getFile(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.getFile(id));
    }

    @PostMapping
    public ResponseEntity<Long> createFile(@RequestBody CreateFileRequest createFileRequest) {
        return ResponseEntity.ok(fileService.create(createFileRequest).getId());
    }

    @GetMapping
    public ResponseEntity<List<ShowFileResponse>> getAllFiles(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "false") Boolean asc
    ) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        if (asc) sort = Sort.by(Sort.Direction.ASC, "creationDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(fileService.getAllFiles(pageRequest));
    }
}
