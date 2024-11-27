package doancuoiki.db_cnpm.QuanLyNhaSach.repository;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Customer;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Order;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqMonthkyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByCustomer(Customer customer);

    @Query(value = "SELECT Thang AS thang, DoanhThu AS doanhThu FROM dbo.FUNC_10(:nam)", nativeQuery = true)
    List<ReqMonthkyRevenue> findMonthlyRevenueByYear(@Param("nam") int year);

    @Query(value = "EXEC DS_HoaDonKhachHang @maKH = :maKH", nativeQuery = true)
    List<Object[]> getInvoicesByCustomerId(@Param("maKH") Long maKH);

}
