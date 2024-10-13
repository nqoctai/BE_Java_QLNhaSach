package doancuoiki.db_cnpm.QuanLyNhaSach.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqPlaceOrder {
    private long accountId;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
}
