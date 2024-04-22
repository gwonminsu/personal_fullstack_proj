package kr.ac.cu.joonggo.controller;

import kr.ac.cu.joonggo.dto.NoticeDTO;
import kr.ac.cu.joonggo.dto.NoticeDetailDTO;
import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import kr.ac.cu.joonggo.entity.NoticeEntity;
import kr.ac.cu.joonggo.service.NoticeCategoryService;
import kr.ac.cu.joonggo.service.NoticeService;
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
@RequiredArgsConstructor // 생성자를 통한 Service 주입
@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    private final NoticeCategoryService noticeCategoryService;

    private final String IMAGE_DIRECTORY = "C:/Users/kd061/Desktop/grade7/full_stack_projects/indiviual_proj/storage"; // 이미지를 저장할 디렉토리

    @GetMapping
    public List<NoticeDTO> getAllNotices() {
        System.out.println("공지사항 리스트 GET 요청..");
        return noticeService.findAllNotices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailDTO> getNoticeDetail(@PathVariable Long id) {
        System.out.println("공지사항 디테일 GET 요청..");
        noticeService.incrementHit(id); // 조회수 증가
        NoticeDetailDTO noticeDetail = noticeService.getNoticeDetail(id);
        return ResponseEntity.ok(noticeDetail);
    }

    // 카테고리 목록 제공
    @GetMapping("/categories")
    public ResponseEntity<List<NoticeCategoryEntity>> getAllCategories() {
        System.out.println("공지사항 카테고리 목록 GET 요청..");
        List<NoticeCategoryEntity> categories = noticeCategoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<NoticeDetailDTO> createNotice(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        System.out.println("새 공지사항 등록 POST 요청..");

        String imagePaths = ""; // imagePaths를 빈 문자열로 초기화

        if (images != null && images.length > 0) { // 이미지가 제공되었는지 그리고 비어있지 않은지 확인
            imagePaths = Arrays.stream(images)
                    .filter(Objects::nonNull)
                    .filter(image -> !image.isEmpty())
                    .map(image -> saveImage(image, "notices"))
                    .collect(Collectors.joining(";")); // 이미지 경로를 ";"로 구분하여 저장
        }

        NoticeDetailDTO noticeDTO = new NoticeDetailDTO();
        noticeDTO.setTitle(title);
        noticeDTO.setContent(content);
        noticeDTO.setCategoryId(categoryId);
        noticeDTO.setUserId(userId);
        noticeDTO.setImagePaths(imagePaths);

        NoticeDetailDTO createdNotice = noticeService.createNotice(noticeDTO);
        return new ResponseEntity<>(createdNotice, HttpStatus.CREATED);
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


    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoticeDetailDTO> updateNotice(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        System.out.println("공지사항 수정 PUT 요청..");

        // 여러 이미지 파일 경로를 저장하는 로직
        String imagePaths = Arrays.stream(images)
                .filter(Objects::nonNull)
                .filter(image -> !image.isEmpty())
                .map(image -> saveImage(image, "notices"))
                .collect(Collectors.joining(";")); // ";"로 경로를 구분하여 저장

        NoticeDetailDTO noticeDTO = new NoticeDetailDTO();
        noticeDTO.setTitle(title);
        noticeDTO.setContent(content);
        noticeDTO.setCategoryId(categoryId);
        noticeDTO.setUserId(userId);
        noticeDTO.setImagePaths(imagePaths);

        NoticeDetailDTO updatedNotice = noticeService.updateNotice(id, noticeDTO);
        return ResponseEntity.ok(updatedNotice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        System.out.println("공지사항 DELETE 요청..");
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();  // 성공적으로 삭제 처리 후 응답
    }
}
