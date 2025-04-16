package com.example.Venus.service;

import com.example.Venus.dto.request.EventsNewsRequestDto;
import com.example.Venus.dto.response.EventNewResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventNewsService {

//    void createAndUpdateEventNews(String EventNewsDto,
//                                  List<MultipartFile> attachmentFile) throws IOException;
   void createAndUpdateEventNews(EventsNewsRequestDto dto, List<MultipartFile> attachmentFiles) throws IOException;
   public EventNewResponseDto getEventNewById(Long id) throws Exception;
   List<EventNewResponseDto> getAllEventNews() throws Exception;
   void deleteEventNew(Long id);
}
