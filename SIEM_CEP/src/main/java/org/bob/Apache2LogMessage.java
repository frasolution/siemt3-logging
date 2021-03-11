package org.bob;

/**
 * @author Nicolas Roth, Robert Uwe Litschel
 *
 */

public class Apache2LogMessage {
    private String message;

    public Apache2LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
