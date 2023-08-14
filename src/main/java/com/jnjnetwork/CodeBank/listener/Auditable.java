package com.jnjnetwork.CodeBank.listener;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getRegDate();
    void setRegDate(LocalDateTime localDateTime);
}
