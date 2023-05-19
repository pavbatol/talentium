package com.pavbatol.talentium;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DemoAuthController {
    @GetMapping("/login")
    public String loginEndpoint() {
        String authenticationStr = getSecurityContext();
        return "Login!\n" + authenticationStr;
    }


    @GetMapping("/admin")
    public String adminEndpoint() {
        String authenticationStr = getSecurityContext();
        return "Admin!\n" + authenticationStr;
    }

    @GetMapping("/user")
    public String userEndpoint() {
        String authenticationStr = getSecurityContext();
        return "User!\n" + authenticationStr;
    }

    @GetMapping("/all")
    public String allRolesEndpoint() {
        String authenticationStr = getSecurityContext();
        return "All Roles!\n" + authenticationStr;
    }

    @DeleteMapping("/delete")
    public String deleteEndpoint(@RequestBody String s) {
        String authenticationStr = getSecurityContext();
        return "I am deleting " + s +"\n" + authenticationStr;
    }

    private static String getSecurityContext() {
        return SecurityContextHolder.getContext() != null
                ? SecurityContextHolder.getContext().getAuthentication().toString()
                : "SecurityContextHolder.getContext() == null";
    }
}