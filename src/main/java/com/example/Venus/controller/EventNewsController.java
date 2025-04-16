package com.example.Venus.controller;


import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.EventsNewsRequestDto;
import com.example.Venus.dto.response.EventNewResponseDto;
import com.example.Venus.service.EventNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.EventNews.EVENT_BASE_URL)
@RequiredArgsConstructor
public class EventNewsController extends BaseController {
    private final EventNewsService eventNewsService;

//    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
//    public GlobalApiResponse<?> createOrUpdateEventNews(
//            @ModelAttribute EventsNewsRequestDto eventNewsDto,
//            @RequestParam(value = "attachmentFiles", required = false) List<MultipartFile> attachmentFiles
//    ) {
//        eventNewsService.createAndUpdateEventNews(eventNewsDto, attachmentFiles);
//        return getSuccessResponse("event.news.create.update.success", "Event News created or updated successfully", HttpStatus.OK);
//    }
    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createOrUpdateEventNews(
            @ModelAttribute EventsNewsRequestDto eventNewsDto,
            @RequestParam(value = "attachmentFiles", required = false) List<MultipartFile> attachmentFiles
    ) throws IOException {
        eventNewsService.createAndUpdateEventNews(eventNewsDto, attachmentFiles);
        return getSuccessResponse("event.news.create.update.success", "Event News created or updated successfully", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GETBYID)
    public GlobalApiResponse<?> getEventNewById(@PathVariable Long id   ) throws Exception {
        EventNewResponseDto eventNewResponseDto = eventNewsService.getEventNewById(id);
        return getSuccessResponse("event.news.get.success", eventNewResponseDto, HttpStatus.OK);
    }


    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> getEventNews() throws Exception {
        List<EventNewResponseDto> eventNewResponseDtos =  eventNewsService.getAllEventNews();
        return getSuccessResponse("event.news.get.success",eventNewResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
    public GlobalApiResponse<?> deleteDocument(@RequestBody GlobalApiRequest<Long> request) {
        eventNewsService.deleteEventNew(request.getData());
        return getSuccessResponse("event.news.delete.success", "event deleted successfully", HttpStatus.NO_CONTENT);
    }





}
