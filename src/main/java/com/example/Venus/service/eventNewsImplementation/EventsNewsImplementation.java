package com.example.Venus.service.eventNewsImplementation;

import com.example.Venus.dto.request.EventsNewsRequestDto;
import com.example.Venus.dto.response.EventNewResponseDto;
import com.example.Venus.entities.EventNew;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.BadRequestException;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.EventNewsRepo;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.EventNewsService;
import com.example.Venus.utils.ImageUtil;
import com.example.Venus.utils.LoggedInUserUtil;
import com.example.Venus.utils.SymmetricEncryptionUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsNewsImplementation implements EventNewsService {
    private final EventNewsRepo eventNewsRepo;
    private final LoggedInUserUtil loggedInUserUtil;
    private final UsersRepo usersRepo;
    private final ImageUtil imageUtil;
    private final SymmetricEncryptionUtil encryptionUtil;
    private static final String ENCRYPTION_KEY = "QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=";
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ObjectMapper objectMapper;

//    @Override
//    public void createAndUpdateEventNews(String eventNewsDto, List<MultipartFile> attachmentFiles) throws IOException {
//
//        List<EventsNewsRequestDto> educationDataList = objectMapper.readValue(
//                eventNewsDto, new TypeReference<List<EventsNewsRequestDto>>() {}
//        );
//
//        Long userId = loggedInUserUtil.getLoggedInUserId();
//        Users user = usersRepo.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
//
//        List<EventNew> eventNewList = new ArrayList<>();
//
//        for (EventsNewsRequestDto dto : educationDataList) {
//            EventNew eventNew;
//
//            Optional<EventNew> existingRecord = eventNewsRepo.findByTitle(dto.getTitle());
//
//            if (existingRecord.isPresent()) {
//                eventNew = existingRecord.get();
//                eventNew.setDescription(dto.getDescription());
//                eventNew.setStartTime(dto.getStartTime());
//                eventNew.setEndTime(dto.getEndTime());
//            } else {
//                eventNew = new EventNew();
//                eventNew.setUserId(userId);
//                eventNew.setIsDeleted(false);
//                eventNew.setTitle(dto.getTitle());
//                eventNew.setDescription(dto.getDescription());
//                eventNew.setLocation(dto.getLocation());
//                eventNew.setPublishedAt(dto.getPublishedAt());
//                eventNew.setStartTime(dto.getStartTime());
//                eventNew.setEndTime(dto.getEndTime());
//            }
//
//            if (dto.getImageIndex() != null && dto.getImageIndex() > 0 && dto.getImageIndex() <= attachmentFiles.size()) {
//                MultipartFile file = attachmentFiles.get(dto.getImageIndex() - 1);
//                String directorySuffix = "eventNews/" + userId;
//                Path documentFilePath = Paths.get(directorySuffix);
//                String savedImagePath = imageUtil.saveImage(file, documentFilePath.toString());
//                eventNew.setImageUrl(savedImagePath);
//            } else {
//                if (eventNew.getImageUrl() != null) {
//                    imageUtil.deleteFile(eventNew.getImageUrl());
//                }
//                eventNew.setImageUrl(null);
//            }
//
//            eventNewList.add(eventNew);
//        }
//
//        eventNewsRepo.saveAll(eventNewList);
//    }

    @Override
    public void createAndUpdateEventNews(EventsNewsRequestDto dto, List<MultipartFile> attachmentFiles) throws IOException {
        Long userId = loggedInUserUtil.getLoggedInUserId();
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));


        Optional<EventNew> existingRecord = eventNewsRepo.findByTitle(dto.getTitle());
        EventNew eventNew = existingRecord.orElse(new EventNew());

        eventNew.setUserId(userId);
        eventNew.setTitle(dto.getTitle());
        eventNew.setDescription(dto.getDescription());
        eventNew.setLocation(dto.getLocation());
        eventNew.setPublishedAt(dto.getPublishedAt());
        eventNew.setStartTime(dto.getStartTime());
        eventNew.setEndTime(dto.getEndTime());

        // Improved image handling
        if (dto.getImageIndex() != null && dto.getImageIndex() > 0
                && attachmentFiles != null && !attachmentFiles.isEmpty()
                && dto.getImageIndex() <= attachmentFiles.size()) {
            try {
                MultipartFile file = attachmentFiles.get(dto.getImageIndex() - 1);
                if (!file.isEmpty()) {
                    String directorySuffix = "eventNews/" + userId;
                    Path documentFilePath = Paths.get(directorySuffix);
                    String savedImagePath = imageUtil.saveImage(file, documentFilePath.toString());
                    eventNew.setImageUrl(savedImagePath);
                }
            } catch (IOException e) {
                log.error("Failed to save image: {}", e.getMessage());
                throw new ServiceException("Failed to save image file", e);
            }
        } else if (dto.getImageIndex() != null) { // If image index is specified but invalid
            throw new BadRequestException("Invalid image index: " + dto.getImageIndex());
        }

        eventNewsRepo.save(eventNew);
    }

    @Override
    public EventNewResponseDto getEventNewById(Long id) throws Exception {
        EventNew event = eventNewsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for ID: " + id));

        if (Boolean.TRUE.equals(event.getIsDeleted())) {
            throw new ResourceNotFoundException("Event is deleted or does not exist for ID: " + id);
        }

        EventNewResponseDto dto = new EventNewResponseDto();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());

        if (event.getImageUrl() != null) {
            String encryptedUrl = encryptionUtil.encrypt(
                    event.getImageUrl(),
                    ENCRYPTION_KEY,
                    SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
            );
            dto.setImageUrl(imageUtil.getImageUri(event.getImageUrl(), encryptedUrl));
        } else {
            dto.setImageUrl(null);
        }

        return dto;
    }

    @Override
    public List<EventNewResponseDto> getAllEventNews() throws Exception {

        List<EventNew> eventNewList = eventNewsRepo.findByIsDeletedFalse();

        List<EventNewResponseDto> responseDtos = new ArrayList<>();

        for (EventNew event : eventNewList) {
            if (Boolean.TRUE.equals(event.getIsDeleted())) {
                continue;
            }

            EventNewResponseDto dto = new EventNewResponseDto();
            dto.setId(event.getId());
            dto.setTitle(event.getTitle());
            dto.setDescription(event.getDescription());
            dto.setLocation(event.getLocation());
            dto.setStartTime(event.getStartTime());
            dto.setEndTime(event.getEndTime());

            if (event.getImageUrl() != null) {
                String encryptedUrl = encryptionUtil.encrypt(
                        event.getImageUrl(),
                        ENCRYPTION_KEY,
                        SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
                );
                dto.setImageUrl(imageUtil.getImageUri(event.getImageUrl(), encryptedUrl));
            } else {
                dto.setImageUrl(null);
            }

            responseDtos.add(dto);
        }

        return responseDtos;
    }

    public void deleteEventNew(Long id) {
        EventNew eventNew = eventNewsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education document not found for ID: " + id));
        eventNew.setIsDeleted(true);
        eventNewsRepo.save(eventNew);
    }



}
