package ru.lanolin.controller;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.lanolin.domain.StatusMessage;
import ru.lanolin.domain.StatusMessage.Status;
import ru.lanolin.exceptions.ResponseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
@RequestMapping("/api/ocr/tesseract")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TesseractController {

    private final Tesseract tesseract;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public StatusMessage doOCR(@RequestParam("image") MultipartFile file,
                               @RequestParam(value = "lang", defaultValue = "rus") String lang) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile(file.getName(), "." + extension);
            Files.write(tempFile, file.getBytes());
            File tempFileFile = tempFile.toFile();
            tesseract.setLanguage(lang);
            String result = tesseract.doOCR(tempFileFile);
            return StatusMessage.builder()
                    .status(Status.OK)
                    .message(result)
                    .build();
        } catch (TesseractException e) {
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(Status.ERROR)
                    .message("Failed to recognize: " + e.getMessage())
                    .error(e.getClass().getName())
                    .build();
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, statusMessage);
        } catch (IOException e) {
            StatusMessage statusMessage = StatusMessage.builder()
                    .status(Status.ERROR)
                    .message("Failed to create temp data: " + e.getMessage())
                    .error(e.getClass().getName())
                    .build();
            throw new ResponseException(HttpStatus.INSUFFICIENT_STORAGE, statusMessage);
        } finally {
            try {
                if (Objects.nonNull(tempFile)) Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
            }
        }
    }

}
