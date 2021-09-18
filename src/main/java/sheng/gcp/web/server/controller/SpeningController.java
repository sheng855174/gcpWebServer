package sheng.gcp.web.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sheng.gcp.web.server.common.DateFormat;
import sheng.gcp.web.server.common.HttpReceiver;
import sheng.gcp.web.server.common.LoggerOutputFormat;
import sheng.gcp.web.server.controller.tableObject.SpendingItemTable;
import sheng.gcp.web.server.model.goldbook.entity.*;
import sheng.gcp.web.server.service.goodbook.IncomeTypeService;
import sheng.gcp.web.server.service.goodbook.SpendingService;
import sheng.gcp.web.server.service.goodbook.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class SpeningController {

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private IncomeTypeService incomeTypeService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/spendingManage/spending"})
    public String spending(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"get /spendingManage/spending");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<Spending> spendingList = spendingService.getSpendingList(auth.getName());
            List<String> spendingOwnList = new ArrayList<>();
            for(Spending spending : spendingList) {
                List<String> ownList = spendingService.getSpendingOwnList(spending.getId());
                StringBuffer owns = new StringBuffer();
                for(String own : ownList){
                    owns.append(own).append(",");
                }
                spendingOwnList.add(owns.substring(0,owns.length()-1));
            }
            model.addAttribute("spendingList", spendingList);
            model.addAttribute("spendingOwnList", spendingOwnList);
        }
        catch (Exception e){
            LoggerOutputFormat.api_error(request,"get /spendingManage/spending",e);
            return "redirect:..?api_error";
        }
        return "spendingManage/spending";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/spendingManage/spendingDetail/{spendingID}"})
    public String spendingDetail(HttpServletRequest request, Model model,
                                 @PathVariable("spendingID") Long spendingID) {
        LoggerOutputFormat.api_before(request,"get /spendingManage/spendingDetail/{spendingID}");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            // 判斷權限
            if(spendingService.getSpendingOwn(spendingID,auth.getName()) == null){
                log.error("權限錯誤 : " + auth.getName() + ", " + spendingID);
                return "Fail";
            }
            Spending spending = spendingService.findOneSpending(spendingID);
            List<IncomeType> incomes = incomeTypeService.getType(0);
            List<IncomeType> expenses = incomeTypeService.getType(1);
            List<Object[]> objects = spendingService.getSpendingItemList(spendingID,data.getInteger("month"));
            List<SpendingItemTable> spendingItems = new ArrayList<>();
            for(Object[] object : objects){
                SpendingItemTable spendingItemTable = new SpendingItemTable();
                int type = (int)(object[5]);
                int income = (int)(object[3]);
                income = type==0 ? income : -income;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format((object[0]));
                spendingItemTable.setDate(date);
                spendingItemTable.setType_name((String)(object[1]));
                spendingItemTable.setRemark((String)(object[2]));
                spendingItemTable.setIncome(income);
                spendingItemTable.setNickname((String)(object[4]));
                spendingItems.add(spendingItemTable);
            }
            model.addAttribute("spending", spending);
            model.addAttribute("incomes", incomes);
            model.addAttribute("expenses", expenses);
            model.addAttribute("spendingItems", spendingItems);
        }
        catch (Exception e){
            LoggerOutputFormat.api_error(request,"get /spendingManage/spendingDetail/{spendingID}",e);
            return "redirect:..?api_error";
        }
        return "spendingManage/spendingDetail";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping({"/spendingManage/spendingShare"})
    public String spendingShare(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"get /spendingManage/spendingShare");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        }
        catch (Exception e){
            LoggerOutputFormat.api_error(request,"get /spendingManage/spendingShare",e);
            return "redirect:..?api_error";
        }
        return "spendingManage/spendingShare";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/spendingManage/addSpending"})
    @ResponseBody
    public String addSpending(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"post /spendingManage/addSpending");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            String spending_name = data.getString("spending_name");
            String spending_description = data.getString("spending_description");
            Spending spending = new Spending();
            Set<SpendingOwner> spendingOwners = new HashSet<>();
            SpendingOwner spendingOwner = new SpendingOwner();
            spendingOwner.setUsername(auth.getName());
            spendingOwners.add(spendingOwner);
            spending.setName(spending_name);
            spending.setDescription(spending_description);
            spending.setIncome(0);
            spending.setSpendingOwners(spendingOwners);
            spending = spendingService.save(spending);
            log.info("新增帳本 : " + spending);
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /spendingManage/addSpending",e);
            return "Fail";
        }
        return "SUCCESS";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/spendingManage/addIncomeType"})
    @ResponseBody
    public String addIncomeType(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"post /spendingManage/addIncomeType");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            IncomeType incomeType = new IncomeType();
            String type_name = data.getString("type_name");
            String type = data.getString("type");
            incomeType.setType(type.equals("支出")?1:0);
            incomeType.setDescription(type_name);
            incomeType = spendingService.save(incomeType);
            log.info("新增類別 : " + incomeType);
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /spendingManage/addIncomeType",e);
            return "Fail";
        }
        return "SUCCESS";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/spendingManage/addItem"})
    @ResponseBody
    public String addItem(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"post /spendingManage/addItem");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            // 判斷權限
            if(spendingService.getSpendingOwn(data.getLong("spending_id"),auth.getName()) == null){
                log.error("權限錯誤 : " + auth.getName() + ", " + data.getLong("spending_id"));
                return "Fail";
            }
            Long type_id = data.getLong("type_id");
            SpendingItem spendingItem = new SpendingItem();
            spendingItem.setType_id(type_id);
            spendingItem.setIncome(data.getInteger("income"));
            spendingItem.setSpending_id(data.getLong("spending_id"));
            spendingItem.setDescription(data.getString("description"));
            spendingItem.setTime(data.getDate("date"));
            spendingItem.setUsername(auth.getName());
            spendingItem.setNickname(userService.findByUsername(auth.getName()).getNickname());
            spendingItem = spendingService.save(spendingItem);
            // 更新損益
            IncomeType incomeType = incomeTypeService.findOne(type_id);
            int income = incomeType.getType()==0 ?data.getInteger("income"):-data.getInteger("income");
            spendingService.updateIncome(data.getLong("spending_id"),income);
            log.info("新增收支 : " + spendingItem);
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /spendingManage/addItem",e);
            return "Fail";
        }
        return "SUCCESS";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/spendingManage/spendingShare"})
    @ResponseBody
    public String spendingSharePost(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"post /spendingManage/spendingShare");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            // 判斷權限
            if(spendingService.getSpendingOwn(data.getLong("spending_id"),auth.getName()) == null){
                log.error("權限錯誤 : " + auth.getName() + ", " + data.getLong("spending_id"));
                return "Fail";
            }
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            SpendingShareLink spendingShareLink = new SpendingShareLink();
            spendingShareLink.setId(uuidString);
            spendingShareLink.setSpending_id(data.getLong("spending_id"));
            spendingShareLink.setTime(new Date());
            spendingShareLink.setStatus(1);
            spendingShareLink = spendingService.save(spendingShareLink);
            log.info("產生連結 : " + spendingShareLink);
            return uuidString;
        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /spendingManage/spendingShare",e);
            return "Fail";
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping({"/spendingManage/spendingJoin"})
    @ResponseBody
    public String spendingJoin(HttpServletRequest request, Model model) {
        LoggerOutputFormat.api_before(request,"post /spendingManage/spendingJoin");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            JSONObject data = HttpReceiver.receiveData(request);
            log.info(data.toString());
            String uuid = data.getString("shareSpendingID");
            SpendingShareLink spendingShareLink = spendingService.findOneSpendingShareLink(uuid);
            Date final_time = DateFormat.lastHours(spendingShareLink.getTime());
            if(spendingShareLink != null && spendingShareLink.getStatus()>0
                    && DateFormat.belongCalendar(new Date(), spendingShareLink.getTime(), final_time)){
                SpendingOwner spendingOwner = new SpendingOwner();
                spendingOwner.setUsername(auth.getName());
                spendingOwner.setSpending_id(spendingShareLink.getSpending_id());
                spendingOwner = spendingService.save(spendingOwner);
                spendingShareLink.setStatus(0);
                spendingService.save(spendingShareLink);
                log.info("加入帳本 : " + spendingOwner);
                return "SUCCESS";
            }
            else {
                log.info("連結無效");
                return "LINK_EMPTY";
            }

        }catch (Exception e){
            LoggerOutputFormat.api_error(request,"post /spendingManage/spendingJoin",e);
            return "Fail";
        }
    }

}
