package kr.ac.cu.joonggo.listener;

import java.time.LocalDateTime;

public interface IAuditable {
    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updatedAt);
}
