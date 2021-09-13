package sheng.gcp.web.server.service.goodbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sheng.gcp.web.server.model.goldbook.entity.Role;
import sheng.gcp.web.server.model.goldbook.entity.User;
import sheng.gcp.web.server.model.goldbook.repository.UserRepository;

import java.util.*;



@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByUsername(username);
        if (user != null){
            log.info(username + "登入成功");
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        }
        log.info("帳號或密碼不正確(" + username + ")");
        throw new UsernameNotFoundException("Invalid username or password.");
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public void updateLoginState(String username,int login_state){
        userRepository.updateLoginState(username,login_state);
    }
}
