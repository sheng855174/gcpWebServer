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
public class SpeningController {

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/spendingManage/spending"})
    public String spending(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"get /spendingManage/spending");
        return "spendingManage/spending";
    }

}
