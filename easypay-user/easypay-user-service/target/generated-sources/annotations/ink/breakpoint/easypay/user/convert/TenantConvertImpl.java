package ink.breakpoint.easypay.user.convert;

import ink.breakpoint.easypay.user.api.dto.tenant.TenantDTO;
import ink.breakpoint.easypay.user.entity.Tenant;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-23T17:14:50+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class TenantConvertImpl implements TenantConvert {

    @Override
    public TenantDTO entity2dto(Tenant entity) {
        if ( entity == null ) {
            return null;
        }

        TenantDTO tenantDTO = new TenantDTO();

        tenantDTO.setId( entity.getId() );
        tenantDTO.setName( entity.getName() );
        tenantDTO.setTenantTypeCode( entity.getTenantTypeCode() );
        tenantDTO.setBundleCode( entity.getBundleCode() );

        return tenantDTO;
    }

    @Override
    public Tenant dto2entity(TenantDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tenant tenant = new Tenant();

        tenant.setId( dto.getId() );
        tenant.setName( dto.getName() );
        tenant.setTenantTypeCode( dto.getTenantTypeCode() );
        tenant.setBundleCode( dto.getBundleCode() );

        return tenant;
    }
}
