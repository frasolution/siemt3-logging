package org.bob;

/**
 * @author Nicolas Roth, Rober Uwe Litschel
 *
 */
public class Apache2FailedLogMessage {
    private String message;

    public Apache2FailedLogMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
