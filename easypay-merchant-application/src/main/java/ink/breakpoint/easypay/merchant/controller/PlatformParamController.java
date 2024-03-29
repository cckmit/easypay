package ink.breakpoint.easypay.merchant.controller;


import ink.breakpoint.easypay.common.cache.domain.BusinessException;
import ink.breakpoint.easypay.merchant.common.util.SecurityUtil;
import ink.breakpoint.easypay.transaction.api.PayChannelService;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.api.dto.PlatformChannelDTO;
import ink.breakpoint.easypay.transaction.api.dto.QrcodeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(value = "商户平台-渠道和支付参数相关", tags = "商户平台-渠道和支付参数", description = "商户平台-渠道和支付参数相关")
@Slf4j
@RestController
public class PlatformParamController {

    @Reference
    PayChannelService payChannelService;


    @ApiOperation("获取平台服务类型")
    @GetMapping(value = "/my/platform-channels")
    public List<PlatformChannelDTO> queryPlatformChannel() {
        return payChannelService.queryPlatformChannel();
    }

//    @ApiOperation("查询应用是否绑定了某个服务类型")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "appId", value = "应用appId", required = true, dataType =
//                    "String", paramType = "query"),
//            @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType =
//                    "String", paramType = "query")
//    })
////    @GetMapping("/my/merchants/apps/platformchannels")
////    public int queryAppBindPlatformChannel(@RequestParam String appId, @RequestParam String platformChannel) {
////        return payChannelService.queryAppBindPlatformChannel(appId, platformChannel);
////    }


    @ApiOperation("根据平台服务类型获取支付渠道列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platformChannelCode", value = "服务类型编码", required =
                    true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/my/pay-channels/platform-channel/{platformChannelCode}")
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(@PathVariable String platformChannelCode) {
        return payChannelService.queryPayChannelByPlatformChannel(platformChannelCode);
    }

    @ApiOperation("商户配置支付渠道参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payChannelParam", value = "商户配置支付渠道参数", required =
                    true, dataType = "PayChannelParamDTO", paramType = "body")
    })
    @RequestMapping(value = "/my/pay-channel-params", method =
            {RequestMethod.POST, RequestMethod.PUT})
    public void createPayChannelParam(@RequestBody PayChannelParamDTO payChannelParam) {
        Long merchantId = SecurityUtil.getMerchantId();
        payChannelParam.setMerchantId(merchantId);
        payChannelService.savePayChannelParam(payChannelParam);
    }


    @ApiOperation("获取指定应用指定服务类型下所包含的原始支付渠道参数列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType = "String", paramType = "path")})
    @GetMapping(value = "/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}")
    public List<PayChannelParamDTO> queryPayChannelParam(@PathVariable String appId, @PathVariable String platformChannel) {
        return payChannelService.queryPayChannelParamByAppAndPlatform(appId, platformChannel);
    }


    @ApiOperation("获取指定应用指定服务类型下所包含的某个原始支付参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "平台支付渠道编码", required = true,
                    dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "payChannel", value = "实际支付渠道编码", required = true,
                    dataType = "String", paramType = "path")})
    @GetMapping(value = "/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}/pay-channels/{payChannel}")
            public PayChannelParamDTO queryPayChannelParam(@PathVariable String appId,
            @PathVariable String platformChannel, @PathVariable String payChannel) {
            return payChannelService.queryParamByAppPlatformAndPayChannel(appId, platformChannel,
            payChannel);
}


}
