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
@Table( name = "user",
        indexes = {})
public class User {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "login_state", nullable = false)
    private int login_state=0;

    @OneToMany(targetEntity= Role.class,cascade={CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private Set<Role> roles;

}

