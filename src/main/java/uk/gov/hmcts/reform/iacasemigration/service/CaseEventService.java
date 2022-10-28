package uk.gov.hmcts.reform.iacasemigration.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CaseEventsApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.Event;
import uk.gov.hmcts.reform.idam.client.models.User;

@Service
public class CaseEventService {
    public static final String JURISDICTION_ID = "IA";
    public static final String CASE_TYPE_ID = "Asylum";

    private final CaseEventsApi caseEventsApi;

    private final AuthTokenGenerator authTokenGenerator;

    public CaseEventService(CaseEventsApi caseEventsApi,
                            AuthTokenGenerator authTokenGenerator) {
        this.caseEventsApi = caseEventsApi;
        this.authTokenGenerator = authTokenGenerator;
    }

    public List<Event> findEventsForCase(String ccdCaseId, User user) {
        List<Event> caseEventList = new ArrayList<>();

        List<CaseEventDetail> caseEventDetails = getEventDetailsForCase(ccdCaseId, user);

        caseEventDetails.sort(Comparator.comparing(CaseEventDetail::getCreatedDate).reversed());

        for (CaseEventDetail caseEventDetail : caseEventDetails) {
            Event event = Event.fromValue(caseEventDetail.getId());
            caseEventList.add(event);
        }

        return caseEventList;
    }

    public List<CaseEventDetail> getEventDetailsForCase(String ccdCaseId, User user) {
        return caseEventsApi.findEventDetailsForCase(user.getAuthToken(),
                                                     authTokenGenerator.generate(), user.getUserDetails().getId(),
                                                     JURISDICTION_ID,
                                                     CASE_TYPE_ID, ccdCaseId);
    }
}
