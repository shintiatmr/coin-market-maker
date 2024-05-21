package com.ajaib.coin.market.maker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -9073499799374251029L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator",
      parameters = {
          @Parameter(
              name = "uuid_gen_strategy_class",
              value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
          )
      }
  )
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  private ZonedDateTime createdAt;

  private ZonedDateTime updatedAt;

  private ZonedDateTime deletedAt;

  @Transient
  private transient BaseEntity previousState;

  protected boolean isManuallySet() {
    if (this.previousState != null && this.previousState.updatedAt != null) {
      return !this.previousState.updatedAt.equals(this.updatedAt);
    } else {
      return true;
    }
  }

  @PrePersist
  protected void prePersist() {
    if (createdAt == null) {
      createdAt = ZonedDateTime.now();
    }
    if (updatedAt == null) {
      updatedAt = ZonedDateTime.now();
    }
    if (id == null) {
      id = UUID.randomUUID();
    }
  }

  @PreUpdate
  protected void preUpdate() {
    if (updatedAt == null || !isManuallySet()) {
      updatedAt = ZonedDateTime.now();
    }
  }

  @PostLoad
  protected void saveState() {
    this.previousState = new BaseEntity();
    this.previousState.createdAt = this.createdAt;
    this.previousState.updatedAt = this.updatedAt;
  }
}
