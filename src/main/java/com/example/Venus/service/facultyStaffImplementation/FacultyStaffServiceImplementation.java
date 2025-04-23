package com.example.Venus.service.facultyStaffImplementation;

import com.example.Venus.dto.request.FacultyStaffRequestDto;
import com.example.Venus.dto.response.FacultyStaffResponseDto;
import com.example.Venus.entities.FacultyStaff;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.FacultyStaffRepo;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.FacultyStaffService;
import com.example.Venus.utils.ImageUtil;
import com.example.Venus.utils.LoggedInUserUtil;
import com.example.Venus.utils.SymmetricEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FacultyStaffServiceImplementation implements FacultyStaffService {

    private final FacultyStaffRepo facultyStaffRepo;
    private final UsersRepo usersRepo;
    private final LoggedInUserUtil loggedInUserUtil;
    private final ImageUtil imageUtil;
    private final SymmetricEncryptionUtil encryptionUtil;
    private static final String ENCRYPTION_KEY = "QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=";
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public void createAndUpdateFacultyStaff(FacultyStaffRequestDto facultyStaffRequestDto) throws IOException {
        Long loggedInUserId = loggedInUserUtil.getLoggedInUserId();
        Users loggedInUser = usersRepo.findById(loggedInUserId)
                .orElseThrow(() -> new UsernameNotFoundException("Logged-in user not found."));

        String documentSuffix = "/facultyStaff/";

        Optional<FacultyStaff> existingFacultyStaff = facultyStaffRepo.findByUserIdAndTitleAndDescription(
                loggedInUserId,facultyStaffRequestDto.getTitle(),facultyStaffRequestDto.getDescription());
        FacultyStaff facultyStaff = existingFacultyStaff.orElse(new FacultyStaff());

        facultyStaff.setUserId(loggedInUserId);
        facultyStaff.setTitle(facultyStaffRequestDto.getTitle());
        facultyStaff.setDescription(facultyStaffRequestDto.getDescription());

        if (facultyStaffRequestDto.getImage() != null) {
            String fileName = loggedInUserId + "/faculty/";
            Path filePath = Paths.get(documentSuffix, fileName);
            String savedFilePath = imageUtil.saveImage(facultyStaffRequestDto.getImage(), filePath.toString());

            facultyStaff.setImageUrl(savedFilePath);
        }

        facultyStaffRepo.save(facultyStaff);
    }

    @Override
    public List<FacultyStaffResponseDto> getAllFacultyStaff() throws Exception {

        List<FacultyStaff> facultyStaffs = facultyStaffRepo.findAll();

        List<FacultyStaffResponseDto> facultyStaffResponseDtos = new ArrayList<>();

        for (FacultyStaff facultyStaff : facultyStaffs) {
            if (Boolean.TRUE.equals(facultyStaff.getIsDeleted())){
                continue;
            }

            FacultyStaffResponseDto responseDto = new FacultyStaffResponseDto();
            responseDto.setId(facultyStaff.getId());
            responseDto.setTitle(facultyStaff.getTitle());
            responseDto.setDescription(facultyStaff.getDescription());

            if (facultyStaff.getImageUrl() != null) {
                String encryptedUrl = encryptionUtil.encrypt(
                        facultyStaff.getImageUrl(),
                        ENCRYPTION_KEY,
                        SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
                );
                responseDto.setImageUrl(imageUtil.getImageUri(facultyStaff.getImageUrl(), encryptedUrl));
            } else {
                responseDto.setImageUrl(null);
            }

            facultyStaffResponseDtos.add(responseDto);
        }

        return facultyStaffResponseDtos;

    }

    @Override
    public FacultyStaffResponseDto getFacultyStaffById() throws Exception {
        Long loggedInUserId = loggedInUserUtil.getLoggedInUserId();

        FacultyStaff facultyStaff = facultyStaffRepo.findByUserId(loggedInUserId).orElseThrow(()
                -> new ResourceNotFoundException("Faculty Staff Not Found")
        );

        if (Boolean.TRUE.equals(facultyStaff.getIsDeleted())) {
            throw new ResourceNotFoundException("Faculty Staff Not Found");
        }

        FacultyStaffResponseDto responseDto = new FacultyStaffResponseDto();
        responseDto.setTitle(facultyStaff.getTitle());
        responseDto.setDescription(facultyStaff.getDescription());

        if (facultyStaff.getImageUrl() != null) {
            String encryptedUrl = encryptionUtil.encrypt(
                    facultyStaff.getImageUrl(),
                    ENCRYPTION_KEY,
                    SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
            );
            String imageUri = imageUtil.getImageUri(facultyStaff.getImageUrl(), encryptedUrl);
            responseDto.setImageUrl(imageUri);
        } else {
            responseDto.setImageUrl(null);
        }

        return responseDto;
    }

    @Override
    public void deleteFacultyStaffById(Long id) {
        FacultyStaff facultyStaff = facultyStaffRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Faculty Staff Not Found"));
        facultyStaff.setIsDeleted(true);
        facultyStaffRepo.save(facultyStaff);

    }


}

