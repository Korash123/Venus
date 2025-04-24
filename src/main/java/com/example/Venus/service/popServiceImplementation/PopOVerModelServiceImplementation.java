package com.example.Venus.service.popServiceImplementation;

import com.example.Venus.dto.request.PopOVerModelRequestDto;
import com.example.Venus.dto.response.PopOverModelResponseDto;
import com.example.Venus.entities.PopOverModel;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.PopOverModelRepo;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.PopOverModelService;
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

@Service
@RequiredArgsConstructor
public class PopOVerModelServiceImplementation implements PopOverModelService {
    private final PopOverModelRepo repo;
    private final UsersRepo usersRepo;
    private final ImageUtil imageUtil;
    private final SymmetricEncryptionUtil encryptionUtil;
    private static final String ENCRYPTION_KEY = "QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=";
    private final PopOverModelRepo popOverModelRepo;

    @Value("${encryption.key}")
    private String AES_KEY;
    @Value("${reset-link.expiration-time}")
    private long resetLinkExpiration;


    private final LoggedInUserUtil loggedInUserUtil;

    @Override
    public void create(PopOVerModelRequestDto requestDto) throws IOException {
        Long userId = loggedInUserUtil.getLoggedInUserId();
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String popOverSuffix = "/popover/userId_" + userId;
        PopOverModel popOverModel = new PopOverModel();
        popOverModel.setUserId(userId);

        if (requestDto.getFile() != null) {
            String popOverFileName = userId + "/popoverFile";
            Path popOverFilePath = Paths.get(popOverSuffix, popOverFileName);
            String savedPopOverFilePath = imageUtil.saveImage(requestDto.getFile(), popOverFilePath.toString());

            popOverModel.setImageUrl(savedPopOverFilePath);
        } else {
            popOverModel.setImageUrl(null);
        }

        repo.save(popOverModel);
    }

    @Override
    public List<PopOverModelResponseDto> showPopOverModel() throws Exception {
        List<PopOverModel> popOverModels = repo.findAll(); // optionally filter only active ones

        List<PopOverModelResponseDto> responseDtos = new ArrayList<>();

        for (PopOverModel popOver : popOverModels) {
            PopOverModelResponseDto dto = new PopOverModelResponseDto();

            if (popOver.getImageUrl() != null) {
                String encryptedUrl = encryptionUtil.encrypt(
                        popOver.getImageUrl(),
                        ENCRYPTION_KEY,
                        SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
                );
                dto.setPopOverImage(imageUtil.getImageUri(popOver.getImageUrl(), encryptedUrl));
            } else {
                dto.setPopOverImage(null);
            }

            responseDtos.add(dto);
        }

        return responseDtos;
    }

    @Override
    public void deletePopOverImage(Long id) {
        PopOverModel popOverModel= popOverModelRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("PopOver not found."));
        popOverModel.setIsDeleted(true);
        repo.save(popOverModel);
    }
}
