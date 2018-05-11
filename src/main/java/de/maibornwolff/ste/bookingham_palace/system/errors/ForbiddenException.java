package de.maibornwolff.ste.bookingham_palace.system.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import static de.maibornwolff.ste.bookingham_palace.system.errors.ErrorConstants.KEY_FORBIDDEN;
import static de.maibornwolff.ste.bookingham_palace.system.errors.ErrorConstants.MSG_FORBIDDEN;

/**
 * Generic exception for HTTP status 403 when the access to a resource is forbidden.
 */
public class ForbiddenException extends AbstractThrowableProblem {

    private final String entityName;

    private final String errorKey;


    public ForbiddenException(String entityName ) {
        this(DEFAULT_TYPE, MSG_FORBIDDEN, entityName, KEY_FORBIDDEN);
    }


    public ForbiddenException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.FORBIDDEN, null, null, null, getAlertParameters(entityName, errorKey));
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
