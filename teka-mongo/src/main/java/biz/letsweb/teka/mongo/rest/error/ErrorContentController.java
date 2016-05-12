package biz.letsweb.teka.mongo.rest.error;

import static biz.letsweb.teka.mongo.rest.error.ErrorContentController.ERROR_PATH;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author tomasz
 */
@RestController
@RequestMapping(ERROR_PATH)
public class ErrorContentController implements ErrorController {

    public static final String STATUS_CODE_PROPERTY = "javax.servlet.error.status_code";
    public static final String ERROR_PATH = "/error";
    public static final Logger LOG = LoggerFactory.getLogger(ErrorContentController.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping
    public ResponseEntity<ErrorMessage> error(final HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, hasStackTrace(request));
        final HttpStatus status = getStatus(request);
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setName(status.name());
        errorMessage.setStatus(status.toString());
        errorMessage.setMessage(String.format("%s %s", getValidationErrorMessage(body), getGenericErrorMessage(body)).trim());
        return new ResponseEntity<>(errorMessage, status);
    }



    private String getGenericErrorMessage(Map<String, Object> errorattributes){
        final Object errorInfo = errorattributes.get("message");
        String errorMessage = "";
        if (errorInfo != null) {
            errorMessage = errorInfo.toString();
        }
        return errorMessage;
    }
    
    private String getValidationErrorMessage(Map<String, Object> errorAttributes) {
        Collection<Object> objectsWithError = retriveObjectsFromErrorAttributes(errorAttributes);
        System.out.println(objectsWithError);
        final Collection<ObjectError> objectErrors = retriveObjectErrorsFromObjectsInErrorAttributes(objectsWithError);
        final StringBuilder stringBuilder = new StringBuilder();
        objectErrors.stream()
                .forEach(objectError -> stringBuilder.append(objectError.getDefaultMessage()));
        return stringBuilder.toString();
    }

    private Collection<Object> retriveObjectsFromErrorAttributes(Map<String, Object> errorAttributes) {
        Collection<Object> errorList = Collections.emptyList();
        final Object errors = errorAttributes.get("errors");
        if (errors != null && (errors instanceof Collection)) {
            try {
                errorList = (Collection) errors;
            } catch (Exception e) {
                LOG.warn("Can not retrive validation errors. {}", e.getMessage());
            }
        }
        return errorList;
    }

    private Collection<ObjectError> retriveObjectErrorsFromObjectsInErrorAttributes(Collection<Object> objectsFromErrorAttributes) {
        return objectsFromErrorAttributes.stream()
                .filter(item -> item != null && item instanceof DefaultMessageSourceResolvable)
                .map(item -> (ObjectError) item)
                .collect(toList());
    }

    private HttpStatus getStatus(final HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute(STATUS_CODE_PROPERTY);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private boolean hasStackTrace(HttpServletRequest request) {
        final String parameter = request.getParameter("trace");
        boolean hasStackTrace = false;
        if (parameter != null && !parameter.toUpperCase().equals(Boolean.FALSE.toString())) {
            hasStackTrace = true;
        }
        return hasStackTrace;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean hasStackTrace) {
        final RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        final Map<String, Object> attributes = errorAttributes.getErrorAttributes(requestAttributes, hasStackTrace);
        return attributes;
    }
}
