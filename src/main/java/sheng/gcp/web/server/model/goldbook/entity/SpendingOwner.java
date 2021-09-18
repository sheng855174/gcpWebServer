package sheng.gcp.web.server.model.goldbook.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table( name = "SpendingOwner",
        indexes = {@Index(name = "index_username", columnList="username", unique=false),
                @Index(name = "index_spending_id", columnList="spending_id", unique=false),})
public class SpendingOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spending_id")
    private Long spending_id;

    @Column(name = "username", nullable = false)
    private String username;

}
