package com.example.Venus.exception;

public class ImageProcessingException extends RuntimeException {
    private final String errorCode;
    private final String methodName;
    private final String serviceName;

    public ImageProcessingException(String message, String errorCode, String methodName, String serviceName, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.methodName = methodName;
        this.serviceName = serviceName;
    }

    public ImageProcessingException(String message, String errorCode, String methodName, String serviceName) {
        super(message);
        this.errorCode = errorCode;
        this.methodName = methodName;
        this.serviceName = serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return "ImageProcessingException{" +
                "message=" + getMessage() +
                ", errorCode='" + errorCode + '\'' +
                ", methodName='" + methodName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
