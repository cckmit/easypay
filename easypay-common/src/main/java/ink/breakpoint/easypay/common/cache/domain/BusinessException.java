package ink.breakpoint.easypay.common.cache.domain;

public class BusinessException extends RuntimeException{
    //错误代码
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(){
        super();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
