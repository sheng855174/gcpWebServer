package sheng.gcp.web.server.model.goldbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table( name = "spendingsharelink",
        indexes = {@Index(name = "index_spending_id", columnList="spending_id", unique=false)})
public class SpendingShareLink {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "spending_id", nullable = false)
    private Long spending_id;

    @Column(name = "time", nullable = false)
    private Date time;

    // 1 可用、0 無效
    @Column(name = "status", nullable = false)
    private int status;

}
