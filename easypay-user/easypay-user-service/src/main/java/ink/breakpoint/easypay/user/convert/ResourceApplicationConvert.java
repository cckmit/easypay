package ink.breakpoint.easypay.user.convert;


import ink.breakpoint.easypay.user.api.dto.resource.ApplicationDTO;
import ink.breakpoint.easypay.user.entity.ResourceApplication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ResourceApplicationConvert {


    ResourceApplicationConvert INSTANCE = Mappers.getMapper(ResourceApplicationConvert.class);

    ApplicationDTO entity2dto(ResourceApplication entity);

    ResourceApplication dto2entity(ApplicationDTO dto);

    List<ApplicationDTO> entitylist2dto(List<ResourceApplication> bundle);
}
