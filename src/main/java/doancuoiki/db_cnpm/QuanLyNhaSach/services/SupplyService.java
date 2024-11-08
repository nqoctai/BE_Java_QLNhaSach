package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Book;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supplier;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supply;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqGetSupply;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.SupplyRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.stereotype.Service;

@Service
public class SupplyService {
    private final SupplyRepository supplyRepository;

    private final BookService bookService;

    private final SupplierService supplierService;

    public SupplyService(SupplyRepository supplyRepository, BookService bookService,SupplierService supplierService ) {
        this.supplyRepository = supplyRepository;
        this.bookService = bookService;
        this.supplierService = supplierService;
    }

    public Supply getSupplyBySupplierIdAndBookId(ReqGetSupply reqGetSupply) throws AppException {

        Supplier supplier = supplierService.getSupplierById(reqGetSupply.getSupplierID());
        if(supplier==null)
        {
            throw new AppException("Supplier not found");
        }
        Book book = bookService.getBookById(reqGetSupply.getBookID());
        if(book==null)
        {
            throw new AppException("Book not found");
        }
        Supply supply = supplyRepository.findBySupplierAndBook(supplier, book);
        if(supply==null)
        {
            throw new AppException("Supply not found");
        }
        return supply;
    }

    public Supply getSupplyById(Long id){
        return supplyRepository.findById(id).orElse(null);
    }
}
