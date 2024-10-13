package doancuoiki.db_cnpm.QuanLyNhaSach.controller;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResDashBoard;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.AccountRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DashBoardController {
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;

    public DashBoardController(AccountRepository accountRepository, OrderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/database/dashboard")
    public ResponseEntity<ApiResponse<ResDashBoard>> getDashBoard() {
        long totalAccount = accountRepository.count();
        long totalOrder = orderRepository.count();
        ResDashBoard resDashBoard = new ResDashBoard();
        resDashBoard.setTotalAccount(totalAccount);
        resDashBoard.setTotalOrder(totalOrder);
        ApiResponse<ResDashBoard> response = new ApiResponse<>();
        response.setData(resDashBoard);
        response.setMessage("Get dashboard success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
