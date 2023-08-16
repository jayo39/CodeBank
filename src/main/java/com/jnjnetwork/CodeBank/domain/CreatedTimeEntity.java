package com.jnjnetwork.CodeBank.domain;

import com.jnjnetwork.CodeBank.listener.Auditable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class CreatedTimeEntity implements Auditable {
    @CreatedDate
    private LocalDateTime regDate;
}
