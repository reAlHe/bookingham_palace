package de.maibornwolff.ste.bookingham_palace.user.service.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.KEY_UNAUTHORIZED;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.MSG_UNAUTHORIZED;

public class UnauthorizedAlertException extends AbstractThrowableProblem {

    private final String entityName;

    private final String errorKey;


    public UnauthorizedAlertException(String defaultMessage, String entityName, String errorKey) {
        this(DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }


    public UnauthorizedAlertException(String entityName) {
        this(DEFAULT_TYPE, MSG_UNAUTHORIZED, entityName, KEY_UNAUTHORIZED);
    }


    private UnauthorizedAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.UNAUTHORIZED, null, null, null, getAlertParameters(entityName, errorKey));
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
