package mfiles.service;

import mfiles.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MfilesClientServiceTest {
    MfilesClientService clientService = new MfilesClientService("http://10.26.0.46/REST/");
    @Test
    public void testAuth() {
        List<Vault> test = clientService.authentication("jirauser", "vnds1234", false, null);
//        List<Vault> test = authentication.authentication("AlexK", "alexk", "﻿D673ADED-6180-494D-BE9D-FC190A4AA1D8");
        assertThat(test).isNotNull();
        assertThat(test.get(0).getAuthentication()).isNotNull();
    }
    @Test
    public void testGetClasses() {
        List<Vault> test = clientService.authentication("thang.hoang", "VNDs$1234", true, "IPA");
//        List<Vault> test = authentication.authentication("AlexK", "alexk", "﻿D673ADED-6180-494D-BE9D-FC190A4AA1D8");
        assertThat(test).isNotNull();
        assertThat(test.get(0).getAuthentication()).isNotNull();

        final List<ClassGroup> classGroups = clientService.getClassGroups(test.get(0).getAuthentication());

        assertThat(classGroups).isNotNull();
    }

    @Test
    public void testExport() {
        Path file = Paths.get("/Users/thang/Documents", "thang.hoang");
        Path file2 = Paths.get("/Users/thang/Documents", "thang.hoang.pub");

        final ExportResult exportResult = clientService.export(Arrays.asList(file, file2), "0001000001", "451", "107", "1");
        assertThat(exportResult).isNotNull();
    }

    @Test
    public void testCreateMultiFile() {
        //Class value
        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, 451);

        PropertyValue soTaiKhoan = PropertyValue.create(1205, MFDataType.Lookup, "0001000001", 32901);

        List<Vault> authJson = clientService.authentication("jirauser", "vnds1234", false, null);
        String xAuthentication = authJson.get(0).getAuthentication();

        List<PropertyDef> propertyDefList = clientService.getPropertyDefs();

        Path file = Paths.get("/Users/thang/Documents", "thang.hoang");
        Path file2 = Paths.get("/Users/thang/Documents", "thang.hoang.pub");

        ObjectVersion test = clientService.createObject(xAuthentication, MFDataType.Uninitialized,
                new PropertyValue[] {testJiraClass, soTaiKhoan}, Arrays.asList(file, file2), 107);

        assertThat(test).isNotNull();
    }

    @Test
    public void testCreateFile() {
        //Class value
        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, 451);

        PropertyValue soTaiKhoan = PropertyValue.create(1205, MFDataType.Lookup, "0001000001", 32901);

        List<Vault> authJson = clientService.authentication("thang.hoang", "VNDs$1234", true, "IPA");
        String xAuthentication = authJson.get(0).getAuthentication();

        List<PropertyDef> propertyDefList = clientService.getPropertyDefs();

        Path file = Paths.get("/Users/thang/Documents", "thang.hoang");
        ObjectVersion test = clientService.createObject(xAuthentication, MFDataType.Uninitialized,
                new PropertyValue[] {testJiraClass, soTaiKhoan}, file, 107);

        assertThat(test).isNotNull();
    }

    @Test
    public void testWorkflow() {
        String auth = clientService.getJiraUserAuthentication();
        final List<Workflow> workflows = clientService.getStructureWorkflows();
        assertThat(workflows).isNotEmpty();
    }

    @Test
    public void getStructureClasses() {
        final ExtendedObjectClass extendedObjectClassList = clientService.getExtendedObjectClass(248);
        assertThat(extendedObjectClassList).isNotNull();
    }

    @Test
    public void getPropertyDef() {
        final List<PropertyDef> propertyDefs = clientService.getPropertyDefs();
        assertThat(propertyDefs).isNotEmpty();
    }
}
