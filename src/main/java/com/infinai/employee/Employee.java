package com.infinai.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee",schema = "employee")
public class Employee {
  @Id
  @Column(name = "id", nullable = false)
  private long id;
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(name = "product_id", nullable = false)
  private Long productId;
  @Column(name = "content")
  private String content;
  @Column(name = "published_date")
  private Date published;

}
