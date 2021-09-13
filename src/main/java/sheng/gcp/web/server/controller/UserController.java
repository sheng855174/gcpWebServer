package sheng.gcp.web.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sheng.gcp.web.server.common.HttpCommon;
import sheng.gcp.web.server.common.HttpReceiver;
import sheng.gcp.web.server.common.LoggerOutputFormat;
import sheng.gcp.web.server.model.goldbook.entity.Role;
import sheng.gcp.web.server.model.goldbook.entity.User;
import sheng.gcp.web.server.service.goodbook.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(HttpServletRequest httpServletRequest) {
        log.info(HttpCommon.getIpAddr(httpServletRequest) + " 請求登入頁面");
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest httpServletRequest) {
        log.info(HttpCommon.getIpAddr(httpServletRequest) + " 請求註冊頁面");
        return "register";
    }

    @GetMapping("/contract")
    public String contract(HttpServletRequest request) {
        LoggerOutputFormat.api_before(request, "/contract");
        return "contract";
    }

    @PostMapping("/userRegister")
    public String userRegister(HttpServletRequest request){
        LoggerOutputFormat.api_before(request,"post /userRegister");
        try {
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            // 驗證
            if(!data.getString("password").equals(data.getString("password_confirmation"))){
                log.info("密碼不一致");
                return "redirect:register?passwordDiff";
            }
            if(userService.findByUsername(data.getString("username")) != null){
                log.info("帳號存在");
                return "redirect:register?repeatUsername";
            }
            User user = new User();
            user.setUsername(data.getString("username"));
            user.setEmail(data.getString("email"));
            user.setNickname(data.getString("nickname"));
            user.setPassword(passwordEncoder.encode(data.getString("password")));
            user.setLogin_state(0);
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(data.getString("username"),"USER"));
            user.setRoles(roles);
            userService.save(user);
            log.info("註冊成功 : " );
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /userRegister",e);
            return "redirect:register?api_error";
        }
        return "redirect:login?success";
    }

}
