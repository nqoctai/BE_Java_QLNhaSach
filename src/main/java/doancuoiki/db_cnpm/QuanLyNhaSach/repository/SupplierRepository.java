package doancuoiki.db_cnpm.QuanLyNhaSach.repository;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
