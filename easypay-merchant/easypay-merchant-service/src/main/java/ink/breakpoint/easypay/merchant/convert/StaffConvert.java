package ink.breakpoint.easypay.merchant.convert;

import java.util.List;

import ink.breakpoint.easypay.merchant.entity.Staff;
import ink.breakpoint.easypay.merchant.service.api.dto.StaffDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StaffConvert {

    StaffConvert INSTANCE = Mappers.getMapper(StaffConvert.class);

    StaffDTO entity2dto(Staff entity);

    Staff dto2entity(StaffDTO dto);

    List<StaffDTO> listentity2dto(List<Staff> staff);

}