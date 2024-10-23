package doancuoiki.db_cnpm.QuanLyNhaSach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);


}
