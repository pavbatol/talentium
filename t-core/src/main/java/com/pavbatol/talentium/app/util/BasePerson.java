package com.pavbatol.talentium.app.util;

import com.pavbatol.talentium.app.enums.Position;

public interface BasePerson {

    public Long getId();

    public void setId(Long id);

    public String getEmail();

    public void setEmail(String email);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getSecondName();

    public void setSecondName(String secondName);

    public Position getPosition();

    public void setPosition(Position position);
}
