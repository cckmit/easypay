package ink.breakpoint.easypay.merchant.service.api;

import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;

public interface MerchantService {
    /**
     * 根据ID查询商户信息
     * @param merchantId 商户id
     * @return merchantDTO
     */
    MerchantDTO queryMerchantById(Long   merchantId) throws BusinessException;

    /**
     * 商户注册
     * @return merchantDTO
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;

    /**
     * 商户资质申请
     *
     * @param merchantId  商户id
     * @param merchantDTO 商户信息
     * @throws BusinessException 自定义异常
     */
    void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException;


    /**
     * 商户下新增门店
     *
     * @param storeDTO 门店信息对象
     * @return 创建的对象
     * @throws BusinessException 自定义异常
     */
    StoreDTO createStore(StoreDTO storeDTO) throws BusinessException;

    /**
     * 商户新增员工
     *
     * @param staffDTO 员工参数对象
     * @return 新增的员工参数
     * @throws BusinessException 自定义异常
     */
    StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException;

    /**
     * 为门店设置管理员
     *
     * @param storeId
     * @param staffId
     * @throws BusinessException
     */
    void bindStaffToStore(Long storeId, Long staffId) throws BusinessException;

    /**
     * token
     * 根据租户id查询商户的信息
     *
     * @param tenantId
     * @return
     */
    public MerchantDTO queryMerchantByTenantId(Long tenantId);

    /**
     * \* 分页条件查询商户下门店
     * \* @param storeDTO
     * \* @param pageNo
     * \* @param pageSize
     * \* @return
     */
    PageVO<StoreDTO> queryStoreByPage(StoreDTO storeDTO, Integer pageNo, Integer pageSize);

    /**
     * 检验商家服务门店合法性
     * 查询门店是否属于某个商户
     *
     * @param storeId    门店id
     * @param merchantId 商户id
     * @return -
     */
    Boolean queryStoreInMerchant(Long storeId, Long merchantId);
}
