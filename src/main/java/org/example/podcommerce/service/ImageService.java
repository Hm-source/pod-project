package org.example.podcommerce.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.repository.image.ImageRepository;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImageService {

    ImageRepository imageRepository;

    public List<Image> uploadAndSaveImages(List<MultipartFile> files, User user) {
        return files.stream()
            .map(file -> uploadAndSaveImage(file, user))
            .collect(Collectors.toList());
    }

    private Image uploadAndSaveImage(MultipartFile file, User user) {
        try {
            String uploadedUrl = uploadFileToStorage(file);
            Image image = Image.create(user, uploadedUrl, file.getOriginalFilename());
            return imageRepository.save(image);

        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다: " + file.getOriginalFilename(), e);
        }
    }

    private String uploadFileToStorage(MultipartFile file) {
        // TODO: 실제 파일 업로드 구현
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        return "https://storage.com/images/" + fileName;
    }
}
