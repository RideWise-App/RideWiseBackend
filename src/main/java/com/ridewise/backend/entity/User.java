package com.ridewise.backend.entity;

import com.ridewise.backend.constants.Roles;

abstract class User {
    private Roles role;

    public Roles getRole() {
        return this.role;
    }
}
