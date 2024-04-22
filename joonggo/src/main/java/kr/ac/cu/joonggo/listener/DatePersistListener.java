package kr.ac.cu.joonggo.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class DatePersistListener {
    @PrePersist
    public void prePersist(Object object) {
        if (object instanceof IAuditable) {
            ((IAuditable)object).setCreatedAt(LocalDateTime.now());
            ((IAuditable)object).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        if (object instanceof IAuditable) {
            ((IAuditable)object).setUpdatedAt(LocalDateTime.now());
        }
    }
}
