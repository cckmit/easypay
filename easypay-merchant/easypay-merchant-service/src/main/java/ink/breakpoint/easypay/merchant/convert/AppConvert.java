package ink.breakpoint.easypay.merchant.convert;

import ink.breakpoint.easypay.merchant.entity.App;
import ink.breakpoint.easypay.merchant.service.api.dto.AppDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppConvert {
    AppConvert INSTANCE = Mappers.getMapper(AppConvert.class);

    AppDTO entity2dto(App entity);

    App dto2entity(AppDTO dto);

    List<AppDTO> listEntity2dto(List<App> app);
}
