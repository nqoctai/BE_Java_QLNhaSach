package doancuoiki.db_cnpm.QuanLyNhaSach.controller;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Book;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supplier;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supply;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.SupplierService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public ResponseEntity<ApiResponse<List<Supplier>>> getAllSupplier(){
        ApiResponse<List<Supplier>> response = new ApiResponse<>();
        response.setData(supplierService.getAllSupplier());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get all supplier successfully!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<ApiResponse<Supplier>> getSupplierById(@PathVariable("id") Long id) throws AppException {
        Supplier supplier = supplierService.getSupplierById(id);
        if(supplier == null)
        {
            throw new AppException("Supplier not found!");
        }

        ApiResponse<Supplier> response = new ApiResponse<>();
        response.setData(supplier);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get supplier by id successfully!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/supplier/{id}/books")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksBySupplierId(@PathVariable("id") Long id) throws AppException {
        Supplier supplier = supplierService.getSupplierById(id);
        if(supplier == null)
        {
            throw new AppException("Supplier not found!");
        }
        List<Supply> supplies = supplier.getSupplies();
        List<Book> books = supplies.stream().map(Supply::getBook).toList();
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setData(books);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get books by supplier id successfully!");
        return ResponseEntity.ok(response);
    }

}
