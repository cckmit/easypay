package ink.breakpoint.easypay.merchant.service.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;

public interface StaffService {
    //商户下新增员工
    StaffDTO addStaff(StaffDTO staffDTO) throws BusinessException;

    //商户下编辑员工
    String updateStaff(StaffDTO StaffDTO) throws BusinessException;

    //商户下删除员工
    String delateStaff(Long id) throws BusinessException;

    PageVO<StaffDTO> queryStaffByPage(Long merchantId, Integer pageNo, Integer pageSize);
}