package ink.breakpoint.easypay.common.cache.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "RestErrorResponse",description = "错误相应参数包装类")
@Data
public class RestErrorResponse {

    private String errCode;
    private String errMessage;

    public RestErrorResponse(String errCode, String errMessage){
        this.errCode = errCode;
        this.errMessage= errMessage;
    }
}
