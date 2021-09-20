package sheng.gcp.web.server.model.goldbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table( name = "incometype",
        indexes = {@Index(name = "index_type", columnList="type", unique=false),})
public class IncomeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 1支出 0收入
    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "description", nullable = false)
    private String description;

}
