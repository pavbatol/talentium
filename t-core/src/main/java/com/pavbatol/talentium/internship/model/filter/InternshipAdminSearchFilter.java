package com.pavbatol.talentium.internship.model.filter;

import com.pavbatol.talentium.internship.model.enums.InternshipState;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InternshipAdminSearchFilter extends AbstractInternshipSearchFilter {

    InternshipState state;
}
