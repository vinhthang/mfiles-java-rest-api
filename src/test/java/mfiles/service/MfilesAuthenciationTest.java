package mfiles.service;

import mfiles.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MfilesAuthenciationTest {
    MfilesClientService clientService = new MfilesClientService("http://10.26.0.46/REST/");
    @Test
    public void testAuth() {
        List<Vault> test = clientService.authentication("thang.hoang", "VNDs$1234");
//        List<Vault> test = authentication.authentication("AlexK", "alexk", "﻿D673ADED-6180-494D-BE9D-FC190A4AA1D8");
        assertThat(test).isNotNull();
        assertThat(test.get(0).getAuthentication()).isNotNull();
    }

    @Test
    public void testCreateFile() {
        //Class value
        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, 451);

        PropertyValue soTaiKhoan = PropertyValue.create(1205, MFDataType.Lookup, "0001000003", 32901);

        List<Vault> authJson = clientService.authentication("thang.hoang", "VNDs$1234");
        String xAuthentication = authJson.get(0).getAuthentication();

        List<PropertyDef> propertyDefList = clientService.getPropertyDefs(xAuthentication);

        Path file = Paths.get("/Users/thang/Documents", "thang.hoang");
        ObjectVersion test = clientService.createObject(xAuthentication, MFDataType.Uninitialized,
                new PropertyValue[] {testJiraClass, soTaiKhoan}, file);


    }

    @Test
    public void testGetObject() {
        List<Vault> authJson = clientService.authentication("thang.hoang", "VNDs$1234");
        String xAuthentication = authJson.get(0).getAuthentication();
        ObjectVersion test = clientService.getObject(xAuthentication, MFDataType.Uninitialized, 869613);
        assertThat(test).isNotNull();
        assertThat(test.getDisplayID()).isEqualTo("869613");

    }
}
