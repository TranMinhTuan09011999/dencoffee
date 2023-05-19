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
@Table(name = "attendance")
public class Attendance extends AbstractEntity implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_id", nullable = false)
  private Long attendanceId;

  @Column(name = "actual_start_date_time")
  private Date actualStartDateTime;

  @Column(name = "start_date_time")
  private Date startDateTime;

  @Column(name = "end_date_time")
  private Date endDateTime;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "payroll_id", nullable = false)
  private Payroll payroll;

}
