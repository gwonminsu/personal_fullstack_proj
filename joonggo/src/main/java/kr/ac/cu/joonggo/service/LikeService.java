package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.dto.LikeDTO;
import kr.ac.cu.joonggo.entity.LikeEntity;
import kr.ac.cu.joonggo.repository.LikeRepository;
import kr.ac.cu.joonggo.repository.PostRepository;
import kr.ac.cu.joonggo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<LikeDTO> getLikesByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("게시물을 찾을 수 없음: " + postId);
        }
        return likeRepository.findAllLikesByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<LikeDTO> getLikesByUserId(Long userId) {
        if (!postRepository.existsById(userId)) {
            throw new EntityNotFoundException("게시물을 찾을 수 없음: " + userId);
        }
        return likeRepository.findAllLikesByUserId(userId);
    }

    @Transactional
    public LikeDTO addLike(Long postId, Long userId) {
        LikeEntity like = LikeEntity.builder()
                .post(postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없음")))
                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음")))
                .build();
        LikeEntity savedLike = likeRepository.save(like);
        return new LikeDTO(savedLike.getIdx(), savedLike.getPost().getIdx(), savedLike.getUser().getIdx(), savedLike.getCreatedAt());
    }

    @Transactional
    public void removeLike(Long likeId) {
        if (!likeRepository.existsById(likeId)) {
            throw new EntityNotFoundException("찜 아이디를 찾을 수 없음: " + likeId);
        }
        likeRepository.deleteById(likeId);
    }
}
