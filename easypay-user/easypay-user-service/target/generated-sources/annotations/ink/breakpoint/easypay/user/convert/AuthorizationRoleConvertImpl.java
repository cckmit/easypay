package ink.breakpoint.easypay.user.convert;

import ink.breakpoint.easypay.user.api.dto.authorization.RoleDTO;
import ink.breakpoint.easypay.user.entity.AuthorizationRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-23T17:14:50+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class AuthorizationRoleConvertImpl implements AuthorizationRoleConvert {

    @Override
    public RoleDTO entity2dto(AuthorizationRole entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setName( entity.getName() );
        roleDTO.setTenantId( entity.getTenantId() );
        roleDTO.setCode( entity.getCode() );
        roleDTO.setId( entity.getId() );

        return roleDTO;
    }

    @Override
    public AuthorizationRole dto2entity(RoleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AuthorizationRole authorizationRole = new AuthorizationRole();

        authorizationRole.setName( dto.getName() );
        authorizationRole.setTenantId( dto.getTenantId() );
        authorizationRole.setCode( dto.getCode() );
        authorizationRole.setId( dto.getId() );

        return authorizationRole;
    }

    @Override
    public List<RoleDTO> entitylist2dto(List<AuthorizationRole> authorizationRole) {
        if ( authorizationRole == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( authorizationRole.size() );
        for ( AuthorizationRole authorizationRole1 : authorizationRole ) {
            list.add( entity2dto( authorizationRole1 ) );
        }

        return list;
    }

    @Override
    public List<AuthorizationRole> dtolist2entity(List<RoleDTO> roleDTOS) {
        if ( roleDTOS == null ) {
            return null;
        }

        List<AuthorizationRole> list = new ArrayList<AuthorizationRole>( roleDTOS.size() );
        for ( RoleDTO roleDTO : roleDTOS ) {
            list.add( dto2entity( roleDTO ) );
        }

        return list;
    }
}
