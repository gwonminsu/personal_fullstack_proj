package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.dto.PostDTO;
import kr.ac.cu.joonggo.dto.PostDetailDTO;
import kr.ac.cu.joonggo.entity.PostCategoryEntity;
import kr.ac.cu.joonggo.entity.PostEntity;
import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostCategoryRepository postCategoryRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    // 모든 게시물 조회
    public List<PostDTO> findAllPosts() {
        return postRepository.findAllPostsAsDTO();
    }

    // id에 해당하는 게시물 하나 조회
    public PostDTO findPostByPostId(Long postId) {
        return postRepository.findPostByPostId(postId);
    }

    // 카테고리 id에 해당하는 모든 게시물 조회
    public List<PostDTO> findAllByCategoryId(Long categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    // 사용자 ID로 게시글 조회
    public List<PostDTO> getPostsByUser(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    // 인기 게시물 순으로 정렬한 목록 조회
    public List<PostDTO> getActivePosts() {
        return postRepository.findActivePostsSortedByHits();
    }

    // 검색어에 따른 게시물 조회
    public List<PostDTO> searchPosts(String query) {
        return postRepository.searchByQuery(query);
    }

    // 게시물 상세 조회
    public PostDetailDTO getPostDetail(Long id) {
        return postRepository.findPostDetailById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));
    }

    // 아이디에 해당하는 게시물의 조회수 증가
    @Transactional
    public void incrementHit(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));
        post.setHit(post.getHit() + 1);
        postRepository.save(post);
    }

    // 게시물 생성
    @Transactional
    public PostDetailDTO createPost(PostDetailDTO postDTO) {
        PostCategoryEntity category = postCategoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        UserEntity user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PostEntity post = PostEntity.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .price(postDTO.getPrice())
                .category(category)
                .user(user)
                .status("판매 중") // Default status
                .imagePaths(postDTO.getImagePaths())
                .build();

        PostEntity savedEntity = postRepository.save(post);
        return convertToDetailDTO(savedEntity);
    }

    // 게시물 수정
    @Transactional
    public PostDetailDTO updatePost(Long id, PostDetailDTO postDTO) {
        PostEntity existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setContent(postDTO.getContent());
        existingPost.setPrice(postDTO.getPrice());
        existingPost.setStatus(postDTO.getStatus());
        existingPost.setImagePaths(postDTO.getImagePaths());

        // Optional: Update the category and user if needed
        if (postDTO.getCategoryId() != null) {
            PostCategoryEntity category = postCategoryRepository.findById(postDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existingPost.setCategory(category);
        }

        PostEntity updatedEntity = postRepository.save(existingPost);
        return convertToDetailDTO(updatedEntity);
    }

    // 게시물 삭제
    @Transactional
    public void deletePost(Long id) {
        likeRepository.deleteByPostIdx(id);

        commentRepository.deleteByPostIdx(id);

        postRepository.deleteById(id);
    }

    // PostEntity를 PostDetailDTO로 변환
    private PostDetailDTO convertToDetailDTO(PostEntity post) {
        return new PostDetailDTO(
                post.getIdx(),
                post.getTitle(),
                post.getPrice(),
                post.getContent(),
                post.getUser().getIdx(),
                post.getUser().getUsername(),
                post.getHit(),
                post.getStatus(),
                post.getCategory().getIdx(),
                post.getCategory().getName(),
                post.getCreatedAt(),
                post.getImagePaths()
        );
    }

}
