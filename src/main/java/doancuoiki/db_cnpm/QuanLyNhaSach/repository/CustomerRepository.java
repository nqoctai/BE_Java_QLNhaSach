package doancuoiki.db_cnpm.QuanLyNhaSach.repository;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByEmail(String email);

    Customer findByEmail(String email);

    @Query(value = "SELECT MaKH, TenKH, Email, STD, ChiPhi FROM dbo.Top5_KhachHang()", nativeQuery = true)
    List<Object[]> getTop5Customers();

    @Query(value = "SELECT * FROM VIEW_9", nativeQuery = true)
    List<Object[]> getView9Data();
}
