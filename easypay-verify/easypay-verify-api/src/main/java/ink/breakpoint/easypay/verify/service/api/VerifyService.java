package ink.breakpoint.easypay.verify.service.api;

public interface VerifyService {
    String sendVerifyCode(String phone);

    void checkVerifyCode(String verifyKey,String verifyCode);
}
