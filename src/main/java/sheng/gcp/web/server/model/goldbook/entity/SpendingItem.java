package sheng.gcp.web.server.model.goldbook.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table( name = "SpendingItem",
        indexes = {@Index(name = "index_spending_id", columnList="spending_id", unique=false),
                @Index(name = "index_type_id", columnList="type_id", unique=false),})
public class SpendingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spending_id", nullable = false)
    private Long spending_id;

    @Column(name = "type_id")
    private Long type_id;

    @Column(name = "description")
    private String description;

    @Column(name = "income", nullable = false)
    private int income ;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "time")
    private Date time;

}
