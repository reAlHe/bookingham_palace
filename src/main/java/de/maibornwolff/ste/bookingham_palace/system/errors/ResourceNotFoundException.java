package de.maibornwolff.ste.bookingham_palace.system.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Generic exception for HTTP status 404 to be thrown when a resource could not be found.
 */
public class ResourceNotFoundException extends AbstractThrowableProblem {

    private final String entityName;

    private final String errorKey;



    public ResourceNotFoundException(String defaultMessage, String entityName, String errorKey) {
        this(DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }


    public ResourceNotFoundException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.NOT_FOUND, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }


    public String getEntityName() {
        return entityName;
    }


    public String getErrorKey() {
        return errorKey;
    }


    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
