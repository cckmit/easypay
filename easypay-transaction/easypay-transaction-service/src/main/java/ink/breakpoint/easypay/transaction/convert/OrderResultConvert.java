package ink.breakpoint.easypay.transaction.convert;

import ink.breakpoint.easypay.transaction.api.dto.OrderResultDTO;
import ink.breakpoint.easypay.transaction.entity.PayOrder;
import org.apache.logging.log4j.Logger;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface OrderResultConvert {

    OrderResultConvert INSTANCE = Mappers.getMapper(OrderResultConvert.class);

    OrderResultDTO entity2dto(PayOrder entity);

    PayOrder dto2entity(OrderResultDTO dto);
}
