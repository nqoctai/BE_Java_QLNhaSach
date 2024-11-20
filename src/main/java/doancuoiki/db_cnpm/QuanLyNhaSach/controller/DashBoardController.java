package doancuoiki.db_cnpm.QuanLyNhaSach.controller;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ViewDB.View1DTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ViewDB.View6DTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ViewDB.View7DTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ViewDB.View9DTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqMonthkyRevenue;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResDashBoard;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.AccountRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.OrderRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.BookService;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.CategoryService;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.CustomerService;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DashBoardController {
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final BookService bookService;
    private final CustomerService customerService;

    private final CategoryService categoryService;

    public DashBoardController(AccountRepository accountRepository, OrderRepository orderRepository, OrderService orderService, CategoryService categoryService, BookService bookService, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.customerService = customerService;
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

    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<ReqMonthkyRevenue>>> getMonthlyRevenue(@RequestParam int year) {
        ApiResponse<List<ReqMonthkyRevenue>> response = new ApiResponse();
        response.setData(orderService.getMonthlyRevenueByYear(year));
        response.setMessage("Get monthly revenue success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/view1")
    public ResponseEntity<ApiResponse<List<View1DTO>>> getView1() {
        ApiResponse<List<View1DTO>> response = new ApiResponse<>();
        response.setData(categoryService.getView1Data());
        response.setMessage("Get view1 success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/get-top5-books-sold")
    public ResponseEntity<ApiResponse<List<View7DTO>>> getTop5BooksSold() {
        List<View7DTO> res = bookService.getTop5BooksSold();
        ApiResponse<List<View7DTO>> response = new ApiResponse<>();
        response.setData(res);
        response.setMessage("Lấy top 5 sách bán nhiều nhất thành công");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/dashboard/view6")
    public ResponseEntity<ApiResponse<View6DTO>> getView6() {
        ApiResponse<View6DTO> response = new ApiResponse<>();
        response.setData(bookService.getView6Data());
        response.setMessage("Get view6 success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/view9")
    public ResponseEntity<ApiResponse<List<View9DTO>>> getView9() {
        ApiResponse<List<View9DTO>> response = new ApiResponse<>();
        response.setData(customerService.getView9Data());
        response.setMessage("Get view9 success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
