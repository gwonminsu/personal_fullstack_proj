package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCategoryRepository extends JpaRepository<NoticeCategoryEntity, Integer> {
    NoticeCategoryEntity findByName(String name);

}
