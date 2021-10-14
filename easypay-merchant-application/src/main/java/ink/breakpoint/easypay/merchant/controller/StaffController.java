package ink.breakpoint.easypay.merchant.controller;

import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.merchant.common.util.SecurityUtil;
import ink.breakpoint.easypay.merchant.service.api.StaffService;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "员工操作相关")
public class StaffController {
    @Reference
    StaffService staffService;


    @ApiOperation("添加成员")
    @PostMapping("/staffs/add")
    public Object add(@RequestBody StaffDTO staffDTO) {
        staffService.addStaff(staffDTO);
        return null;
    }

    @ApiOperation("编辑成员")
    @PostMapping("/staffs/update")
    public Object update(@RequestBody StaffDTO staffDTO) {
        staffService.updateStaff(staffDTO);
        return null;
    }


    @ApiOperation("删除成员")
    @PostMapping("/staffs/delate")
    public Object delate(Long id) {
        staffService.delateStaff(id);
        return null;
    }

    @ApiOperation("分页查询成员信息")
    @PostMapping("/my/staffs/page")
    public PageVO<StaffDTO> queryStaffByPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Long merchantId = SecurityUtil.getMerchantId();
        return staffService.queryStaffByPage(merchantId, pageNo, pageSize);
    }

}

//No provider available in
// [invoker :interface
// ink.breakpoint.easypay.merchant.service.api.StaffService ->
// spring-cloud://localhost:9090/org.apache.dubbo.registry.RegistryService?application=merchant-application&check=false&dubbo=2.0.2&interface=ink.breakpoint.easypay.merchant.service.api.StaffService&lazy=false&methods=addStaff,queryStaffByPage,delateStaff,updateStaff&pid=13696&qos.enable=false&qos.port=22310&register.ip=172.16.42.102&release=2.7.3&retries=-1&side=consumer&sticky=false&timeout=3000&timestamp=1634634609537,directory: org.apache.dubbo.registry.integration.RegistryDirectory@8e8ceb3, invoker :interface ink.breakpoint.easypay.merchant.service.api.StaffService -> nacos://localhost:8848/org.apache.dubbo.registry.RegistryService?application=merchant-application&check=false&dubbo=2.0.2&interface=ink.breakpoint.easypay.merchant.service.api.StaffService&lazy=false&methods=addStaff,queryStaffByPage,delateStaff,updateStaff&pid=13696&qos.enable=false&qos.port=22310&register.ip=172.16.42.102&release=2.7.3&retries=-1&side=consumer&sticky=false&timeout=3000&timestamp=1634634609537,directory: org.apache.dubbo.registry.integration.RegistryDirectory@6c2f8ecb]