package doancuoiki.db_cnpm.QuanLyNhaSach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplies")
@Getter
@Setter
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double supplyPrice;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


}
