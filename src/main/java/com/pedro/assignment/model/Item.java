package com.pedro.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table
@Entity
public class Item {
  @Id
  @Column(name="code")
  private String code;

  @Column(name="source")
  private String source;

  @Column(name="codeListCode")
  private String codeListCode;

  @Column(name="displayValue")
  private String displayValue;

  @Column(name="longDescription")
  private String longDescription;

  @Column(name="fromDate")
  private String fromDate;

  @Column(name="toDate")
  private String toDate;

  @Column(name="sortingPriority")
  private int sortingPriority;
}
