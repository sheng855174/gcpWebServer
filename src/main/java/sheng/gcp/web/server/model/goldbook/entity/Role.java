package sheng.gcp.web.server.model.goldbook.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table( name = "role",
        indexes = {@Index(name = "index_username", columnList="username", unique=false),
                   @Index(name = "index_role", columnList="role", unique=false)})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private String role;

    public Role(){

    }

    public Role(String username, String role){
        this.username = username;
        this.role = role;
    }

}
