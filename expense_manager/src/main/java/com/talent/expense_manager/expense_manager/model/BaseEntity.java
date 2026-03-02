package com.talent.expense_manager.expense_manager.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDatetime;

    @Column(name = "updated_at")
    private LocalDateTime updatedDatetime;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "deleted_at")
    private LocalDateTime deletedDatetime;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDatetime = now;
        this.updatedDatetime = now;

        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDatetime = LocalDateTime.now();

        if (Boolean.FALSE.equals(this.active) && this.deletedDatetime == null) {
            this.deletedDatetime = LocalDateTime.now();
        }
    }

    public Boolean isActive() {
        return this.active;
    }

}
