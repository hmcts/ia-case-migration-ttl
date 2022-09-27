package uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd;

import java.util.List;

public class CheckValues<T> {

    private List<T> values;

    private CheckValues() {
        // noop -- for deserializer
    }

    public CheckValues(List<T> values) {
        this.values = values;
    }

    public List<T> getValues() {
        return values;
    }
}
