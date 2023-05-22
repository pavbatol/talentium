package com.pavbatol.talentium.hh.model;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class HhFilter {
    String authority;
    String management;
    String address;
    String contacts;
}
