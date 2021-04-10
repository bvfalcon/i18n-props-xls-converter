/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.i18n.propsxlsconverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.FileUtils;
import org.everit.i18n.propsxlsconverter.internal.I18nConverterImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class I18nConverterTest {

  private static final String FILE_NAME_XLS = "example.xls";

  private static final String FOLDER_TARGET_TEST = "./target/test/";

  private static final List<String> MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES;

  private static final List<String> MESSAGES2_FOLDER_FILE_NAMES;

  private static final String MESSAGES2_FOLDER_NAME = "messages2";

  static {
    MESSAGES2_FOLDER_FILE_NAMES = new ArrayList<String>();
    MESSAGES2_FOLDER_FILE_NAMES.add("messages2.properties");
    MESSAGES2_FOLDER_FILE_NAMES.add("messages2_hu.properties");
    MESSAGES2_FOLDER_FILE_NAMES.add("messages2_de.properties");

    MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES = new ArrayList<String>();
    MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.add("messages.properties");
    MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.add("messages_hu.properties");
    MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.add("messages_de.properties");
    MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.add(MESSAGES2_FOLDER_NAME);
  }
  private I18nConverter i18nConverter;

  @After
  public void after() {
    File folderTargetTest = new File(FOLDER_TARGET_TEST);
    FileUtils.deleteQuietly(folderTargetTest);

    File fileXls = new File(FILE_NAME_XLS);
    FileUtils.deleteQuietly(fileXls);
  }

  @Before
  public void before() {
    File folderTargetTest = new File(FOLDER_TARGET_TEST);
    FileUtils.deleteQuietly(folderTargetTest);

    File fileXls = new File(FILE_NAME_XLS);
    FileUtils.deleteQuietly(fileXls);

    i18nConverter = new I18nConverterImpl();
  }

  private void checkExportFunctionValidatesProblem() {
    try {
      i18nConverter.exportToXls(null, null, null, null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls("", null, null, null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls("", "", null, null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls("", "", "", null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls("", "", "", new String[] {});
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls(FILE_NAME_XLS, "", "", new String[] {});
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls(FILE_NAME_XLS, "./src/", "", new String[] {});
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls(FILE_NAME_XLS, "./src/", "", new String[] {});
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls(FILE_NAME_XLS, "pom.xml", "*", new String[] {});
      Assert.fail("Expect LanguageException.");
    } catch (RuntimeException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.exportToXls(FILE_NAME_XLS, "./src/", "*", new String[] {});
      Assert.fail("Expect PatternSyntaxException.");
    } catch (PatternSyntaxException e) {
      Assert.assertNotNull(e);
    }
  }

  private void checkImportFunctionValidatesProblem() {
    try {
      i18nConverter.importFromXls(null, null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.importFromXls("", null);
      Assert.fail("Expect NullPointerExpection.");
    } catch (NullPointerException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.importFromXls("", "");
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.importFromXls(FILE_NAME_XLS, "");
      Assert.fail("Expect IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      Assert.assertNotNull(e);
    }

    try {
      i18nConverter.importFromXls(FILE_NAME_XLS, "pom.xml");
      Assert.fail("Expect LanguageException.");
    } catch (RuntimeException e) {
      Assert.assertNotNull(e);
    }
  }

  private void exportFunctionTest() throws IOException {
    File workingDirectory = new File("./src/test/resources/messages/");

    i18nConverter.exportToXls(FILE_NAME_XLS,
        workingDirectory.getCanonicalPath().toString(),
        ".*\\.properties$",
        new String[] { "hu", "de" });

    File file = new File(FILE_NAME_XLS);
    Assert.assertTrue("The " + FILE_NAME_XLS + " not found", file.exists());
  }

  private void importFunctiontest() throws IOException {
    File workingDirectory = new File(FOLDER_TARGET_TEST);
    workingDirectory.mkdirs();

    i18nConverter.importFromXls(FILE_NAME_XLS,
        workingDirectory.getCanonicalPath().toString());

    File[] messageFolderFiles = workingDirectory.listFiles();
    Assert.assertEquals("Too less or too much files in the directory.",
        MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.size(),
        messageFolderFiles.length);

    for (File messageFolderfile : messageFolderFiles) {
      if (messageFolderfile.isDirectory()) {
        Assert.assertEquals("The folder name not the expected",
            MESSAGES2_FOLDER_NAME,
            messageFolderfile.getName());

        File[] messages2Folderfiles = messageFolderfile.listFiles();
        Assert.assertEquals("Too less or too much files in the directory.",
            MESSAGES2_FOLDER_FILE_NAMES.size(),
            messages2Folderfiles.length);

        for (File messages2Folderfile : messages2Folderfiles) {
          Assert.assertTrue("Not cointains the expected file names.",
              MESSAGES2_FOLDER_FILE_NAMES.contains(messages2Folderfile.getName()));
        }
      }
      Assert.assertTrue("Not cointains the expected file names.",
          MESSAGES_FOLDER_FILE_AND_DIRECTORY_NAMES.contains(messageFolderfile.getName()));
    }
  }

  @Test
  public void testLanguageFiles() throws IOException {
    exportFunctionTest();
    importFunctiontest();
  }

  @Test
  public void testValidateProblems() {
    checkExportFunctionValidatesProblem();
    checkImportFunctionValidatesProblem();
  }
}
