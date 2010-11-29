/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.application.servicefacade;

import java.util.List;

import org.joda.time.DateTime;
import org.mifos.dto.domain.ClientCreationDetail;
import org.mifos.dto.domain.ClientFamilyDetailsDto;
import org.mifos.dto.domain.ClientFamilyInfoUpdate;
import org.mifos.dto.domain.ClientMfiInfoUpdate;
import org.mifos.dto.domain.ClientPersonalInfoUpdate;
import org.mifos.dto.domain.ClientRulesDto;
import org.mifos.dto.domain.CustomerDetailsDto;
import org.mifos.dto.domain.MeetingDto;
import org.mifos.dto.domain.ProcessRulesDto;
import org.mifos.dto.domain.SavingsDetailDto;
import org.mifos.dto.screen.ClientFamilyInfoDto;
import org.mifos.dto.screen.ClientFormCreationDto;
import org.mifos.dto.screen.ClientInformationDto;
import org.mifos.dto.screen.ClientMfiInfoDto;
import org.mifos.dto.screen.ClientPersonalInfoDto;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ClientServiceFacade {

    @PreAuthorize("isFullyAuthenticated()")
    ClientFormCreationDto retrieveClientFormCreationData(Short groupFlag, Short officeId, String parentGroupId);

    @PreAuthorize("isFullyAuthenticated()")
    ClientFamilyDetailsDto retrieveClientFamilyDetails();

    @PreAuthorize("isFullyAuthenticated()")
    ProcessRulesDto previewClient(String governmentId, DateTime dateOfBirth, String clientName, boolean defaultFeesRemoval, Short officeId, Short loanOfficerId);

    @PreAuthorize("isFullyAuthenticated()")
    CustomerDetailsDto createNewClient(ClientCreationDetail clientCreationDetail, MeetingDto meeting, List<SavingsDetailDto> allowedSavingProducts);

    @PreAuthorize("isFullyAuthenticated()")
    ClientInformationDto getClientInformationDto(String globalCustNum);

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_PERSONAL_INFO')")
    ClientPersonalInfoDto retrieveClientPersonalInfoForUpdate(String clientSystemId);

    @PreAuthorize("isFullyAuthenticated()")
    ClientRulesDto retrieveClientDetailsForPreviewingEditOfPersonalInfo();

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_PERSONAL_INFO')")
    void updateClientPersonalInfo(ClientPersonalInfoUpdate personalInfo);

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_PERSONAL_INFO')")
    ClientFamilyInfoDto retrieveFamilyInfoForEdit(String globalCustNum);

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_PERSONAL_INFO')")
    void updateFamilyInfo(ClientFamilyInfoUpdate clientFamilyInfoUpdate);

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_MFI_INFO')")
    ClientMfiInfoDto retrieveMfiInfoForEdit(String clientSystemId);

    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_EDIT_CLIENT_MFI_INFO')")
    void updateClientMfiInfo(ClientMfiInfoUpdate clientMfiInfoUpdate);
}