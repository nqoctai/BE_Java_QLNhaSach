package doancuoiki.db_cnpm.QuanLyNhaSach.domain;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.SecurityUtil;
import jakarta.persistence.*;
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

    @NotBlank(message = "Họ và tên không được để trống")
    @Column(name = "full_name", columnDefinition = "nvarchar(255)")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;

    private String phone;
    private LocalDate hireDate;

    private double salary;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Account account;

    @ManyToOne
    private Role role;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        this.updatedAt = Instant.now();
    }

}
