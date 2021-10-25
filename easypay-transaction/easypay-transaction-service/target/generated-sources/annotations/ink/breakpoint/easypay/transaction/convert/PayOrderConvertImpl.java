package ink.breakpoint.easypay.transaction.convert;

import ink.breakpoint.easypay.transaction.api.dto.OrderResultDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.entity.PayOrder;
import ink.breakpoint.easypay.transaction.vo.OrderConfirmVo;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-23T17:14:54+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class PayOrderConvertImpl implements PayOrderConvert {

    @Override
    public OrderResultDTO request2dto(PayOrderDTO payOrderDTO) {
        if ( payOrderDTO == null ) {
            return null;
        }

        OrderResultDTO orderResultDTO = new OrderResultDTO();

        orderResultDTO.setTradeNo( payOrderDTO.getTradeNo() );
        orderResultDTO.setMerchantId( payOrderDTO.getMerchantId() );
        orderResultDTO.setStoreId( payOrderDTO.getStoreId() );
        orderResultDTO.setAppId( payOrderDTO.getAppId() );
        orderResultDTO.setPayChannel( payOrderDTO.getPayChannel() );
        orderResultDTO.setChannel( payOrderDTO.getChannel() );
        orderResultDTO.setOutTradeNo( payOrderDTO.getOutTradeNo() );
        orderResultDTO.setSubject( payOrderDTO.getSubject() );
        orderResultDTO.setBody( payOrderDTO.getBody() );
        orderResultDTO.setCurrency( payOrderDTO.getCurrency() );
        orderResultDTO.setTotalAmount( payOrderDTO.getTotalAmount() );
        orderResultDTO.setOptional( payOrderDTO.getOptional() );
        orderResultDTO.setAnalysis( payOrderDTO.getAnalysis() );
        orderResultDTO.setExtra( payOrderDTO.getExtra() );
        orderResultDTO.setTradeState( payOrderDTO.getTradeState() );
        orderResultDTO.setDevice( payOrderDTO.getDevice() );
        orderResultDTO.setClientIp( payOrderDTO.getClientIp() );
        orderResultDTO.setCreateTime( payOrderDTO.getCreateTime() );
        orderResultDTO.setUpdateTime( payOrderDTO.getUpdateTime() );
        orderResultDTO.setExpireTime( payOrderDTO.getExpireTime() );
        orderResultDTO.setPaySuccessTime( payOrderDTO.getPaySuccessTime() );
        orderResultDTO.setOpenId( payOrderDTO.getOpenId() );
        orderResultDTO.setAuthCode( payOrderDTO.getAuthCode() );

        return orderResultDTO;
    }

    @Override
    public PayOrderDTO dto2request(OrderResultDTO OrderResult) {
        if ( OrderResult == null ) {
            return null;
        }

        PayOrderDTO payOrderDTO = new PayOrderDTO();

        payOrderDTO.setMerchantId( OrderResult.getMerchantId() );
        payOrderDTO.setStoreId( OrderResult.getStoreId() );
        payOrderDTO.setAppId( OrderResult.getAppId() );
        payOrderDTO.setChannel( OrderResult.getChannel() );
        payOrderDTO.setTradeNo( OrderResult.getTradeNo() );
        payOrderDTO.setOutTradeNo( OrderResult.getOutTradeNo() );
        payOrderDTO.setSubject( OrderResult.getSubject() );
        payOrderDTO.setBody( OrderResult.getBody() );
        payOrderDTO.setCurrency( OrderResult.getCurrency() );
        payOrderDTO.setTotalAmount( OrderResult.getTotalAmount() );
        payOrderDTO.setOptional( OrderResult.getOptional() );
        payOrderDTO.setAnalysis( OrderResult.getAnalysis() );
        payOrderDTO.setExtra( OrderResult.getExtra() );
        payOrderDTO.setOpenId( OrderResult.getOpenId() );
        payOrderDTO.setAuthCode( OrderResult.getAuthCode() );
        payOrderDTO.setDevice( OrderResult.getDevice() );
        payOrderDTO.setClientIp( OrderResult.getClientIp() );
        payOrderDTO.setPayChannel( OrderResult.getPayChannel() );
        payOrderDTO.setTradeState( OrderResult.getTradeState() );
        payOrderDTO.setCreateTime( OrderResult.getCreateTime() );
        payOrderDTO.setUpdateTime( OrderResult.getUpdateTime() );
        payOrderDTO.setExpireTime( OrderResult.getExpireTime() );
        payOrderDTO.setPaySuccessTime( OrderResult.getPaySuccessTime() );

        return payOrderDTO;
    }

    @Override
    public PayOrderDTO entity2dto(PayOrder entity) {
        if ( entity == null ) {
            return null;
        }

        PayOrderDTO payOrderDTO = new PayOrderDTO();

        payOrderDTO.setMerchantId( entity.getMerchantId() );
        payOrderDTO.setStoreId( entity.getStoreId() );
        payOrderDTO.setAppId( entity.getAppId() );
        payOrderDTO.setChannel( entity.getChannel() );
        payOrderDTO.setTradeNo( entity.getTradeNo() );
        payOrderDTO.setOutTradeNo( entity.getOutTradeNo() );
        payOrderDTO.setSubject( entity.getSubject() );
        payOrderDTO.setBody( entity.getBody() );
        payOrderDTO.setCurrency( entity.getCurrency() );
        payOrderDTO.setTotalAmount( entity.getTotalAmount() );
        payOrderDTO.setOptional( entity.getOptional() );
        payOrderDTO.setAnalysis( entity.getAnalysis() );
        payOrderDTO.setExtra( entity.getExtra() );
        payOrderDTO.setDevice( entity.getDevice() );
        payOrderDTO.setClientIp( entity.getClientIp() );
        payOrderDTO.setPayChannel( entity.getPayChannel() );
        payOrderDTO.setTradeState( entity.getTradeState() );
        payOrderDTO.setCreateTime( entity.getCreateTime() );
        payOrderDTO.setUpdateTime( entity.getUpdateTime() );
        payOrderDTO.setExpireTime( entity.getExpireTime() );
        payOrderDTO.setPaySuccessTime( entity.getPaySuccessTime() );

        return payOrderDTO;
    }

    @Override
    public PayOrder dto2entity(PayOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PayOrder payOrder = new PayOrder();

        payOrder.setTradeNo( dto.getTradeNo() );
        payOrder.setMerchantId( dto.getMerchantId() );
        payOrder.setStoreId( dto.getStoreId() );
        payOrder.setAppId( dto.getAppId() );
        payOrder.setPayChannel( dto.getPayChannel() );
        payOrder.setChannel( dto.getChannel() );
        payOrder.setOutTradeNo( dto.getOutTradeNo() );
        payOrder.setSubject( dto.getSubject() );
        payOrder.setBody( dto.getBody() );
        payOrder.setCurrency( dto.getCurrency() );
        payOrder.setTotalAmount( dto.getTotalAmount() );
        payOrder.setOptional( dto.getOptional() );
        payOrder.setAnalysis( dto.getAnalysis() );
        payOrder.setExtra( dto.getExtra() );
        payOrder.setTradeState( dto.getTradeState() );
        payOrder.setDevice( dto.getDevice() );
        payOrder.setClientIp( dto.getClientIp() );
        payOrder.setCreateTime( dto.getCreateTime() );
        payOrder.setUpdateTime( dto.getUpdateTime() );
        payOrder.setExpireTime( dto.getExpireTime() );
        payOrder.setPaySuccessTime( dto.getPaySuccessTime() );

        return payOrder;
    }

    @Override
    public PayOrderDTO vo2dto(OrderConfirmVo orderConfirmVo) {
        if ( orderConfirmVo == null ) {
            return null;
        }

        PayOrderDTO payOrderDTO = new PayOrderDTO();

        if ( orderConfirmVo.getStoreId() != null ) {
            payOrderDTO.setStoreId( Long.parseLong( orderConfirmVo.getStoreId() ) );
        }
        payOrderDTO.setAppId( orderConfirmVo.getAppId() );
        payOrderDTO.setChannel( orderConfirmVo.getChannel() );
        payOrderDTO.setTradeNo( orderConfirmVo.getTradeNo() );
        payOrderDTO.setSubject( orderConfirmVo.getSubject() );
        payOrderDTO.setBody( orderConfirmVo.getBody() );
        if ( orderConfirmVo.getTotalAmount() != null ) {
            payOrderDTO.setTotalAmount( Integer.parseInt( orderConfirmVo.getTotalAmount() ) );
        }
        payOrderDTO.setOpenId( orderConfirmVo.getOpenId() );

        return payOrderDTO;
    }
}
