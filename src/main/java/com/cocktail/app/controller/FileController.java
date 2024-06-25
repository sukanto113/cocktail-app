package com.cocktail.app.controller;

import com.cocktail.app.entity.UserInfo;
import com.cocktail.app.model.FileUploadResponse;
import com.cocktail.app.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    private final UserRepository userRepository;

    private FileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @GetMapping("/profile-picture/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {
        String fileDir = Paths.get(System.getProperty("user.dir")).getParent().toString() + "/file/profile-picture";
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

    @PostMapping("/profile-picture")
    ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile file, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) throws IOException {
        Long userId = Long.parseLong(jwt.getSubject());
        Optional<UserInfo> userInfoOptional = userRepository.findById(userId);
        if (!userInfoOptional.isPresent()) {
            return ResponseEntity.ok(new FileUploadResponse(false, null, "User not found"));
        }

        UserInfo user = userInfoOptional.get();

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        String uploadFileName = uuidAsString + "_" + file.getOriginalFilename();
        String fileDir = Paths.get(System.getProperty("user.dir")).getParent().toString() + "/file";
        String uploadDir = fileDir + "/profile-picture";
        Path uploadDirPath = Paths.get(uploadDir);
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }
        Path uploadFilePath = Paths.get(uploadDir + "/" + uploadFileName);
        Files.copy(file.getInputStream(), uploadFilePath, StandardCopyOption.REPLACE_EXISTING);
        String filePath = "file/profile-picture/" + uploadFileName;
        user.setPicture(filePath);
        userRepository.save(user);
        return ResponseEntity.ok(new FileUploadResponse(true, filePath, null));
    }
}
