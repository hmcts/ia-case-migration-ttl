package uk.gov.hmcts.reform.iacasemigration.domain;

import uk.gov.hmcts.reform.iacasemigration.domain.entities.UserDetails;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.UserRole;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.UserRoleLabel;

public interface UserDetailsHelper {

    UserRole getLoggedInUserRole(UserDetails userDetails);

    UserRoleLabel getLoggedInUserRoleLabel(UserDetails userDetails);

}
