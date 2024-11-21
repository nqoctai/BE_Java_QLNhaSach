package doancuoiki.db_cnpm.QuanLyNhaSach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {


    @Query(value = "SELECT BookID, BookName, TotalQuantity FROM dbo.SachBanDuocTrongKhoangThoiGian(:startDate, :endDate)", nativeQuery = true)
    List<Object[]> getBooksSoldWithinPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT * FROM VIEW_10", nativeQuery = true)
    List<Object[]> getView7();

    @Query(value = "SELECT * FROM VIEW_6", nativeQuery = true)
    Object getView6Data();
}
