package com.pavbatol.talentium.internship.model.filter;

import com.pavbatol.talentium.internship.model.QInternship;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.querydsl.core.BooleanBuilder;
import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;

public class InternshipFilterHelper {

    public static java.util.function.Predicate<Object> isNullOrEmpty() {
        return obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
    }

    public static BooleanBuilder getInternshipBaseBooleanBuilder(@NonNull BaseInternshipSearchFilter filter) {
        java.util.function.Predicate<Object> isNullOrEmpty = obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
        QInternship qInternship = QInternship.internship;
        return new BooleanBuilder()
                .and(!isNullOrEmpty.test(filter.getInitiatorIds()) ? qInternship.initiator.id.in(filter.getInitiatorIds()) : null)
                .and(!isNullOrEmpty.test(filter.getManagementIds()) ? qInternship.management.id.in(filter.getManagementIds()) : null)

                .and(!isNullOrEmpty.test(filter.getStartLatitude()) ? qInternship.longitude.goe(filter.getStartLatitude()) : null)
                .and(!isNullOrEmpty.test(filter.getEndLatitude()) ? qInternship.longitude.loe(filter.getEndLatitude()) : null)
                .and(!isNullOrEmpty.test(filter.getStartLongitude()) ? qInternship.longitude.goe(filter.getStartLongitude()) : null)
                .and(!isNullOrEmpty.test(filter.getEndLongitude()) ? qInternship.longitude.loe(filter.getEndLongitude()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeStartAgeFrom()) ? qInternship.longitude.goe(filter.getRangeStartAgeFrom()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeStartAgeTo()) ? qInternship.longitude.goe(filter.getRangeStartAgeTo()) : null)

                .and(!isNullOrEmpty.test(filter.getRangeStartPublishedOn()) ? qInternship.startDate.after(filter.getRangeStartPublishedOn()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeEndPublishedOn()) ? qInternship.startDate.before(filter.getRangeEndPublishedOn()) : null)

                .and(!isNullOrEmpty.test(filter.getRangeStartStartDate()) ? qInternship.startDate.after(filter.getRangeStartStartDate()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeEndStartDate()) ? qInternship.startDate.before(filter.getRangeEndStartDate()) : null)

                .and(!isNullOrEmpty.test(filter.getRangeStartEndDate()) ? qInternship.startDate.after(filter.getRangeStartEndDate()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeEndEndDate()) ? qInternship.startDate.before(filter.getRangeEndEndDate()) : null)

                .and(!isNullOrEmpty.test(filter.getDayDuration()) ? qInternship.dayDuration.eq(WorkingDayDuration.by(filter.getDayDuration())) : null);
    }

    public static BooleanBuilder getInternshipPublicBooleanBuilder(@NonNull InternshipPublicSearchFilter filter) {
        return getInternshipBaseBooleanBuilder(filter);
    }

    public static BooleanBuilder getInternshipAdminBooleanBuilder(@NonNull InternshipAdminSearchFilter filter) {
        QInternship qInternship = QInternship.internship;
        BooleanBuilder builder = getInternshipBaseBooleanBuilder(filter);
        return builder
                .and(!isNullOrEmpty().test(filter.getState()) ? qInternship.state.eq(InternshipState.by(filter.getState())) : null);
    }
}
