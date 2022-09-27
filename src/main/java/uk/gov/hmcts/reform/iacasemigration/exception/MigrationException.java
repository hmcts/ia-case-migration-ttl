package uk.gov.hmcts.reform.iacasemigration.exception;

public class MigrationException extends RuntimeException {
    public MigrationException(String message) {
        super(message);
    }
}
