package Enum;

public enum StatusCode
{
    SUCCESS(200,"OK"),
    CREATED(201,"A resource was successfully created"),
    DELETED(204,"A resource was successfully deleted"),
    BAD_REQUEST(400,"Please check request body missing field"),
    UNAUTHORIZED(401,"Invalid access token"),
    NOT_FOUND(404,"The requested resource does not exist"),
    INTERNAL_SERVER_ERROR(500,"Internal server error");

    public final int code;
    public final String message;

     StatusCode(int code , String message)
    {
        this.code = code;
        this.message = message;
    }
}
