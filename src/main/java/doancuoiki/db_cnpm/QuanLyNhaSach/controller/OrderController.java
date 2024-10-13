package doancuoiki.db_cnpm.QuanLyNhaSach.controller;


import com.turkraft.springfilter.boot.Filter;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Order;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqPlaceOrder;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResultPaginationDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.OrderService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<Order>> placeOrder(@RequestBody ReqPlaceOrder reqPlaceOrder) throws AppException {
        Order order = orderService.placeOrder(reqPlaceOrder);
        ApiResponse<Order> apiResponse = new ApiResponse<Order>();
        apiResponse.setData(order);
        apiResponse.setMessage("Place order successfully");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/order")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> fetchListOrder(@Filter Specification<Order> spec, Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = orderService.fetchListOrder(spec, pageable);
        ApiResponse<ResultPaginationDTO> apiResponse = new ApiResponse<ResultPaginationDTO>();
        apiResponse.setData(resultPaginationDTO);
        apiResponse.setMessage("Fetch list order successfully");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/order/history/{accountId}")
    public ResponseEntity<ApiResponse<List<Order>>> getHistoryOrder(@PathVariable("accountId") Long accountId) throws AppException {
        List<Order> orders = orderService.getHistoryOrder(accountId);
        ApiResponse<List<Order>> apiResponse = new ApiResponse<List<Order>>();
        apiResponse.setData(orders);
        apiResponse.setMessage("Get history order successfully");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }





}
