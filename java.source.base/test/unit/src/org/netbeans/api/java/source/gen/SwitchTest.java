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
package org.netbeans.api.java.source.gen;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.java.source.TestUtilities;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.WorkingCopy;

/**
 * The following shell script was used to generate the code snippets
 * <code>cat test/unit/data/test/Test.java | tr '\n' ' ' | tr '\t' ' ' | sed -E 's| +| |g' | sed 's|"|\\"|g'</code>
 * @author Samuel Halliday
 */
public class SwitchTest extends GeneratorTestBase {

    public SwitchTest(String name) {
        super(name);
    }

    public void test158129() throws Exception {
        testFile = new File(getWorkDir(), "Test.java");
        String test = "public class Test { void m(int p) { switch (p) { ca|se 0: } } }";
        // XXX whitespace "public class Test { void m(int p) { switch (p) { case 0: break; } } }"
        String golden = "public class Test { void m(int p) { switch (p) { case 0:break;\n } } }";
        final int index = test.indexOf("|");
        assertTrue(index != -1);
        TestUtilities.copyStringToFile(testFile, test.replace("|", ""));
        JavaSource src = getJavaSource(testFile);
        Task<WorkingCopy> task = new Task<WorkingCopy>() {

            public void run(WorkingCopy copy) throws IOException {
                if (copy.toPhase(Phase.RESOLVED).compareTo(Phase.RESOLVED) < 0) {
                    return;
                }
                TreeMaker make = copy.getTreeMaker();
                TreePath node = copy.getTreeUtilities().pathFor(index);
                assertTrue(node.getLeaf().getKind() == Kind.CASE);
                CaseTree original = (CaseTree) node.getLeaf();
                List<StatementTree> st = new ArrayList<StatementTree>();
                st.addAll(original.getStatements());
                st.add(make.Break(null));
                CaseTree modified = make.Case(original.getExpression(), st);
                copy.rewrite(original, modified);
            }
        };
        src.runModificationTask(task).commit();
        String res = TestUtilities.copyFileToString(testFile);
        System.err.println(res);
        assertEquals(golden, res);
    }

    public void testAddCase1() throws Exception {
        testFile = new File(getWorkDir(), "Test.java");
        String test = "public class Test {\n" +
                      "    void m(int p) {\n" +
                      "        switch (p) {\n" +
                      "            case 0:\n" +
                      "                System.err.println(1);\n" +
                      "                break;\n" +
                      "            ca|se 2:\n" +
                      "                System.err.println(2);\n" +
                      "                break;\n" +
                      "        }\n" +
                      "    }\n" +
                      "}\n";
        String golden = "public class Test {\n" +
                        "    void m(int p) {\n" +
                        "        switch (p) {\n" +
                        "            case 0:\n" +
                        "                System.err.println(1);\n" +
                        "                break;\n" +
                        "            case 1:\n" +
                        "            case 2:\n" +
                        "            case 3:\n" +
                        "                System.err.println(2);\n" +
                        "                break;\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n";
        final int index = test.indexOf("|");
        assertTrue(index != -1);
        TestUtilities.copyStringToFile(testFile, test.replace("|", ""));
        JavaSource src = getJavaSource(testFile);
        Task<WorkingCopy> task = new Task<WorkingCopy>() {

            public void run(WorkingCopy copy) throws IOException {
                if (copy.toPhase(Phase.RESOLVED).compareTo(Phase.RESOLVED) < 0) {
                    return;
                }
                TreeMaker make = copy.getTreeMaker();
                TreePath node = copy.getTreeUtilities().pathFor(index);
                assertTrue(node.getLeaf().getKind() == Kind.CASE);
                SwitchTree st = (SwitchTree) node.getParentPath().getLeaf();
                List<CaseTree> newCases = new LinkedList<CaseTree>();
                newCases.add(st.getCases().get(0));
                newCases.add(make.Case(make.Literal(1), Collections.<StatementTree>emptyList()));
                newCases.add(make.Case(make.Literal(2), Collections.<StatementTree>emptyList()));
                newCases.add(make.Case(make.Literal(3), st.getCases().get(1).getStatements()));
                copy.rewrite(st, make.Switch(st.getExpression(), newCases));
            }
        };
        src.runModificationTask(task).commit();
        String res = TestUtilities.copyFileToString(testFile);
        System.err.println(res);
        assertEquals(golden, res);
    }
    
