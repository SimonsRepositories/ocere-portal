package com.ocere.portal.model;

import com.ocere.portal.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToRoleConverter implements Converter<String, Role>
{
    @Autowired
    private RoleService roleService;

    @Override
    public Role convert(String s) {
        //NumberFormatException!!!
        return roleService.findById(Integer.valueOf(s));
    }
}
