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

/*
 * ResolveTreeConflictPanel.java
 *
 * Created on Nov 26, 2010, 2:17:25 PM
 */

package org.netbeans.modules.git.ui.conflicts;

import java.io.File;
import org.netbeans.libs.git.GitConflictDescriptor.Type;
import org.openide.util.NbBundle;

/**
 *
 * @author ondra
 */
public class ResolveTreeConflictPanel extends javax.swing.JPanel {

    /** Creates new form ResolveTreeConflictPanel */
    public ResolveTreeConflictPanel (File file, String relPath, Type conflictType) {
        initComponents();
        lblFile.setText(NbBundle.getMessage(ResolveTreeConflictPanel.class, "MSG_ResolveTreeConflictPanel.file.text", file.getName()));
        txtFilePath.setText(relPath);
        lblFileStatus.setText(NbBundle.getMessage(ResolveTreeConflictPanel.class, "MSG_ResolveTreeConflictPanel.fileStatus.text", conflictType.getDescription()));
    }

    @Override
    public void addNotify () {
        super.addNotify();
        txtFilePath.setCaretPosition(txtFilePath.getText().length());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblFile = new javax.swing.JLabel();
        txtFilePath = new javax.swing.JTextField();
        lblFileStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(lblFile, org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.lblFile.text")); // NOI18N

        txtFilePath.setEditable(false);
        txtFilePath.setBorder(null);

        org.openide.awt.Mnemonics.setLocalizedText(lblFileStatus, org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.lblFileStatus.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.jLabel1.text")); // NOI18N

        buttonGroup1.add(rbRemove);
        org.openide.awt.Mnemonics.setLocalizedText(rbRemove, org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.rbRemove.text")); // NOI18N
        rbRemove.setToolTipText(org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.rbRemove.TTtext")); // NOI18N

        buttonGroup1.add(rbAdd);
        org.openide.awt.Mnemonics.setLocalizedText(rbAdd, org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.rbAdd.text")); // NOI18N
        rbAdd.setToolTipText(org.openide.util.NbBundle.getMessage(ResolveTreeConflictPanel.class, "ResolveTreeConflictPanel.rbAdd.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFile)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbAdd)
                            .addComponent(rbRemove))))
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFileStatus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFileStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbAdd)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFile;
    private javax.swing.JLabel lblFileStatus;
    final javax.swing.JRadioButton rbAdd = new javax.swing.JRadioButton();
    final javax.swing.JRadioButton rbRemove = new javax.swing.JRadioButton();
    private javax.swing.JTextField txtFilePath;
    // End of variables declaration//GEN-END:variables

}
