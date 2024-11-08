package doancuoiki.db_cnpm.QuanLyNhaSach.repository;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Book;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supplier;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Supply findBySupplierAndBook(Supplier supplier, Book book);
}
