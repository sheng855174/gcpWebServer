package sheng.gcp.web.server.model.goldbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table( name = "spending",
        indexes = {@Index(name = "index_name", columnList="name", unique=false)})
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1024, nullable = false)
    private String description;

    @Column(name = "income", nullable = false)
    private int income ;

    @OneToMany(targetEntity= SpendingOwner.class,cascade={CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "spending_id")
    private Set<SpendingOwner> spendingOwners;

}
