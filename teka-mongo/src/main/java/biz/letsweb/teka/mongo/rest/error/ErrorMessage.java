package biz.letsweb.teka.mongo.rest.error;

/**
 *
 * @author tomasz
 */
public class ErrorMessage {
    private String name = "Default error name";
    private String status = "Default error value";
    private String message = "No message available.";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
