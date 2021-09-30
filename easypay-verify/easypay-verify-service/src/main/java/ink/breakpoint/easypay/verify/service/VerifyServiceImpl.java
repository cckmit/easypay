package ink.breakpoint.easypay.verify.service;

import ink.breakpoint.easypay.verify.service.api.VerifyService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class VerifyServiceImpl implements VerifyService {
    @Override
    public String sendVerifyCode(String phone) {
        return null;
    }

    @Override
    public void checkVerifyCode(String verifyKey, String verifyCode) {

    }
}
