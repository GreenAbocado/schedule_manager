package com.greenabocado.schedulemanager.common;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseEntity {

    @Column(nullable=false, updatable=false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable=false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}