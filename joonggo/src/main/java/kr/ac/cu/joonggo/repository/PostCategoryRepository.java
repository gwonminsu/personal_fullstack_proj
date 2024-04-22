package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.entity.PostCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategoryEntity, Integer> {
    PostCategoryEntity findByName(String name);
}
