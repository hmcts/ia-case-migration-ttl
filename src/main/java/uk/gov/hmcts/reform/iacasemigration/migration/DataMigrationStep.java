package uk.gov.hmcts.reform.iacasemigration.migration;

import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;

public interface DataMigrationStep {

    void apply(AsylumCase asylumCase, Long id);
}
