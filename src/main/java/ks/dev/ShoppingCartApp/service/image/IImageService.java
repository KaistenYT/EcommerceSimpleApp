package ks.dev.ShoppingCartApp.service.image;

import ks.dev.ShoppingCartApp.dto.ImageDto;
import ks.dev.ShoppingCartApp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
   List <ImageDto>saveImages (List<MultipartFile> files , Long productId);
    void updateImage(MultipartFile file , long imgageId);

}
