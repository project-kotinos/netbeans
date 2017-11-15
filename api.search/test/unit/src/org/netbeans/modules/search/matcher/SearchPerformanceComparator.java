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
package org.netbeans.modules.search.matcher;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFileChooser;
import org.netbeans.api.search.SearchPattern;
import org.netbeans.api.search.SearchScopeOptions;
import org.netbeans.api.search.provider.SearchInfo;
import org.netbeans.api.search.provider.SearchInfoUtils;
import org.netbeans.api.search.provider.SearchListener;
import org.netbeans.modules.search.MatchingObject.Def;
import org.netbeans.spi.search.SearchFilterDefinition;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author jhavlin
 */
public class SearchPerformanceComparator extends javax.swing.JFrame {

    JFileChooser jFileChooser;
    File root = null;

    /**
     * Creates new form SearchPerformanceComparator
     */
    public SearchPerformanceComparator() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        rootTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        regexpTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        matcherComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        matchesLabel = new javax.swing.JLabel();
        currentLabel = new javax.swing.JLabel();
        runButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel1.text")); // NOI18N

        rootTextField.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.rootTextField.text")); // NOI18N

        jButton1.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel2.text")); // NOI18N

        regexpTextField.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.regexpTextField.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel3.text")); // NOI18N

        matcherComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default (ML: BufferedCharSequence, SL: LineReader)", "MultiLineMappedMatcherBig", "MultiLineMappedMatcherSmall", "MultiLineStreamMatcher", "SingleLineStreamMatcher", "Iterator", "AsciiMultiLineMappedMatcher" }));

        jLabel4.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel5.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.jLabel6.text")); // NOI18N

        timeLabel.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.timeLabel.text")); // NOI18N

        matchesLabel.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.matchesLabel.text")); // NOI18N

        currentLabel.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.currentLabel.text")); // NOI18N

        runButton.setText(org.openide.util.NbBundle.getMessage(SearchPerformanceComparator.class, "SearchPerformanceComparator.runButton.text")); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rootTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addComponent(regexpTextField)
                            .addComponent(matcherComboBox, 0, 310, Short.MAX_VALUE)
                            .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(matchesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(runButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rootTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(regexpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(matcherComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(timeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(matchesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(currentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jFileChooser == null) {
            jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(
                    JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setMultiSelectionEnabled(false);
        }
        jFileChooser.showOpenDialog(this);
        File f = jFileChooser.getSelectedFile();
        if (f != null) {
            rootTextField.setText(f.getPath());
            root = f;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        if (root == null) {
            return;
        }
        timeLabel.setText("0:00:00");
        matchesLabel.setText("0");
        currentLabel.setText(root.getPath());

        SearchFilterDefinition hiddenFilter = new SearchFilterDefinition() {

            @Override
            public boolean searchFile(FileObject file)
                    throws IllegalArgumentException {

                return !file.getName().startsWith(".");
            }

            @Override
            public FolderResult traverseFolder(FileObject folder)
                    throws IllegalArgumentException {

                return folder.getName().startsWith(".")
                        ? FolderResult.DO_NOT_TRAVERSE
                        : FolderResult.TRAVERSE;
            }
        };

        final SearchScopeOptions so = SearchScopeOptions.create(
                "*.java, *.txt, *.xml, *.properties, *.log", false);

        final SearchInfo si =
                SearchInfoUtils.createSearchInfoForRoots(
                new FileObject[] {FileUtil.toFileObject(root)},
                false, hiddenFilter);

        AbstractMatcher matcher = null;

        final SearchPattern sp = SearchPattern.create(
                regexpTextField.getText(), false, false, true);

        String selectedMatcherType = (String) matcherComboBox.getSelectedItem();
        if (selectedMatcherType == null) {
            return;
        } else if (selectedMatcherType.startsWith("Default")) {
            matcher = new DefaultMatcher(sp);
        } else if (selectedMatcherType.startsWith("MultiLineMappedMatcherBig")) {
            matcher = new MultiLineMappedMatcherBig(sp);
        } else if (selectedMatcherType.startsWith("MultiLineMappedMatcherSmall")) {
            matcher = new MultiLineMappedMatcherSmall(sp);
        } else if (selectedMatcherType.startsWith("MultiLineStreamMatcher")) {
            matcher = new MultiLineStreamMatcher(sp);
        } else if (selectedMatcherType.startsWith("SingleLineStreamMatcher")) {
            matcher = new SingleLineStreamMatcher(sp);
        } else if (selectedMatcherType.equals("AsciiMultiLineMappedMatcher")) {
            matcher = new AsciiMultiLineMappedMatcher(sp);
        }
        if (matcher != null) {
            final AbstractMatcher fm = matcher;
            final CustomSearchListener listener =
                    new CustomSearchListener(matcher);
            final AtomicBoolean terminated = new AtomicBoolean(false);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    listener.searchStarted();
                    for (FileObject fo : si.getFilesToSearch(
                            so, listener, terminated)) {

                        Def result = fm.check(fo, listener);
                        if (result != null) {
                            listener.objectFound(result);
                        }
                    }
                    listener.searchFinished();
                }
            }).start();
        }
    }//GEN-LAST:event_runButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchPerformanceComparator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPerformanceComparator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPerformanceComparator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPerformanceComparator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SearchPerformanceComparator spc =
                        new SearchPerformanceComparator();
                spc.setVisible(true);
                spc.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox matcherComboBox;
    private javax.swing.JLabel matchesLabel;
    private javax.swing.JTextField regexpTextField;
    private javax.swing.JTextField rootTextField;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

    private class CustomSearchListener
            extends SearchListener {

        private AbstractMatcher matcher;
        private int matches = 0;
        private long start = 0;
        private long end = 0;

        public CustomSearchListener(AbstractMatcher matcher) {
            this.matcher = matcher;
        }

        @Override
        public void directoryEntered(String path) {
            currentLabel.setText(path);
        }

        @Override
        public void fileContentMatchingStarted(String fileName) {
            currentLabel.setText(fileName);
            updateTime();
        }

        public void objectFound(Def object) {
            matches++;
            matchesLabel.setText(matches + "");
            updateTime();
        }

        public void searchStarted() {
            start = System.currentTimeMillis();
        }

        public void searchFinished() {
            end = System.currentTimeMillis();
            timeLabel.setText((end - start) + " / " + matcher.getTotalTime()
                    + " ms (ended)");
            matchesLabel.setText(matcher.getMatchingFiles()
                    + " / " + matcher.getMatchingItems());
        }

        private void updateTime() {
            timeLabel.setText((System.currentTimeMillis() - start) + " ms");
        }

        @Override
        public void generalError(Throwable t) {
            System.out.println(t.getMessage());
            Exceptions.printStackTrace(t);
        }

        @Override
        public void fileSkipped(FileObject fo, SearchFilterDefinition filter,
                String message) {
            System.out.println("Skipping file: " + fo.getPath());
        }
    }
}
