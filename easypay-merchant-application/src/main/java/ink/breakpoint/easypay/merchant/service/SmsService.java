package ink.breakpoint.easypay.merchant.service;


import ink.breakpoint.easypay.common.cache.domain.BusinessException;

public interface SmsService {

    String sendSms(String phone);

    /**
     * 校验验证码，抛出异常则校验无效
     * @param verifiyKey  验证key
     * @param verifiyCode 验证码
     */
    void checkVerifiyCode(String verifiyKey, String verifiyCode) throws BusinessException;

}
