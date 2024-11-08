package doancuoiki.db_cnpm.QuanLyNhaSach.controller;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supply;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqGetSupply;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResSupply;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.SupplyService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SupplyController {
    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping("/supply")
    public ResponseEntity<ApiResponse<ResSupply>> getSupplyBySupplierIdAndBookId(@RequestBody ReqGetSupply reqGetSupply) throws AppException {
        Supply supply = supplyService.getSupplyBySupplierIdAndBookId(reqGetSupply);
        ResSupply resSupply = new ResSupply();
        resSupply.setId(supply.getId());
        resSupply.setBook(supply.getBook());
        resSupply.setSupplier(supply.getSupplier());
        resSupply.setSupplyPrice(supply.getSupplyPrice());
        ApiResponse<ResSupply> response = new ApiResponse<>();
        response.setData(resSupply);
        response.setStatus(200);
        response.setMessage("Get supply by supplier id and book id successfully!");
        return ResponseEntity.ok(response);
    }
}
