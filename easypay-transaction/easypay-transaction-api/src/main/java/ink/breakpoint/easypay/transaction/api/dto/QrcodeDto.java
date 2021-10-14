package ink.breakpoint.easypay.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrcodeDto implements Serializable {
    private String appId;
    private Long merchantId;
    private Long storeId;
    private String qrUrl; //地址
    private String subject;//店铺名
    private String body;//备注
}