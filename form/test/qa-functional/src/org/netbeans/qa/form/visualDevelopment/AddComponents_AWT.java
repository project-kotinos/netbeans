/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.qa.form.visualDevelopment;

import java.util.Vector;


import org.netbeans.jellytools.*;
import org.netbeans.jellytools.modules.form.*;
import org.netbeans.jellytools.nodes.*;
import org.netbeans.jellytools.actions.*;

import org.netbeans.junit.ide.ProjectSupport;
import org.netbeans.qa.form.*;
import java.io.*;
import java.util.ArrayList;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;

/**
 *<P>
 *<B><BR> Test create frame.</B>
 *
 *<BR><BR><B>What it tests:</B><BR>
 *  Frame containing all components from Component Palette AWT category try compile.
 *<BR><BR><B>How it works:</B><BR>
 *  Find tested form file, add all components from AWT category and compile created frame (check compile resolution).
 *
 *<BR><BR><B>Settings:</B><BR>
 *  Jemmy/Jelly classes, VisualDevelopmentSupport class in the classpath.
 *
 *<BR><BR><B>Resources:</B><BR>
 *  File (Resources.) clear_Frame(java/form) generated by NBr32(37).
 *
 *<BR><B>Possible reasons of failure</B>
 * <BR><U>jelly didn't find menu or popup menu</U>
 * <BR><U>is impossible add component or components in AWT category is another as in NB r3.2 (37)</U>
 * <BR><U>component was't add correctly or generated source code is wrong</U>
 *
 * @author  Marian.Mirilovic@oracle.com
 * @version
 * 
 * <b>Adam Senk</b>
 * 20 April 2011 WORKS
 */
public class AddComponents_AWT extends ExtJellyTestCase {

    public String FILE_NAME = "clear_Frame.java";
    public String PACKAGE_NAME = "data";
    public String DATA_PROJECT_NAME = "SampleProject";
    public String FRAME_ROOT = "[Frame]";
    public MainWindowOperator mainWindow;
    public ProjectsTabOperator pto;
    public Node formnode;

    public AddComponents_AWT(String testName) {
        super(testName);
    }

    @Override
    public void setUp() throws IOException {
        openDataProjects(_testProjectName);
    }

    public static Test suite() {
        return NbModuleSuite.create(NbModuleSuite.createConfiguration(AddComponents_AWT.class).addTest(
                "testAddAndCompile",
                "testJavaFile"//,
                //"testFormFile"
                ).clusters(".*").enableModules(".*").gui(true));

    }

    /** Run test.
     */
    public void testAddAndCompile() {
        String categoryName = "AWT";

        System.out.println(">>>" + this.getWorkDirPath() + "<<<");

        mainWindow = MainWindowOperator.getDefault();
        pto = new ProjectsTabOperator();
        sleep(300);
        ProjectRootNode prn = pto.getProjectRootNode(DATA_PROJECT_NAME);
        sleep(300);
        prn.select();
        formnode = new Node(prn, "Source Packages|" + PACKAGE_NAME + "|" + FILE_NAME);
        formnode.select();
        log("Form node selected.");

        EditAction editAction = new EditAction();
        editAction.perform(formnode);
        log("Source Editor window opened.");

        OpenAction openAction = new OpenAction();
        openAction.perform(formnode);
        log("Form Editor window opened.");

        // store all component names from the category in the Vector

        ComponentPaletteOperator palette = new ComponentPaletteOperator();
        palette.collapseBeans();
        //        palette.collapseLayouts();
        palette.collapseSwingControls();
        palette.collapseSwingControls();
        palette.collapseSwingMenus();
        palette.collapseSwingWindows();
        palette.expandAWT();

        ComponentInspectorOperator cio = new ComponentInspectorOperator();
        cio.freezeNavigatorAndRun(new Runnable() {

            @Override
            public void run() {
                ArrayList componentNames = new ArrayList();
                String[] componentList = {"Label", "Button", "Text Field", "Text Area", "Checkbox", "Choice", "List", "Scrollbar", "Scroll Pane", "Panel", "Canvas", "Menu Bar", "Popup Menu"};
                for (int i = 0; i < componentList.length; i++) {
                    log("Adding " + componentList[i]);
                    componentNames.add(componentList[i]);
                }

                ComponentInspectorOperator cio = new ComponentInspectorOperator();
                Node inspectorRootNode = new Node(cio.treeComponents(), FRAME_ROOT);
                inspectorRootNode.select();
                inspectorRootNode.expand();
                // add all beans from Palette Category to form

                for (int i = 0; i < componentNames.size(); i++) {
                    String itemPath = "Add From Palette|AWT|" + componentNames.remove(0).toString();
                    log("Running " + itemPath);

                    runPopupOverNode(itemPath, inspectorRootNode);
                }
            }
        });


        log("All components from Component Palette : " + categoryName + " - were added to " + FILE_NAME);
        log("Try to save the form.");
        new org.netbeans.jemmy.EventTool().waitNoEvent(1000);
        editAction.perform(formnode);
        Action saveAction;
        saveAction = new Action("File|Save", null);
        saveAction.perform();
    }

