package ma.emsi.controle.exception;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global d'exceptions pour l'application.
 * Permet de centraliser la gestion des erreurs et de standardiser les réponses d'erreur.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Gère les exceptions EntityNotFoundException qui surviennent lorsqu'une entité n'est pas trouvée.
         *
         * @param ex L'exception levée
         * @return ResponseEntity avec le statut 404 et un message d'erreur
         */
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        /**
         * Gère les exceptions IllegalStateException qui surviennent pour des erreurs
         * liées à l'état des objets (par exemple, campagne non active).
         *
         * @param ex L'exception levée
         * @return ResponseEntity avec le statut 400 et un message d'erreur
         */
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        /**
         * Gère les exceptions de validation qui surviennent lorsque les données d'entrée
         * ne respectent pas les contraintes de validation spécifiées.
         *
         * @param ex L'exception de validation
         * @return ResponseEntity avec le statut 400 et une carte des erreurs par champ
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            // Parcours de toutes les erreurs de validation pour les ajouter à la carte
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        /**
         * Classe interne pour représenter une réponse d'erreur standardisée.
         */
        private static class ErrorResponse {
            private final int status;
            private final String message;
            private final LocalDateTime timestamp;

            public ErrorResponse(int status, String message, LocalDateTime timestamp) {
                this.status = status;
                this.message = message;
                this.timestamp = timestamp;
            }

            public int getStatus() {
                return status;
            }

            public String getMessage() {
                return message;
            }

            public LocalDateTime getTimestamp() {
                return timestamp;
            }
        }
}
