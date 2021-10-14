package ink.breakpoint.easypay.merchant.service;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.common.cache.util.PhoneUtil;
import ink.breakpoint.easypay.merchant.convert.MerchantConvert;
import ink.breakpoint.easypay.merchant.convert.StaffConvert;
import ink.breakpoint.easypay.merchant.convert.StoreConvert;
import ink.breakpoint.easypay.merchant.entity.Merchant;
import ink.breakpoint.easypay.merchant.entity.Staff;
import ink.breakpoint.easypay.merchant.entity.Store;
import ink.breakpoint.easypay.merchant.entity.StoreStaff;
import ink.breakpoint.easypay.merchant.mapper.MerchantMapper;
import ink.breakpoint.easypay.merchant.mapper.StaffMapper;
import ink.breakpoint.easypay.merchant.mapper.StoreMapper;
import ink.breakpoint.easypay.merchant.mapper.StoreStaffMapper;
import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;
import ink.breakpoint.easypay.user.api.TenantService;
import ink.breakpoint.easypay.user.api.dto.tenant.CreateTenantRequestDTO;
import ink.breakpoint.easypay.user.api.dto.tenant.TenantDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class MerchantServiceImpl implements ink.breakpoint.easypay.merchant.service.api.MerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    StoreMapper storeMapper;

    @Reference
    TenantService tenantService;
    @Autowired
    StoreStaffMapper storeStaffMapper;
    @Autowired
    private StaffMapper staffMapper;

    /**
     * 根据 商家id查询商家信息
     *
     * @param merchantId 商户id
     * @return 商家信息
     * @throws BusinessException 自定义异常
     */
    @Override
    public MerchantDTO queryMerchantById(Long merchantId) throws BusinessException {
        if (merchantId == null) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        Merchant merchant = merchantMapper.selectById(merchantId);
        return MerchantConvert.INSTANCE.entity2Dto(merchant);
    }

    /**
     * 商户注册服务
     *
     * @param merchantDTO 商户信息
     * @return
     */

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        //传入对象非空校验
        if (merchantDTO == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        // 手机号非空校验
        if (StringUtils.isEmpty(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200230);
        }
        // 手机号合法性校验
        if (!PhoneUtil.isMatches(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200224);
        }
        // 用户名非空校验
        if (StringUtils.isEmpty(merchantDTO.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_200231);
        }
        // 手机号重复校验
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        wrapper.eq(Merchant::getMobile, merchantDTO.getMobile());
        Integer count = merchantMapper.selectCount(wrapper);
        // 如果大于0 说明手机号已存在，抛异常
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_200203);
        }

        //====================================================
        //调用Sass接口 添加租户并绑定 门店 员工  门店 之间的关系
        //====================================================

        CreateTenantRequestDTO createTenantRequestDTO = new CreateTenantRequestDTO();
        //添加账号信息
        createTenantRequestDTO.setUsername(merchantDTO.getUsername());
        createTenantRequestDTO.setMobile(merchantDTO.getMobile());
        createTenantRequestDTO.setPassword(merchantDTO.getPassword());
        //设置租户类型
        createTenantRequestDTO.setTenantTypeCode("huimin-merchant");
        //设置默认套餐
        createTenantRequestDTO.setBundleCode("huimin-merchant");
        //租户名称，和账户名一致
        createTenantRequestDTO.setName(merchantDTO.getUsername());


