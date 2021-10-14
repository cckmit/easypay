package ink.breakpoint.easypay.merchant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.merchant.convert.StaffConvert;
import ink.breakpoint.easypay.merchant.entity.Staff;
import ink.breakpoint.easypay.merchant.mapper.StaffMapper;
import ink.breakpoint.easypay.merchant.service.api.StaffService;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffMapper staffMapper;

    @Override
    public StaffDTO addStaff(StaffDTO staffDTO) throws BusinessException {
        Staff entity = StaffConvert.INSTANCE.dto2entity(staffDTO);
        staffMapper.insert(entity);
        return StaffConvert.INSTANCE.entity2dto(entity);
    }

    @Override
    public String updateStaff(StaffDTO StaffDTO) throws BusinessException {
        Staff entity = StaffConvert.INSTANCE.dto2entity(StaffDTO);
        staffMapper.updateById(entity);
        return null;
    }

    @Override
    public String delateStaff(Long id) throws BusinessException {
        staffMapper.deleteById(id);
        return null;
    }

    /**
     * 根据merchantId分页查询staff列表
     *
     * @param merchantId merchantId
     * @return -
     */
    @Override
    public PageVO<StaffDTO> queryStaffByPage(Long merchantId, Integer pageNo, Integer pageSize) {
        Page<Staff> page = new Page<>(pageNo, pageSize);
        //执行查询
        IPage<Staff> staffIPage = staffMapper.selectPage(page, new LambdaQueryWrapper<Staff>().eq(Staff::getMerchantId, merchantId));
        //去除结果中列表，创建DTO并放入数据
        List<Staff> staffLis = staffIPage.getRecords();
        List<StaffDTO> staffDTOList = StaffConvert.INSTANCE.listentity2dto(staffLis);
        PageVO<StaffDTO> staffDTOPageVO = new PageVO<>();
        staffDTOPageVO.setPage(pageNo);
        staffDTOPageVO.setPageSize(pageSize);
        staffDTOPageVO.setItems(staffDTOList);
        staffDTOPageVO.setCounts(staffIPage.getTotal());
        return staffDTOPageVO;
    }
}
