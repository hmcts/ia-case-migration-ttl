package uk.gov.hmcts.reform.iacasemigration.domain;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String message) {
        super(message);
    }
}
