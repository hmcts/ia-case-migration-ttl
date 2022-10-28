package uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.YesOrNo;

@Builder(toBuilder = true)
public class TTL {

    @JsonProperty("SystemTTL")
    private LocalDate systemTTL;
    @JsonProperty("OverrideTTL")
    private LocalDate overrideTTL;
    @JsonProperty("Suspended")
    private YesOrNo suspended;

}

