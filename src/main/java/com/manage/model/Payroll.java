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
@Table(name = "payroll")
public class Payroll extends AbstractEntity implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payroll_id", nullable = false)
  private Long payrollId;

  @Column(name = "salary")
  private Double salary;

  @Column(name = "allowance")
  private Double allowance;

  @Column(name = "bonus")
  private Double bonus;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "position_id", nullable = false)
  private Position position;

}
