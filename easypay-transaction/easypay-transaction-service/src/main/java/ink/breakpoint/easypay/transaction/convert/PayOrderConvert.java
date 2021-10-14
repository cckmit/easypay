package ink.breakpoint.easypay.transaction.convert;

import ink.breakpoint.easypay.transaction.api.dto.OrderResultDTO;
import ink.breakpoint.easypay.transaction.api.dto.PayOrderDTO;
import ink.breakpoint.easypay.transaction.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    OrderResultDTO request2dto(PayOrderDTO payOrderDTO);

    PayOrderDTO dto2request(OrderResultDTO OrderResult);

    OrderResultDTO entity2dto(PayOrder entity);

    PayOrder dto2entity(OrderResultDTO dto);

}
