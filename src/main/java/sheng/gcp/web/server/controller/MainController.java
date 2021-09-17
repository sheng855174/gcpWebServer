package sheng.gcp.web.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sheng.gcp.web.server.common.LoggerOutputFormat;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class MainController {

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/", "/index","/main/index"})
    public String index(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request, "/index");
        return "main/index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"blank"})
    public String blank(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request, "/blank");
        return "blank";
    }
}
