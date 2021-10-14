package ink.breakpoint.easypay.transaction;

import ink.breakpoint.easypay.transaction.api.PayChannelService;
import ink.breakpoint.easypay.transaction.api.dto.PayChannelDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 /* @author Administrator
 /* @version qawine
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestPayChannelService {

    @Autowired
    PayChannelService payChannelService;

    //测试根据服务类型查询支付渠道
    @Test
    public void testqueryPayChannelByPlatformChannel(){
        List<PayChannelDTO> huimin_c2b =
                payChannelService.queryPayChannelByPlatformChannel("huimin_b2c");
        System.out.println(huimin_c2b);

    }
}




