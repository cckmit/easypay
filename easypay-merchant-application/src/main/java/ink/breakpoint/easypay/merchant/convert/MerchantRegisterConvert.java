package ink.breakpoint.easypay.merchant.convert;


import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.vo.MerchantRegisterVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper // 对象属性映射
public interface MerchantRegisterConvert {

    MerchantRegisterConvert INSTANCE = Mappers.getMapper(MerchantRegisterConvert.class);

    // 将vo转为dto
    MerchantDTO vo2Dto(MerchantRegisterVo merchantRegisterVo);


    // 将dto转为vo
    MerchantRegisterVo dto2VO(MerchantDTO merchantDTO);

}
