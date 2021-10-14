package ink.breakpoint.easypay.merchant.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.common.cache.domain.CommonErrorCode;
import ink.breakpoint.easypay.common.cache.util.PhoneUtil;
import ink.breakpoint.easypay.merchant.common.util.SecurityUtil;
import ink.breakpoint.easypay.merchant.convert.MerchantDetailConvert;
import ink.breakpoint.easypay.merchant.convert.MerchantRegisterConvert;
import ink.breakpoint.easypay.merchant.service.SmsService;
import ink.breakpoint.easypay.merchant.service.api.MerchantService;
import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.vo.MerchantDetailVO;
import ink.breakpoint.easypay.merchant.vo.MerchantRegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(value = "商户平台应用接口",tags = "商户")
@RestController
public class MerchantController {

    @Reference // Dubbo注入，调用远程dubbo服务
    MerchantService merchantService;

    @Autowired // 注入本地接口，调用本地方法
    SmsService smsService;
    @Value("${oss.aliyun.bucket}")
    private String bucket;
    @Value("${oss.aliyun.domain}")
    private String domain;

    @Autowired
    OSS oss;


    @ApiOperation(value = "根据id查询商户",tags = "商户")
    @GetMapping("/merchants/{id}")
    MerchantDTO queryMerchantById(@PathVariable("id") Long id){
        return merchantService.queryMerchantById(id);
    }

    // 发送验证码
    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "phone",value = "手机号")
    @GetMapping("/sms")
    public String sendSms(String phone){
        return smsService.sendSms(phone);
    }

    // 商户注册
    @ApiOperation("商户注册")
    @PostMapping("/merchants/register")
    public MerchantRegisterVo merchantRegister(@RequestBody MerchantRegisterVo merchantRegisterVo){
        // 1.校验
        if (merchantRegisterVo == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //手机号非空校验
        if (StringUtils.isBlank(merchantRegisterVo.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200230);
        }
        //校验手机号的合法性
        if (!PhoneUtil.isMatches(merchantRegisterVo.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200224);
        }
        //联系人非空校验
        if (StringUtils.isBlank(merchantRegisterVo.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_200231);
        }
        //密码非空校验
        if (StringUtils.isBlank(merchantRegisterVo.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_200232);
        }
        //验证码非空校验
        if (StringUtils.isBlank(merchantRegisterVo.getVerifiyCode()) ||
                StringUtils.isBlank(merchantRegisterVo.getVerifiykey())) {
            throw new BusinessException(CommonErrorCode.E_100103);
        }
//        int i = 1 / 0;

        // 校验验证码 调验证码服务
        smsService.checkVerifiyCode(merchantRegisterVo.getVerifiykey(),merchantRegisterVo.getVerifiyCode());

        System.out.println(merchantRegisterVo);
        // 将vo转为dto
        MerchantDTO merchantDTO = MerchantRegisterConvert.INSTANCE.vo2Dto(merchantRegisterVo);
        System.out.println(merchantDTO);

//        MerchantDTO merchantDTO = new MerchantDTO();
//        merchantDTO.setUsername(merchantRegisterVo.getUsername());
//        merchantDTO.setMobile(merchantRegisterVo.getMobile());

        // 商户注册 调dubbo服务
        merchantService.createMerchant(merchantDTO);
        return MerchantRegisterConvert.INSTANCE.dto2VO(merchantDTO);
    }



    // 测试阿里云oss上传
    @ApiOperation("证件照上传")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile){
        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 获取上传文件的名称
            String filename = multipartFile.getOriginalFilename();
            // 截取文件扩展名
            String ext = filename.substring(filename.lastIndexOf("."));
            // 自定义文件名
            filename = System.currentTimeMillis() + ext;
            // 上传的请求对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, inputStream);
            // 上传
            oss.putObject(putObjectRequest);
            return domain + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("商户资质申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantDetailVO",value = "商户资质信息",required = true,dataType = "MerchantDetailVO",paramType = "body")
    })
    @PostMapping("/my/merchants/save")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantDetailVO){
        Long merchantId = SecurityUtil.getMerchantId();
        // 将vo转为dto
        MerchantDTO merchantDTO = MerchantDetailConvert.INSTANCE.vo2Dto(merchantDetailVO);
        // 商户id，dto对象
        merchantService.applyMerchant(merchantId,merchantDTO);
    }

    /*@ApiOperation(value = "hello")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @ApiOperation("hi")
    @PostMapping("/hi")
    public String hi(String name){
        return "hi " + name;
    }*/

    @GetMapping("/my/merchants")
    public MerchantDTO getMerchantByTenantId(@RequestParam Long tenantId) {
        return merchantService.queryMerchantByTenantId(tenantId);
    }

}