//如果租户在SaaS已经存在，SaaS直接 返回此租户的信息，否则进行添加
        TenantDTO tenantAndAccount = tenantService.createTenantAndAccount(createTenantRequestDTO);
        //获取租户的id
        if (tenantAndAccount == null || tenantAndAccount.getId() == null) {
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        //租户的id
        Long tenantId = tenantAndAccount.getId();
        //租户id在商户表唯一      租户  和   商户 是  一一对应的， 商户就是在  saas 中的租户
        //根据租户id从商户表查询，如果存在记录则不允许添加商户
        Integer count1 = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        if (count1 > 0) {
            throw new BusinessException(CommonErrorCode.E_200017);
        }

        // 3. 设置 租户 和 商户 绑定关系
        //将dto转成entity
        Merchant entity = MerchantConvert.INSTANCE.dto2Entity(merchantDTO);
        //设置所对应的租户的Id
        entity.setTenantId(tenantId);  // 设置 商户 所属的租户id  注意 商户和租户是一一对应的
        //设置审核状态0‐未申请,1‐已申请待审核,2‐审核通过,3‐审核拒绝
        entity.setAuditStatus("0");
        //保存商户信息
        merchantMapper.insert(entity);

        //4.新增门店  创建根门店
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName("根门店");
        storeDTO.setMerchantId(entity.getId());//商户id 也是 租户id
        StoreDTO store = createStore(storeDTO);

        //5.新增员工
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setMobile(merchantDTO.getMobile());//手机号
        staffDTO.setUsername(merchantDTO.getUsername());//账号
        // 给员工设置所属门店 此处为根门店
        staffDTO.setStoreId(store.getId());//员所属门店id
        staffDTO.setMerchantId(entity.getId());//商户id

        StaffDTO staff = createStaff(staffDTO);

        //6.为门店设置管理员
        bindStaffToStore(store.getId(), staff.getId());

        //将entity转成 dto
        MerchantDTO merchantDTONew = MerchantConvert.INSTANCE.entity2Dto(entity);
        return merchantDTONew;
    }

    /**
     * @param merchantId  商户id
     * @param merchantDTO 商户信息
     * @throws BusinessException 自定义异常
     */
    @Override
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        // 非空校验
        if (merchantId == null || merchantDTO == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        // 校验id的合法性
        Merchant m1 = merchantMapper.selectById(merchantId);
        if (m1 == null) {
            throw new BusinessException(CommonErrorCode.E_200227);
        }
        // 将dto转为entity
        Merchant merchant = MerchantConvert.INSTANCE.dto2Entity(merchantDTO);
        // 将关键信息设置进去
        merchant.setId(m1.getId());
        merchant.setMobile(m1.getMobile());
        merchant.setAuditStatus("1"); // 已申请 待审核
        merchant.setTenantId(m1.getTenantId());
        merchantMapper.updateById(merchant);
    }
    //====================================================================================

    /**
     * 创建们带你
     *
     * @param storeDTO 门店信息对象
     * @return 门店信息对象
     * @throws BusinessException 自定义异常
     */
    @Override
    public StoreDTO createStore(StoreDTO storeDTO) throws BusinessException {
        if (storeDTO == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //dto2entity
        Store store = StoreConvert.INSTANCE.DTO2entity(storeDTO);
        //调用mapper中添加方法
        storeMapper.insert(store);
        return StoreConvert.INSTANCE.entity2DTO(store);
    }

    /**
     * 商户下 新增员工
     *
     * @param staffDTO -
     */
    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException {
        //1.校验手机号格式及是否存在
        String mobile = staffDTO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //根据商户id和手机号校验唯一性
        if (isExistStaffByMobile(mobile, staffDTO.getMerchantId())) {
            throw new BusinessException(CommonErrorCode.E_100113);
        }
//2.校验用户名是否为空
        String username = staffDTO.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
//根据商户id和账号校验唯一性
        if (isExistStaffByUserName(username, staffDTO.getMerchantId())) {
            throw new BusinessException(CommonErrorCode.E_100114);
        }
        Staff entity = StaffConvert.INSTANCE.dto2entity(staffDTO);
        staffMapper.insert(entity);
        return StaffConvert.INSTANCE.entity2dto(entity);
    }

    /**
     * 根据手机号判断员工是否已在指定商户存在
     *
     * @param mobile 手机号
     * @return -
     */
    private boolean isExistStaffByMobile(String mobile, Long merchantId) {
        LambdaQueryWrapper<Staff> lambdaQueryWrapper = new LambdaQueryWrapper<Staff>();
        lambdaQueryWrapper.eq(Staff::getMobile, mobile).eq(Staff::getMerchantId, merchantId);
        int i = staffMapper.selectCount(lambdaQueryWrapper);
        return i > 0;
    }

    /**
     * 根据账号判断员工是否已在指定商户存在
     *
     * @param userName   -
     * @param merchantId -
     * @return -
     */
    private boolean isExistStaffByUserName(String userName, Long merchantId) {
        LambdaQueryWrapper<Staff> lambdaQueryWrapper = new LambdaQueryWrapper<Staff>();
        lambdaQueryWrapper.eq(Staff::getUsername, userName).eq(Staff::getMerchantId,
                merchantId);
        int i = staffMapper.selectCount(lambdaQueryWrapper);
        return i > 0;
    }

    /**
     * 给门店设置管理员
     *
     * @param storeId 门店id
     * @param staffId 员工id
     * @return void
     * @author glls
     * @date 2021/2/27 13:00
     */
    @Override
    public void bindStaffToStore(Long storeId, Long staffId) throws BusinessException {
        StoreStaff storeStaff = new StoreStaff();
        storeStaff.setStoreId(storeId);
        storeStaff.setStaffId(staffId);
        storeStaffMapper.insert(storeStaff);
    }

    @Override
    public MerchantDTO queryMerchantByTenantId(Long tenantId) {
        Merchant merchant = merchantMapper.selectOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        return MerchantConvert.INSTANCE.entity2Dto(merchant);
    }

    /**
     * 门店分页查询
     *
     * @param storeDTO
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<StoreDTO> queryStoreByPage(StoreDTO storeDTO, Integer pageNo, Integer pageSize) {
        // 创建分页
        Page<Store> page = new Page<>(pageNo, pageSize);
        // 构造查询条件
        QueryWrapper<Store> qw = new QueryWrapper<>();
        if (null != storeDTO && null != storeDTO.getMerchantId()) {

            qw.lambda().eq(Store::getMerchantId, storeDTO.getMerchantId());
        }
        // 执行查询
        IPage<Store> storeIPage = storeMapper.selectPage(page, qw);
        // entity List转DTO List
        List<StoreDTO> storeList = StoreConvert.INSTANCE.listentity2dto(storeIPage.getRecords());
        // 封装结果集
        return new PageVO<StoreDTO>(storeList, storeIPage.getTotal(), pageNo, pageSize);

    }

    @Override
    public Boolean queryStoreInMerchant(Long storeId, Long merchantId) {
        Integer count = storeMapper.selectCount(new LambdaQueryWrapper<Store>()
                .eq(Store::getId, storeId).eq(Store::getMerchantId, merchantId));
        return count > 0;
    }
}
