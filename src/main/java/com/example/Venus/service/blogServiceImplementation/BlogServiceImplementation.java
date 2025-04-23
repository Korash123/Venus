package com.example.Venus.service.blogServiceImplementation;


import com.example.Venus.dto.request.BlogRequestDto;
import com.example.Venus.dto.response.BlogResponseDto;
import com.example.Venus.entities.Blog;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.BlogRepo;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.BlogService;
import com.example.Venus.utils.ImageUtil;
import com.example.Venus.utils.LoggedInUserUtil;
import com.example.Venus.utils.SymmetricEncryptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class BlogServiceImplementation implements BlogService {
    private final BlogRepo blogRepo;
    private final UsersRepo usersRepo;
    private final ImageUtil imageUtil;
    private final LoggedInUserUtil loggedInUserUtil;
    private final SymmetricEncryptionUtil encryptionUtil;
    private static final String ENCRYPTION_KEY = "QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=";
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ObjectMapper objectMapper;


    @Override
    public void CreateBlogAndUpdate(BlogRequestDto requestDto) throws IOException {
       Long userId = loggedInUserUtil.getLoggedInUserId();
       Users user = usersRepo.findById(userId).orElseThrow(()->
        new ResourceNotFoundException("User Not Found"));

       String blogSuffix = "/blog/";

        Optional<Blog> existingBlog= blogRepo.findByUserIdAndTitleAndContent(userId,requestDto.getTitle(),requestDto.getContent());

        Blog blog = existingBlog.orElse(new Blog());

        blog.setUserId(userId);
        blog.setTitle(requestDto.getTitle());
        blog.setContent(requestDto.getContent());

        if (requestDto.getImageUrl() != null) {
            String fileName = userId + "/blog/";
            Path filePath = Paths.get(blogSuffix + fileName);
            String saveBlogImage = imageUtil.saveImage(requestDto.getImageUrl(), filePath.toString());

            blog.setImage(saveBlogImage);
        }else {
            blog.setImage(null);
        }

        blogRepo.save(blog);

    }


    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Blog Not Found"));
        blog.setIsDeleted(true);
        blogRepo.save(blog);

    }

    @Override
    public List<BlogResponseDto> getAllBlog() throws Exception {

        List<Blog> blogList = blogRepo.findAll();

        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();

        for (Blog blog : blogList) {
            if (Boolean.TRUE.equals(blog.getIsDeleted())) {
                continue;
            }

            BlogResponseDto blogResponseDto = new BlogResponseDto();
            blogResponseDto.setId(blog.getId());
            blogResponseDto.setTitle(blog.getTitle());
            blogResponseDto.setContent(blog.getContent());

            if (blog.getImage() != null) {
                String encryptedUrl = encryptionUtil.encrypt(blog.getImage(),ENCRYPTION_KEY,
                        SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC);
                blogResponseDto.setImageUrl(imageUtil.getImageUri(blog.getImage(), encryptedUrl));
            }else {
                blogResponseDto.setImageUrl(null);
            }
            blogResponseDtoList.add(blogResponseDto);
        }
        return blogResponseDtoList;
    }

    @Override
    public BlogResponseDto getBlogById() throws Exception {
        Long userId = loggedInUserUtil.getLoggedInUserId();
        Blog blog = blogRepo.findByUserId(userId).orElseThrow(()->
                new ResourceNotFoundException("Blog Not Found"));

        if (Boolean.TRUE.equals(blog.getIsDeleted())) {
            throw new ResourceNotFoundException("Blog Not Found");
        }

        BlogResponseDto blogResponseDto = new BlogResponseDto();
        blogResponseDto.setTitle(blog.getTitle());
        blogResponseDto.setContent(blog.getContent());
        if (blog.getImage() != null) {
            String encrytedUrl = encryptionUtil.encrypt(
                    blog.getImage(),
                    ENCRYPTION_KEY,
                    SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
            );
            String imageUrl = imageUtil.getImageUri(blog.getImage(), encrytedUrl);
            blogResponseDto.setImageUrl(imageUrl);
        } else {
            blogResponseDto.setImageUrl(null);
        }
        return blogResponseDto;
    }


}
