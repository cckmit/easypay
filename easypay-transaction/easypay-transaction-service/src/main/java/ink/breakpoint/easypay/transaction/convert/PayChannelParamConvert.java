package ink.breakpoint.easypay.transaction.convert;

import ink.breakpoint.easypay.transaction.api.dto.PayChannelParamDTO;
import ink.breakpoint.easypay.transaction.entity.PayChannelParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PayChannelParamConvert {

    PayChannelParamConvert INSTANCE= Mappers.getMapper(PayChannelParamConvert.class);

    PayChannelParamDTO entity2dto(PayChannelParam entity);

    PayChannelParam dto2entity(PayChannelParamDTO dto);

    List<PayChannelParamDTO> listentity2listdto(List<PayChannelParam> PlatformChannel);

    List<PayChannelParam> listdto2listentity(List<PayChannelParamDTO> PlatformChannelDTO);
}
