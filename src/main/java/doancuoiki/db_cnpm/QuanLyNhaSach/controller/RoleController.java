package doancuoiki.db_cnpm.QuanLyNhaSach.controller;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.RoleService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity<ApiResponse<Role>> createRole(@Valid @RequestBody Role role) throws AppException {
        boolean isNameRoleExist = roleService.isNameRoleExist(role.getName());
        if (isNameRoleExist) {
            throw new AppException("Tên role đã tồn tại");
        }
        Role newRole = roleService.createRole(role);
        ApiResponse<Role> response = new ApiResponse<Role>();

        response.setData(newRole);
        response.setMessage("Tạo role thành công");
        response.setStatus(HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
