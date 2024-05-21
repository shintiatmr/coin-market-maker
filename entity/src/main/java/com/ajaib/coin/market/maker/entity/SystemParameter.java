package com.ajaib.coin.market.maker.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "system_parameters")
@EqualsAndHashCode(callSuper = true)
public class SystemParameter extends BaseEntity {

  private String variable;
  private String value;
  private String description;

}
