package fr.pham.vinh.portail.api.exception;

import java.util.List;

/**
 * Exception response used when getting an error.
 * Created by Vinh PHAM on 07/04/2017.
 */
public class ExceptionResponse {

    private int httpStatus;

    private String reason;

    private List<String> errorMessages;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
