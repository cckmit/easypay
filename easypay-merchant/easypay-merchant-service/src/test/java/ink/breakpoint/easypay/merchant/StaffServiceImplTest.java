package ink.breakpoint.easypay.merchant.service;


import ink.breakpoint.easypay.common.cache.domain.PageVO;
import ink.breakpoint.easypay.merchant.service.api.StaffService;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class StaffServiceImplTest {

    @Autowired
    StaffService staffService;

    @Test
    public void testQueryStaffByPage() {
        PageVO<StaffDTO> staffDTOS = staffService.queryStaffByPage(1449955397829902338L, 1, 10);
        for (StaffDTO item : staffDTOS.getItems()) {
            System.out.println(item);
        }

    }
}