package ink.breakpoint.easypay.merchant.convert;

import ink.breakpoint.easypay.merchant.entity.Merchant;
import ink.breakpoint.easypay.merchant.service.api.dto.MerchantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MerchantConvert {

    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    // 将entity转为dto
    MerchantDTO entity2Dto(Merchant merchant);

    // 将dto转为entity
    Merchant dto2Entity(MerchantDTO merchantDTO);

    // 将entity的集合转为dto的集合
    List<MerchantDTO> entityList2Dto(List<Merchant> merchantList);


///    public static void main(String[] args) {
//        // 测试entity2Dto
//        Merchant merchant = new Merchant();
//        merchant.setMerchantName("美团");
//        merchant.setMobile("15025689547");
//        // 测试dto2Entity
////        MerchantDTO merchantDTO = MerchantCovert.INSTANCE.entity2Dto(merchant);
////        System.out.println(merchantDTO);
////
////        Merchant m1 = MerchantCovert.INSTANCE.dto2Entity(merchantDTO);
////        System.out.println(m1);
//
//        // 测试entity的集合转为dto的集合
//        List<Merchant> merchantList = new ArrayList<>();
//        merchantList.add(merchant);
//        List<MerchantDTO> dtoList = MerchantConvert.INSTANCE.entityList2Dto(merchantList);
//        System.out.println(dtoList);
//    }
}
