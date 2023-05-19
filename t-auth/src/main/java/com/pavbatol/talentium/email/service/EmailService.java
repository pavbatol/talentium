package com.pavbatol.talentium.email.service;

import com.pavbatol.talentium.app.exception.SendingMailException;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text) throws SendingMailException;
}
