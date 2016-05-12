package biz.letsweb.teka.mongo.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author tomasz
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends Exception{

    public ContentNotFoundException(String message) {
        super(message);
    }
    
}
