package sheng.gcp.web.server.controller.tableObject;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
public class SpendingItemTable {

    private String date;
    private String type_name;
    private String remark;
    private int income;
    private String nickname;

}
