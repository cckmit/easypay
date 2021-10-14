package ink.breakpoint.easypay.merchant.convert;


import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import ink.breakpoint.easypay.merchant.vo.MerchantDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MerchantDetailConvert {

    MerchantDetailConvert INSTANCE = Mappers.getMapper(MerchantDetailConvert.class);

    // vo转dto
    MerchantDTO vo2Dto(MerchantDetailVO merchantDetailVO);

    // dto转vo
    MerchantDetailVO dto2VO(MerchantDTO merchantDTO);

}
