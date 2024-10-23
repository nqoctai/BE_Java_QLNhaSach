package doancuoiki.db_cnpm.QuanLyNhaSach.controller;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.RoleService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import jakarta.validation.Valid;

import java.util.List;

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

    @GetMapping("/role")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRole() {
        List<Role> listRole = roleService.getAllRole();
        ApiResponse<List<Role>> response = new ApiResponse<List<Role>>();
        response.setData(listRole);
        response.setMessage("Lấy danh sách role thành công");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
