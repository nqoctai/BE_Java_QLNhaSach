package doancuoiki.db_cnpm.QuanLyNhaSach.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private int status;
    private Object message;
    private T data;

}
