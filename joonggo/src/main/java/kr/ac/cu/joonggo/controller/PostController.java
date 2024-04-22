package kr.ac.cu.joonggo.controller;

import kr.ac.cu.joonggo.dto.PostDTO;
import kr.ac.cu.joonggo.dto.PostDetailDTO;
import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import kr.ac.cu.joonggo.entity.PostCategoryEntity;
import kr.ac.cu.joonggo.entity.PostEntity;
import kr.ac.cu.joonggo.service.PostCategoryService;
import kr.ac.cu.joonggo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final PostCategoryService postCategoryService;

    private final String IMAGE_DIRECTORY = "C:/Users/kd061/Desktop/grade7/full_stack_projects/indiviual_proj/storage"; // 이미지를 저장할 디렉토리

    @GetMapping
    public List<PostDTO> getAllPosts() {
        System.out.println("게시물 전체 리스트 GET 요청..");
        return postService.findAllPosts();
    }

    // 게시물 아이디로 게시물 하나 불러오기
    @GetMapping("/one/{postId}")
    public PostDTO getOnePost(@PathVariable Long postId) {
        System.out.println("게시물 하나 GET 요청..");
        return postService.findPostByPostId(postId);
    }

    // 검색어로 게시물 검색
    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String query) {
        List<PostDTO> posts = postService.searchPosts(query);
        return ResponseEntity.ok(posts);
    }

    // HOT 게시물 목록
    @GetMapping("/hot")
    public ResponseEntity<List<PostDTO>> getHotPosts() {
        System.out.println("HOT 게시물 전체 리스트 GET 요청..");
        List<PostDTO> hotPosts = postService.getActivePosts();
        return ResponseEntity.ok(hotPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPostById(@PathVariable Long id) {
        System.out.println("게시물 디테일 GET 요청..");
        postService.incrementHit(id);
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
        System.out.println("카테고리" + categoryId + " 게시물 GET 요청..");
        List<PostDTO> posts = postService.findAllByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 사용자가 작성한 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
        System.out.println("유저" + userId + " 게시물 디테일 GET 요청..");
        List<PostDTO> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    // 카테고리 목록 제공
    @GetMapping("/categories")
    public ResponseEntity<List<PostCategoryEntity>> getAllCategories() {
        System.out.println("게시물 카테고리 목록 GET 요청..");
        List<PostCategoryEntity> categories = postCategoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<PostDetailDTO> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") int price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        System.out.println("새 게시물 등록 POST 요청..");

        String imagePaths = ""; // imagePaths를 빈 문자열로 초기화

        if (images != null && images.length > 0) { // 이미지가 제공되었는지 그리고 비어있지 않은지 확인
            imagePaths = Arrays.stream(images)
                    .filter(Objects::nonNull)
                    .filter(image -> !image.isEmpty())
                    .map(image -> saveImage(image, "posts"))
                    .collect(Collectors.joining(";")); // 이미지 경로를 ";"로 구분하여 저장
        }

        PostDetailDTO postDTO = new PostDetailDTO();
        postDTO.setTitle(title);
        postDTO.setContent(content);
        postDTO.setPrice(price);
        postDTO.setCategoryId(categoryId.intValue());
        postDTO.setUserId(userId);
        postDTO.setImagePaths(imagePaths);

        PostDetailDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDetailDTO> updatePost(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") int price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("userId") Long userId,
            @RequestParam("status") String status,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        System.out.println("게시물 수정 PUT요청..");

        String imagePaths = ""; // imagePaths를 빈 문자열로 초기화

        if (images != null && images.length > 0) { // 이미지가 제공되었는지 그리고 비어있지 않은지 확인
            imagePaths = Arrays.stream(images)
                    .filter(Objects::nonNull)
                    .filter(image -> !image.isEmpty())
                    .map(image -> saveImage(image, "posts"))
                    .collect(Collectors.joining(";")); // 이미지 경로를 ";"로 구분하여 저장
        }

        PostDetailDTO postDTO = new PostDetailDTO();
        postDTO.setTitle(title);
        postDTO.setContent(content);
        postDTO.setPrice(price);
        postDTO.setCategoryId(categoryId.intValue());
        postDTO.setUserId(userId);
        postDTO.setImagePaths(imagePaths);
        postDTO.setStatus(status);

        PostDetailDTO updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        System.out.println("게시물 DELETE 요청..");
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private String saveImage(MultipartFile file, String type) {
        try {
            String fileName = generateFileName(file.getOriginalFilename(), type);
            Path destinationPath = Paths.get(IMAGE_DIRECTORY).resolve(fileName);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:7979/resources/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Image save failed", e);
        }
    }

    private String generateFileName(String originalFileName, String type) {
        String fileExtension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        return type + "_" + timestamp + fileExtension;
    }
}
