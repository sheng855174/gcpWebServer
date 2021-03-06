package sheng.gcp.web.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sheng.gcp.web.server.common.*;
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
    public String login(HttpServletRequest request) {
        log.info(HttpCommon.getIpAddr(request) + " 請求登入頁面");
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        log.info(HttpCommon.getIpAddr(request) + " 請求註冊頁面");
        return "register";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(HttpServletRequest request) {
        LoggerOutputFormat.api_before(request, "/forgotPassword");
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String resetPassword(HttpServletRequest request) {
        LoggerOutputFormat.api_before(request,"post /forgotPassword");
        /*try{
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            // 比對資料
            User user = userService.findByUsername(data.getString("username"));
            if(user!= null && user.getEmail().equals(data.getString("email"))){
                // 發送新密碼
                String password = Generater.generatePassword();
                user.setPassword(passwordEncoder.encode(password));
                userService.save(user);
                log.info("密碼重置 : " + user);
                // 發送email
                new Thread(() -> {
                    try {
                        SmtpGmail.send(user.getEmail(),"情侶契約書密碼重置通知",password);
                    } catch (Exception e) {
                        log.error("post /forgotPassword error : ",e);
                    }
                }).start();
            }
            else {
                //不存在帳號及電子郵件
                log.info("不存在帳號及電子郵件");
                return "redirect:forgotPassword?no_exist";
            }
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /forgotPassword",e);
            return "redirect:login?api_error";
        }*/
        return "redirect:login?success_password";
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
            log.info("註冊成功 : " + user);
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /userRegister",e);
            return "redirect:register?api_error";
        }
        return "redirect:login?success";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/user/resetPassword"})
    public String resetPassword(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request, "/resetPassword");
        return "user/resetPassword";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/user/resetPassword"})
    public String resetPasswordPost(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request, "post /resetPassword");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(auth.getName() + "修改密碼");
            User user = userService.findByUsername(auth.getName());
            String password = data.getString("password");
            String confirmPassword = data.getString("confirmPassword");
            if(!password.equals(confirmPassword)){
                return "redirect:resetPassword?passwordDiff";
            }
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            log.info(user.toString());
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /resetPassword",e);
            return "redirect:resetPassword?api_error";
        }
        return "redirect:resetPassword?success";
    }

}
