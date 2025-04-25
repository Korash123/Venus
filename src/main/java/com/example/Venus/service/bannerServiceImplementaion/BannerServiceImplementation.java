package com.example.Venus.service.bannerServiceImplementaion;


import com.example.Venus.dto.request.BannerRequestDto;
import com.example.Venus.dto.response.BannersResponseDto;
import com.example.Venus.entities.Banners;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.BannerRepo;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.BannerService;
import com.example.Venus.utils.ImageUtil;
import com.example.Venus.utils.LoggedInUserUtil;
import com.example.Venus.utils.SymmetricEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImplementation implements BannerService {
    private final BannerRepo bannerRepo;
    private final UsersRepo usersRepo;
    private final ImageUtil imageUtil;
    private final LoggedInUserUtil loggedInUserUtil;
    private final SymmetricEncryptionUtil encryptionUtil;
    private static final String ENCRYPTION_KEY = "QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=";
    @Value("${file.upload-dir}")
    private String uploadDir;



    @Override
    public void createBanner(BannerRequestDto requestDto) throws IOException {
        Long userId = loggedInUserUtil.getLoggedInUserId();
        Users user = usersRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User.not.Found" + userId));
        String bannerSuffix = "/banner";

        Optional<Banners> exitingBanner = bannerRepo.findByUserIdAndTitle(userId,requestDto.getTitle());

        Banners banner = exitingBanner.orElse(new Banners());
        banner.setUserId(userId);
        banner.setTitle(requestDto.getTitle());
        banner.setDescription(requestDto.getDescription());

        if (requestDto.getImage() != null) {
            String fileName = userId + "/banner/";
            Path filePath = Paths.get(bannerSuffix + fileName);
            String saveBannerImage = imageUtil.saveImage(requestDto.getImage(), filePath.toString());

            banner.setImageUrl(saveBannerImage);
        }else {
            banner.setImageUrl(null);
        }

        bannerRepo.save(banner);

    }

    @Override
    public List<BannersResponseDto> getAllBanner() throws Exception {

        List<Banners> banners = bannerRepo.findAll();

        List<BannersResponseDto> responseDtos = new ArrayList<>();

        for (Banners banner : banners) {
            if (Boolean.TRUE.equals(banner.getIsDeleted())){
                continue;
            }

            BannersResponseDto responseDto = new BannersResponseDto();
            responseDto.setId(banner.getBannerId());
            responseDto.setTitle(banner.getTitle());
            responseDto.setDescription(banner.getDescription());

            if (banner.getImageUrl() != null) {
                String encrytedUrl = encryptionUtil.encrypt(banner.getImageUrl(),ENCRYPTION_KEY,
                        SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC);
                responseDto.setImage(imageUtil.getImageUri(banner.getImageUrl(), encrytedUrl));

            }else {
                responseDto.setImage(null);
            }
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }

    @Override
    public void deleteBanner(Long id) {
        Banners banners = bannerRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Banner not Found" + id));
        banners.setIsDeleted(true);
        bannerRepo.save(banners);
    }
}