    /** Run test.
     */
    public void testFormFile() {
        compareFileByExt("form");
    }

    /** Run test.
     */
    public void testJavaFile() {
        //compareFileByExt("java");

        mainWindow = MainWindowOperator.getDefault();
        pto = new ProjectsTabOperator();
        sleep(300);
        ProjectRootNode prn = pto.getProjectRootNode(DATA_PROJECT_NAME);
        sleep(300);
        prn.select();
        formnode = new Node(prn, "Source Packages|" + PACKAGE_NAME + "|" + FILE_NAME);
        formnode.select();
        log("Form node selected.");

        FormDesignerOperator designer = new FormDesignerOperator(FILE_NAME);
        OpenAction openAction = new OpenAction();
        openAction.perform(formnode);
        ArrayList<String> lines = new ArrayList<String>();

        lines.add("label1 = new java.awt.Label();");
        lines.add("button1 = new java.awt.Button();");
        lines.add("textField1 = new java.awt.TextField();");
        lines.add("textArea1 = new java.awt.TextArea();");
        lines.add("checkbox1 = new java.awt.Checkbox();");
        lines.add("choice1 = new java.awt.Choice();");
        lines.add("list1 = new java.awt.List();");


        lines.add("private java.awt.Button button1;");
        lines.add("private java.awt.Checkbox checkbox1;");
        lines.add("private java.awt.Choice choice1;");
        lines.add("private java.awt.Label label1;");
        lines.add("private java.awt.List list1;");
        lines.add("private java.awt.TextArea textArea1;");
        lines.add("private java.awt.TextField textField1;");

        findInCode(lines, designer);
    }

    private void compareFileByExt(String fileExt) {
        String refSourceFilePath = getDataDir().getAbsolutePath() + File.separatorChar
                + DATA_PROJECT_NAME + File.separatorChar + "src" + File.separatorChar
                + PACKAGE_NAME + File.separatorChar + FILE_NAME + "." + fileExt;
        log("refSourceFilePath:" + refSourceFilePath);

        try {
            getRef().print(VisualDevelopmentUtil.readFromFile(refSourceFilePath));
        } catch (Exception e) {
            fail("Fail during creating ref file: " + e.getMessage());
        }

        String javaVersionPrefix = VisualDevelopmentUtil.JAVA_VERSION.substring(0, 3);
        String passFileName = this.getName() + "_" + javaVersionPrefix + ".pass";
        log("passFileName: " + passFileName);

        compareReferenceFiles(this.getName() + ".ref", passFileName, this.getName() + ".diff");
    }

    /** Run test.
     */
    public void testCloseDataProject() {
        closeDataProject();
        //        EditorWindowOperator ewo = new EditorWindowOperator();
        //        ewo.closeDiscard();
    }

    public void closeDataProject() {
        ProjectSupport.closeProject(DATA_PROJECT_NAME);
        log("SampleProject closed.");
    }

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
}
