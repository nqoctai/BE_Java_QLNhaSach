package doancuoiki.db_cnpm.QuanLyNhaSach.services;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.OrderShippingEvent;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.OrderShippingEventRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderShippingEventService {
    private final OrderShippingEventRepository orderShippingEventRepository;

    public OrderShippingEventService(OrderShippingEventRepository orderShippingEventRepository) {
        this.orderShippingEventRepository = orderShippingEventRepository;
    }

    public OrderShippingEvent createOrderShippingEvent(OrderShippingEvent orderShippingEvent) {
        return orderShippingEventRepository.save(orderShippingEvent);
    }
}
