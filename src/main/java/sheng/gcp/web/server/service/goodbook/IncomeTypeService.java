package sheng.gcp.web.server.service.goodbook;

import sheng.gcp.web.server.model.goldbook.entity.IncomeType;

import java.util.List;

public interface IncomeTypeService {

    List<IncomeType> getType(int type);

    IncomeType findOne(Long id);

}
