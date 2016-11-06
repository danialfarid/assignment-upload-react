package com.df.interviews.dotsub.upload;

import com.df.interviews.dotsub.upload.model.FileMetadata;
import com.df.interviews.dotsub.upload.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/upload")
    public Collection<FileMetadata> listUploadedFiles(Model model) throws IOException {
        return storageService.getAll();
    }

    @GetMapping("/upload/{id}/file")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String id) {
        FileMetadata fileInfo = storageService.get(id);
        if (fileInfo == null) {
            throw new IllegalArgumentException("File not found with the given id: " + id);
        }
        Resource file = storageService.getAsResource(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.fileName + "\"")
                .body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("metadata") String metadata) {
        try {
            FileMetadata fileMetadata = new ObjectMapper().readValue(metadata, FileMetadata.class);
            fileMetadata.fileName = file.getOriginalFilename();
            storageService.save(fileMetadata, file.getInputStream());
            return fileMetadata.id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handle400(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}
