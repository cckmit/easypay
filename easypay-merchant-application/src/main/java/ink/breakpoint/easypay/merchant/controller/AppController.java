package ink.breakpoint.easypay.merchant.controller;

import ink.breakpoint.easypay.merchant.common.util.SecurityUtil;
import ink.breakpoint.easypay.merchant.service.api.AppService;
import ink.breakpoint.easypay.merchant.service.api.dto.AppDTO;
import ink.breakpoint.easypay.transaction.api.PayChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商户平台-应用管理", tags = "应用")
@RestController
public class AppController {

    @Reference
    AppService appService;

    @Reference
    PayChannelService payChannelService;

    @ApiOperation("创建应用")
    @ApiImplicitParam(name = "appDTO", value = "应用信息", required = true, dataType = "AppDTO", paramType = "body")
    @PostMapping("/my/apps")
    public AppDTO createApp(@RequestBody AppDTO appDTO) {
        Long merchantId = SecurityUtil.getMerchantId();
        appDTO.setMerchantId(merchantId);
        return appService.createApp(merchantId, appDTO);
    }

    @ApiOperation("编辑修改应用")
    @ApiImplicitParam(name = "appDTO", value = "应用信息", required = true, dataType = "AppDTO", paramType = "body")
    @PutMapping("/my/apps")
    public AppDTO updateApp(@RequestBody AppDTO appDTO) {
        Long merchantId = SecurityUtil.getMerchantId();
        appDTO.setMerchantId(merchantId);
        return appService.updateApp(appDTO);
//        return appService.createApp(merchantId, appDTO);
    }

    // 根据商户id查询应用列表
    @ApiOperation("根据商户id查询应用列表")
    @GetMapping("/my/apps")
    public List<AppDTO> queryAppsByMerchantId() {
        Long merchantId = SecurityUtil.getMerchantId();
        return appService.queryAppByMerchant(merchantId);
    }

    @ApiOperation("根据appid查询app信息")
    @GetMapping("/my/apps/{appid}")
    public AppDTO getAPP(@PathVariable String appid) {
        return appService.getAppById(appid);
    }

    // 根据appId查询应用信息
    @ApiOperation("根据appId查询应用信息")
    @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/my/apps/{appId}")
    public AppDTO getApp(@PathVariable String appId) {
        return appService.getAppById(appId);
    }

    @ApiOperation("绑定服务类型")
    @PostMapping(value = "/my/apps/{appId}/platform-channels")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "应用id", name = "appId", dataType = "string", paramType = "path"),
            @ApiImplicitParam(value = "服务类型code", name = "platformChannelCode", dataType = "string", paramType = "query")
    })
    public void bindPlatformForApp(@PathVariable String appId, String platformChannelCodes) {
        payChannelService.bindPlatformChannelForApp(appId, platformChannelCodes);
    }

    @ApiOperation("查询应用是否绑定了某个服务类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用appId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/my/merchants/apps/platformchannels")
    public int queryAppBindPlatformChannel(@RequestParam String appId, @RequestParam String platformChannel) {
        return payChannelService.queryAppBindPlatformChannel(appId, platformChannel);
    }

}
