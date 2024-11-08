package doancuoiki.db_cnpm.QuanLyNhaSach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Supply> supplies;
}
