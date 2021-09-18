package sheng.gcp.web.server.service.goodbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sheng.gcp.web.server.model.goldbook.entity.IncomeType;
import sheng.gcp.web.server.model.goldbook.repository.IncomeTypeRepository;

import java.util.List;

@Service
@Slf4j
public class IncomeTypeServiceImpl implements IncomeTypeService{

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Override
    public List<IncomeType> getType(int type){
        return incomeTypeRepository.getType(type);
    }

    @Override
    public IncomeType findOne(Long id){
        return incomeTypeRepository.findOne(id);
    }
}
