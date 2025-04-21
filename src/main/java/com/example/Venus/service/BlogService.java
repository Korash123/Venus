package com.example.Venus.service;

import com.example.Venus.dto.request.BlogRequestDto;
import com.example.Venus.dto.response.BlogResponseDto;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    void CreateBlogAndUpdate(BlogRequestDto requestDto) throws IOException;
    void deleteBlog(Long id);
    List<BlogResponseDto> getAllBlog() throws Exception;
    BlogResponseDto getBlogById() throws Exception;

}