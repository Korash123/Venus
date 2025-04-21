package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.BlogRequestDto;
import com.example.Venus.dto.request.FacultyStaffRequestDto;
import com.example.Venus.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(URL_CONSTANTS.Blog.USER_BASE_URL)
@RequiredArgsConstructor
public class BlogController extends BaseController {

    private final BlogService blogService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> CreateBlogAndUpdate(@ModelAttribute BlogRequestDto requestDto) throws Exception {
        blogService.CreateBlogAndUpdate(requestDto);
        return getSuccessResponse("blog.create", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> getAllBlogs() throws Exception {
        return getSuccessResponse("blog.get", blogService.getAllBlog(),HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_BY_ID)
    public GlobalApiResponse<?> getBlogById() throws Exception {
        return getSuccessResponse("blog.get",blogService.getBlogById(), HttpStatus.OK);
    }


}
