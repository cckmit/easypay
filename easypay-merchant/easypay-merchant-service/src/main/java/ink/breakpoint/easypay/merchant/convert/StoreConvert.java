package ink.breakpoint.easypay.merchant.convert;

import ink.breakpoint.easypay.merchant.entity.Store;
import ink.breakpoint.easypay.merchant.service.api.dto.StoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StoreConvert {
    StoreConvert INSTANCE = Mappers.getMapper(StoreConvert.class);

    StoreDTO entity2DTO(Store entity);

    Store DTO2entity(StoreDTO DTO);

    List<StoreDTO> listentity2dto(List<Store> list);


}
