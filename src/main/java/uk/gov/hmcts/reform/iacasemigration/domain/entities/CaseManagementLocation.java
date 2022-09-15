package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.Value;

@Value
public class CaseManagementLocation {
    Region region;
    BaseLocation baseLocation;
}
