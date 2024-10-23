package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import org.springframework.stereotype.Service;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean isNameRoleExist(String name) {
        return roleRepository.existsByName(name);
    }

    public Role getRoleById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
