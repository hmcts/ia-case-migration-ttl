package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.NonNull;
import lombok.Value;

@Value
public class CaseFlag {

    @NonNull
    CaseFlagType caseFlagType;
    @NonNull
    String caseFlagAdditionalInformation;

}
