package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.*;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqPlaceOrder;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResultPaginationDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.CartItemRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.CartRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.OrderItemRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.OrderRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final AccountService accountService;

    private final CartRepository cartRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository, AccountService accountService, CartRepository cartRepository,
            OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.accountService = accountService;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Order placeOrder(ReqPlaceOrder reqPlaceOrder) throws AppException {
        Account account = accountService.getAccountById(reqPlaceOrder.getAccountId());
        if (account == null) {
            throw new AppException("Account not found");
        }
        Customer customer = account.getCustomer();
        if(customer == null){
            throw new AppException("Customer not found");
        }
        Cart cart = this.cartRepository.findByCustomer(customer);
        if (cart == null) {
            throw new AppException("Cart not found");
        }
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null || cartItems.size() == 0) {
            throw new AppException("Cart is empty");
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setReceiverAddress(reqPlaceOrder.getAddress());
        order.setReceiverName(reqPlaceOrder.getName());
        order.setReceiverPhone(reqPlaceOrder.getPhone());
        order.setTotalPrice(reqPlaceOrder.getTotalPrice());
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem = this.orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }

        for (CartItem cartItem : cartItems) {
            this.cartItemRepository.deleteById(cartItem.getId());
        }
        this.cartRepository.deleteById(cart.getId());
        return order;
    }

    public ResultPaginationDTO fetchListOrder(Specification<Order> spec, Pageable pageable) {
        Page<Order> pageOrders = this.orderRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageOrders.getTotalPages());
        meta.setTotal(pageOrders.getTotalElements());
        rs.setMeta(meta);
        rs.setResult(pageOrders.getContent());
        return rs;
    }

    public List<Order> getHistoryOrder(Long accountId) throws AppException {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            throw new AppException("Account not found");
        }

        Customer customer = account.getCustomer();

        return this.orderRepository.findByCustomer(customer);
    }
}
