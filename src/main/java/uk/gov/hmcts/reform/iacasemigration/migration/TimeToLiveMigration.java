package uk.gov.hmcts.reform.iacasemigration.migration;

import com.microsoft.applicationinsights.core.dependencies.google.common.collect.Iterables;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.ccd.client.CaseEventsApi;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.Event;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.TTL;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.YesOrNo;
import uk.gov.hmcts.reform.iacasemigration.service.CaseEventService;
import uk.gov.hmcts.reform.idam.client.models.User;

@Service
@Slf4j
public class TimeToLiveMigration {

    private final CaseEventService caseEventService;
    private static final int TWO_YEARS = 730;
    private static final Set<Event> eventsThatTriggerTtlClock = Set.of(Event.SEND_DECISION_AND_REASONS,
                                                                       Event.END_APPEAL,
                                                                       Event.REMOVE_APPEAL_FROM_ONLINE);

    public TimeToLiveMigration(CaseEventService caseEventService) {
        this.caseEventService = caseEventService;
    }

    public Optional<TTL> calculateTTL(User user, CaseDetails caseDetails) {
        String ccdCaseId = String.valueOf(caseDetails.getId());
        List<CaseEventDetail> caseEventDetails = caseEventService.getEventDetailsForCase(ccdCaseId, user);

        if (caseEventDetails.size() > 0) {
            caseEventDetails.sort(Comparator.comparing(CaseEventDetail::getCreatedDate).reversed());
            log.info("Case events {}", caseEventDetails);
            var lastEvent = Iterables.getLast(caseEventDetails);
            LocalDate lastEventDate = lastEvent.getCreatedDate().toLocalDate();
            Event lastEventName = Event.fromValue(lastEvent.getId());
            LocalDate lastEventCreatedDate = caseDetails.getCreatedDate().toLocalDate();
            log.info("Case created date is {}", lastEventCreatedDate);
            log.info("Case last event is {}", lastEventName);

            return eventsThatTriggerTtlClock.contains(lastEventName)
                ? Optional.of(TTL.builder()
                                  .systemTTL(lastEventCreatedDate.plusDays(TWO_YEARS))
                                  .suspended(YesOrNo.NO)
                                  .build())
                : Optional.empty();
        }

        return Optional.empty();
    }
}