    public void testIf2Switch() throws Exception {
        testFile = new File(getWorkDir(), "Test.java");
        String test = "public class Test {\n" +
                      "    void m(int p, int thmbPaneWidth) {\n" +
                      "        if (p == 0) {\n" +
                      "            int width = thmbPaneWidth,\n" +
                      "                    height = (int) (thmbPaneWidth * 1.2),\n" +
                      "                    left = 0,\n" +
                      "                    top = 0;\n" +
                      "        }\n" +
                      "    }\n" +
                      "}\n";
        String golden = "public class Test {\n" +
                        "    void m(int p, int thmbPaneWidth) {\n" +
                        "        switch (p) {\n" +
                        "            case 0:\n" +
                        "                int width = thmbPaneWidth,\n" +
                        "                        height = (int) (thmbPaneWidth * 1.2),\n" +
                        "                        left = 0,\n" +
                        "                        top = 0;\n" +
                        "                break;\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n";
        TestUtilities.copyStringToFile(testFile, test.replace("|", ""));
        JavaSource src = getJavaSource(testFile);
        Task<WorkingCopy> task = new Task<WorkingCopy>() {
            public void run(final WorkingCopy copy) throws IOException {
                if (copy.toPhase(Phase.RESOLVED).compareTo(Phase.RESOLVED) < 0) {
                    return;
                }
                final TreeMaker make = copy.getTreeMaker();
                new TreePathScanner<Void, Void>() {
                    @Override public Void visitIf(IfTree node, Void p) {
                        List<StatementTree> statements = new ArrayList<StatementTree>(((BlockTree) node.getThenStatement()).getStatements());
                        statements.add(make.Break(null));
                        copy.rewrite(node, make.Switch(make.Identifier("p"), Collections.singletonList(make.Case(make.Literal(0), statements))));
                        return super.visitIf(node, p);
                    }
                }.scan(copy.getCompilationUnit(), null);
            }
        };
        src.runModificationTask(task).commit();
        String res = TestUtilities.copyFileToString(testFile);
        System.err.println(res);
        assertEquals(golden, res);
    }

    public void testSwitch2If() throws Exception {
        testFile = new File(getWorkDir(), "Test.java");
        String test = "public class Test {\n" +
                      "    void m(int p, int thmbPaneWidth) {\n" +
                        "        switch (p) {\n" +
                        "            case 0:\n" +
                        "                int width = thmbPaneWidth,\n" +
                        "                        height = (int) (thmbPaneWidth * 1.2),\n" +
                        "                        left = 0,\n" +
                        "                        top = 0;\n" +
                        "                break;\n" +
                        "        }\n" +
                      "    }\n" +
                      "}\n";
        String golden = "public class Test {\n" +
                        "    void m(int p, int thmbPaneWidth) {\n" +
                      "        if (p == 0) {\n" +
                      "            int width = thmbPaneWidth,\n" +
                      "                    height = (int) (thmbPaneWidth * 1.2),\n" +
                      "                    left = 0,\n" +
                      "                    top = 0;\n" +
                      "        }\n" +
                        "    }\n" +
                        "}\n";
        TestUtilities.copyStringToFile(testFile, test.replace("|", ""));
        JavaSource src = getJavaSource(testFile);
        Task<WorkingCopy> task = new Task<WorkingCopy>() {
            public void run(final WorkingCopy copy) throws IOException {
                if (copy.toPhase(Phase.RESOLVED).compareTo(Phase.RESOLVED) < 0) {
                    return;
                }
                final TreeMaker make = copy.getTreeMaker();
                new TreePathScanner<Void, Void>() {
                    @Override public Void visitCase(CaseTree node, Void p) {
                        IfTree nue = make.If(make.Binary(Kind.EQUAL_TO, make.Identifier("p"), make.Literal(0)), make.Block(node.getStatements().subList(0, node.getStatements().size() - 1), false), null);
                        copy.rewrite(getCurrentPath().getParentPath().getLeaf(), nue);
                        return super.visitCase(node, p);
                    }
                }.scan(copy.getCompilationUnit(), null);
            }
        };
        src.runModificationTask(task).commit();
        String res = TestUtilities.copyFileToString(testFile);
        System.err.println(res);
        assertEquals(golden, res);
    }

    // XXX I don't understand what these are used for
    @Override
    String getSourcePckg() {
        return "";
    }

    @Override
    String getGoldenPckg() {
        return "";
    }
}
