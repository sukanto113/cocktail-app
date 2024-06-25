package com.cocktail.app.controller;

import com.cocktail.app.model.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/image/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) throws MalformedURLException {
        String fileDir = Paths.get(System.getProperty("user.dir")).getParent().toString() + "/file/image";
        Path rootLocation = Paths.get(fileDir);
        Path file = rootLocation.resolve(filename);
        System.out.println(file);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/upload/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {
        String fileDir = Paths.get(System.getProperty("user.dir")).getParent().toString() + "/file/upload";
        Path rootLocation = Paths.get(fileDir);
        Path file = rootLocation.resolve(filename);
        System.out.println(file);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"").body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        String uploadFileName = uuidAsString + "_" + file.getOriginalFilename();
        String fileDir = Paths.get(System.getProperty("user.dir")).getParent().toString() + "/file";
        String uploadDir = fileDir + "/upload";
        Path uploadDirPath = Paths.get(uploadDir);
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }
        Path uploadFilePath = Paths.get(uploadDir + "/" + uploadFileName);
        Files.copy(file.getInputStream(), uploadFilePath, StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok(new FileUploadResponse(true, "file/upload/" + uploadFileName, null));
    }
}
