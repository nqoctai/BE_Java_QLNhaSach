package doancuoiki.db_cnpm.QuanLyNhaSach.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateAccountDTO {
    private long id;
    private String username;
    private String phone;
    private String avatar;
}
