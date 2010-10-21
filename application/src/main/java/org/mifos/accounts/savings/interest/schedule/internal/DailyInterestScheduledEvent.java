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

package org.mifos.accounts.savings.interest.schedule.internal;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifos.accounts.savings.interest.schedule.InterestScheduledEvent;

/**
 * I represent an {@link InterestScheduledEvent} that occurs every number of days.
 */
public class DailyInterestScheduledEvent implements InterestScheduledEvent {

    private final int every;

    public DailyInterestScheduledEvent(int every) {
        this.every = every;
    }

    @Override
    public LocalDate nextMatchingDateFromAlreadyMatchingDate(LocalDate validMatchingDate) {
        return validMatchingDate.plusDays(every);
    }

    @Override
    public List<LocalDate> findAllMatchingDatesFromBaseDateToCutOffDate(LocalDate baseDate, LocalDate cutOffDate) {

        List<LocalDate> allMatchingDates = new ArrayList<LocalDate>();

        LocalDate previousMatchingDate = baseDate.minusDays(1);
        LocalDate matchingDate = nextMatchingDateFromAlreadyMatchingDate(previousMatchingDate);
        while (matchingDate.isEqual(cutOffDate) || matchingDate.isBefore(cutOffDate)) {
            allMatchingDates.add(matchingDate);
            matchingDate = nextMatchingDateFromAlreadyMatchingDate(matchingDate);
        }

        return allMatchingDates;
    }

    @Override
    public LocalDate findFirstDateOfPeriodForMatchingDate(LocalDate matchingDate) {
        LocalDate previousMatchingDate;
        if (every > 1) {
            previousMatchingDate = matchingDate.minusDays(every-1);
        } else {
            previousMatchingDate = matchingDate;
        }

        return previousMatchingDate;
    }

    @Override
    public boolean isAMatchingDate(LocalDate baseDate, LocalDate date) {
        List<LocalDate> allMatchingDates = findAllMatchingDatesFromBaseDateToCutOffDate(baseDate, date);
        return allMatchingDates.contains(date);
    }

    @Override
    public LocalDate nextMatchingDateAfter(LocalDate baseDate, LocalDate after) {
        LocalDate cutOff = after.plusMonths(every+1);
        List<LocalDate> allMatchingDates = findAllMatchingDatesFromBaseDateToCutOffDate(baseDate, cutOff);

        return findNextMatchingDateFromList(after, allMatchingDates);
    }

    private LocalDate findNextMatchingDateFromList(LocalDate after, List<LocalDate> allMatchingDates) {
        LocalDate nextMatchingDate = after;
        for (LocalDate matchingDate : allMatchingDates) {
            if (matchingDate.isAfter(after)) {
                nextMatchingDate = matchingDate;
                break;
            }
        }
        return nextMatchingDate;
    }
}