package ru.lanolin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lanolin.domain.StatusMessage;
import ru.lanolin.exceptions.FileOperationException;
import ru.lanolin.service.AvatarStorageService;

import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/avatar")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ImageController {

    private final AvatarStorageService avatarStorageService;

    @GetMapping(value = "/{userKey}")
    public ResponseEntity<?> getIcon(@PathVariable String userKey) {
        try {
            InputStream icon = avatarStorageService.getIcon(userKey);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(icon));
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (FileOperationException e) {
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(StatusMessage.Status.ERROR)
                    .message("Cannot " + e.getFileOperation() + ": " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(statusMessage);
        }
    }

    @PutMapping(value = "/{userKey}")
    public ResponseEntity<StatusMessage> uploadIcon(@PathVariable String userKey,
                                                    @RequestParam("image") MultipartFile file) {
        try {
            avatarStorageService.uploadIcon(userKey, file);
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(StatusMessage.Status.OK).build();
            return ResponseEntity.ok().body(statusMessage);
        } catch (FileOperationException e) {
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(StatusMessage.Status.ERROR)
                    .message("Cannot " + e.getFileOperation() + ": " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(statusMessage);
        }
    }

    @DeleteMapping(value = "/{userKey}")
    public ResponseEntity<StatusMessage> deleteIcon(@PathVariable String userKey) {
        try {
            boolean deleted = avatarStorageService.deleteIcon(userKey);
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(StatusMessage.Status.OK)
                    .message(String.valueOf(deleted))
                    .build();
            return ResponseEntity.badRequest().body(statusMessage);
        } catch (FileOperationException e) {
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(StatusMessage.Status.ERROR)
                    .message("Cannot " + e.getFileOperation() + ": " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(statusMessage);
        }
    }

}
