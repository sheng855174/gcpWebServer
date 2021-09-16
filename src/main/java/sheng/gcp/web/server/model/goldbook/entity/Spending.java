package sheng.gcp.web.server.model.goldbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table( name = "Spending",
        indexes = {@Index(name = "index_name", columnList="name", unique=false),
                @Index(name = "index_username", columnList="username", unique=false),})
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

}
