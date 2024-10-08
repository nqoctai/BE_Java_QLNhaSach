package doancuoiki.db_cnpm.QuanLyNhaSach.domain;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Setter
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    private String phone;
    private Instant hireDate;

    private double salary;

}
