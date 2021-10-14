package ink.breakpoint.easypay.transaction.convert;

import ink.breakpoint.easypay.transaction.api.dto.PlatformChannelDTO;
import ink.breakpoint.easypay.transaction.entity.PlatformChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlatformChannelConvert {

    PlatformChannelConvert INSTANCE = Mappers.getMapper(PlatformChannelConvert.class);

    PlatformChannelDTO entity2dto(PlatformChannel entity);

    PlatformChannel dto2entity(PlatformChannelDTO dto);

    List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel);
}
