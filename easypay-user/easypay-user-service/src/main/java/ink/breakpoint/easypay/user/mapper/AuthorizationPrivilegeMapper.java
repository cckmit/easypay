package ink.breakpoint.easypay.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.breakpoint.easypay.user.api.dto.tenant.TenRolePrivilegeDTO;
import ink.breakpoint.easypay.user.entity.AuthorizationPrivilege;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 *
 * @since 2019-08-13
 */
@Repository
public interface AuthorizationPrivilegeMapper extends BaseMapper<AuthorizationPrivilege> {

    @Select("<script>" +
            "SELECT  p.* FROM authorization_privilege p \n" +
            "\tLEFT JOIN authorization_role_privilege arp ON arp.PRIVILEGE_ID=p.ID\n" +
            "\tLEFT JOIN authorization_role r ON arp.ROLE_ID=r.ID\n" +
            "\tWHERE r.ID IN <foreach collection='roleIds' item='item' open='(' separator=',' close=')'>#{item}</foreach> " +
            "</script>")
    List<AuthorizationPrivilege> selectPrivilegeByRole(@Param("roleIds") List<Long> roleIds);

    @Select("<script>" +
            "select r.TENANT_ID,r.`CODE` ROLE_CODE,p.`CODE` PRIVILEGE_CODE from authorization_privilege p " +
            "LEFT JOIN authorization_role_privilege arp ON arp.PRIVILEGE_ID=p.ID " +
            "LEFT JOIN authorization_role r ON arp.ROLE_ID = r.ID " +
            "where r.ID IN <foreach collection='roleIds' item='item' open='(' separator=',' close=')'>#{item}</foreach> " +
            "ORDER BY r.TENANT_ID " +
            "</script>")
     List<TenRolePrivilegeDTO> selectPrivilegeRoleInTenant(@Param("roleIds") List<Long> roleIds);

}
