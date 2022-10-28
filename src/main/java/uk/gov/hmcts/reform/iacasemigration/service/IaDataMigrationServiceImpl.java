package uk.gov.hmcts.reform.iacasemigration.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.TTL;
import uk.gov.hmcts.reform.iacasemigration.migration.TimeToLiveMigration;
import uk.gov.hmcts.reform.idam.client.models.User;
import uk.gov.hmcts.reform.migration.service.DataMigrationService;

@Slf4j
@Service("dataMigrationService")
@RequiredArgsConstructor
public class IaDataMigrationServiceImpl implements DataMigrationService<Map<String, Object>> {

    private static final String JURISDICTION = "IA";
    private final TimeToLiveMigration timeToLiveMigration;

    @Override
    public Predicate<CaseDetails> accepts() {
        return caseDetails ->
            caseDetails != null
            && caseDetails.getData() != null
            && JURISDICTION.equalsIgnoreCase(caseDetails.getJurisdiction())
            && !caseDetails.getData().containsKey("TTL");
    }

    @Override
    public Map<String, Object> migrate(User user, CaseDetails caseDetails) {
        Optional<TTL> ttlOptional = timeToLiveMigration.calculateTTL(user, caseDetails);

        return ttlOptional.<Map<String, Object>>map(value -> Map.of("TTL", value))
            .orElse(Collections.emptyMap());
    }
}
