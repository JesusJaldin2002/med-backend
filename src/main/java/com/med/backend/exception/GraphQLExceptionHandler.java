package com.med.backend.exception;

import com.med.backend.dto.auth.ApiError;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLExceptionHandler.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setUrl(env.getExecutionStepInfo().getPath().toString());
        apiError.setMethod(env.getField().getName());

        if (ex instanceof AccessDeniedException) {
            apiError.setHttpCode(HttpStatus.FORBIDDEN.value());
            apiError.setMessage("Acceso denegado. No tienes los permisos necesarios para acceder a esta función.");
            apiError.setBackendMessage("Acceso denegado: " + ex.getMessage());
            logger.error("AccessDeniedException: {}", ex.getMessage());
        } else if (ex instanceof BadCredentialsException) {
            apiError.setHttpCode(HttpStatus.UNAUTHORIZED.value());
            apiError.setMessage("El nombre de usuario o la contraseña son incorrectos. Por favor, verifica tus credenciales.");
            apiError.setBackendMessage("Credenciales inválidas: " + ex.getMessage());
            logger.error("BadCredentialsException: {}", ex.getMessage());
        } else if (ex instanceof AccessDeniedException) {
            apiError.setHttpCode(HttpStatus.FORBIDDEN.value());
            apiError.setMessage("Acceso denegado. No tienes los permisos necesarios para acceder a esta función.");
            apiError.setBackendMessage("Acceso denegado: " + ex.getMessage());
            logger.error("AccessDeniedException: {}", ex.getMessage());
        } else if (ex instanceof AuthenticationException || ex instanceof AuthenticationCredentialsNotFoundException || ex instanceof AuthenticationServiceException) {
            apiError.setHttpCode(HttpStatus.UNAUTHORIZED.value());
            apiError.setMessage("Acceso no autorizado. Se requiere un token válido.");
            apiError.setBackendMessage("Acceso no autorizado: " + ex.getMessage());
            logger.error("UnauthorizedAccessException: {}", ex.getMessage());
        } else if (ex instanceof DuplicateResourceException) {
            DuplicateResourceException duplicateEx = (DuplicateResourceException) ex;
            apiError.setHttpCode(HttpStatus.CONFLICT.value());
            apiError.setMessage(duplicateEx.getFrontendMessage());  // Mensaje específico para el frontend
            apiError.setBackendMessage("Recurso duplicado: " + duplicateEx.getMessage());
            logger.error("DuplicateResourceException: {}", duplicateEx.getMessage());
        } else if (ex instanceof MethodArgumentNotValidException) {
            apiError.setHttpCode(HttpStatus.BAD_REQUEST.value());
            apiError.setMessage("Error en los datos enviados. Por favor, revisa y corrige los campos.");
            apiError.setBackendMessage("Argumento no válido: " + ex.getMessage());
            logger.error("MethodArgumentNotValidException: {}", ex.getMessage());
        } else if (ex instanceof ObjectNotFoundException) {
            apiError.setHttpCode(HttpStatus.NOT_FOUND.value());
            apiError.setMessage("El recurso solicitado no fue encontrado.");
            apiError.setBackendMessage("Recurso no encontrado: " + ex.getMessage());
            logger.error("ObjectNotFoundException: {}", ex.getMessage());
        } else {
            apiError.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiError.setMessage("Error interno en el servidor. Por favor, intenta más tarde.");
            apiError.setBackendMessage(ex.getMessage());
            logger.error("Unhandled Exception: {}", ex.getMessage(), ex);
        }

        return GraphqlErrorBuilder.newError(env)
                .message(apiError.getMessage()) // Mensaje amigable para el frontend
                .extensions(Map.of(
                        "code", resolveErrorCode(ex),
                        "status", apiError.getHttpCode(),
                        "detail", apiError // Pasa todo el objeto ApiError
                ))
                .build();
    }

    private String resolveErrorCode(Throwable ex) {
        if (ex instanceof AccessDeniedException) return "ACCESS_DENIED";
        if (ex instanceof AuthenticationException || ex instanceof AuthenticationCredentialsNotFoundException || ex instanceof AuthenticationServiceException)
            return "UNAUTHORIZED_ACCESS";
        if (ex instanceof DuplicateResourceException) return "DUPLICATE_RESOURCE";
        if (ex instanceof MethodArgumentNotValidException) return "INVALID_ARGUMENT";
        if (ex instanceof ObjectNotFoundException) return "OBJECT_NOT_FOUND";
        return "INTERNAL_ERROR";
    }
}

