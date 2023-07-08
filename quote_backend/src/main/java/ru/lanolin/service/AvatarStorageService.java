package ru.lanolin.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.lanolin.exceptions.FileOperationException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class AvatarStorageService {

    @Value("${avatar.datapath}")
    public Path avatarPath;

    @PostConstruct
    public void postConstruct() throws IOException {
        Files.createDirectories(avatarPath);
    }

    public InputStream getIcon(String userKey) throws FileNotFoundException, FileOperationException {
        Path icons = avatarPath.resolve(userKey + ".png");
        boolean exists = Files.exists(icons);
        if (exists) {
            try {
                return Files.newInputStream(icons, StandardOpenOption.READ);
            } catch (IOException e) {
                throw new FileOperationException(FileOperationException.FileOperation.READ, e);
            }
        } else {
            throw new FileNotFoundException("File " + icons.getFileName() + "not found");
        }
    }

    public void uploadIcon(String userKey, MultipartFile file) throws FileOperationException {
        Path icons = avatarPath.resolve(userKey + ".png");
        try {
            OutputStream outputStream = Files.newOutputStream(icons, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            throw new FileOperationException(FileOperationException.FileOperation.WRITE, e);
        }
    }

    public boolean deleteIcon(String userKey) throws FileOperationException {
        Path icons = avatarPath.resolve(userKey + ".png");
        try {
            return Files.deleteIfExists(icons);
        } catch (IOException e) {
            throw new FileOperationException(FileOperationException.FileOperation.DELETE, e);
        }
    }
}
