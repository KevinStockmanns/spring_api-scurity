package com.security.utils.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum Roles {
    CUSTOMER(Arrays.asList(Permisos.READ_ALL_PRODUCTS)),
    ADMIN(Arrays.asList(Permisos.READ_ALL_PRODUCTS ,Permisos.SAVE_ONE_PRODUCT));

    private List<Permisos> permisos;

    Roles(List<Permisos> permisos){
        this.permisos = permisos;
    }
}
