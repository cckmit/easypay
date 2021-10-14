package ink.breakpoint.easypay.user.convert;

import ink.breakpoint.easypay.user.api.dto.tenant.TenantDTO;
import ink.breakpoint.easypay.user.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    TenantDTO entity2dto(Tenant entity);

    Tenant dto2entity(TenantDTO dto);
}
