package com.manage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "salary_advance")
public class SalaryAdvance extends AbstractEntity implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "salary_advance_id", nullable = false)
  private Long salaryAdvanceId;

  @Column(name = "salary_advance_amount")
  private Double salaryAdvanceAmount;

  @Column(name = "salary_advance_date")
  private Date salaryAdvanceDate;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

}
