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

package org.netbeans.modules.java.hints;

import java.util.prefs.Preferences;


/**
 * Options Customiser panel for {@link WrongStringComparison} hint
 *
 * @author Mark Lewis
 */
public class WrongStringComparisonCustomizer extends javax.swing.JPanel {

    private Preferences p;

    /** Creates new customizer WrongStringComparisonCustomizer */
    public WrongStringComparisonCustomizer(Preferences p) {
        initComponents();
        this.p = p;
        ternaryNullCheck.setSelected(WrongStringComparison.getTernaryNullCheck(p));
        stringLiteralsFirst.setSelected(WrongStringComparison.getStringLiteralsFirst(p));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ternaryNullCheck = new javax.swing.JCheckBox();
        stringLiteralsFirst = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(346, 60));

        ternaryNullCheck.setText(org.openide.util.NbBundle.getMessage(WrongStringComparisonCustomizer.class, "WrongStringComparisonCustomizer.ternaryNullCheck.text")); // NOI18N
        ternaryNullCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ternaryNullCheckActionPerformed(evt);
            }
        });

        stringLiteralsFirst.setText(org.openide.util.NbBundle.getMessage(WrongStringComparisonCustomizer.class, "WrongStringComparisonCustomizer.stringLiteralFirst.text")); // NOI18N
        stringLiteralsFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringLiteralsFirstActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ternaryNullCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stringLiteralsFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ternaryNullCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stringLiteralsFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ternaryNullCheck.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WrongStringComparisonCustomizer.class, "ACSD_Ternary_Null_Check")); // NOI18N
        stringLiteralsFirst.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WrongStringComparisonCustomizer.class, "ACSD_String_Literals_First")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void ternaryNullCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ternaryNullCheckActionPerformed
        WrongStringComparison.setTernaryNullCheck(p, ternaryNullCheck.isSelected());
    }//GEN-LAST:event_ternaryNullCheckActionPerformed

    private void stringLiteralsFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stringLiteralsFirstActionPerformed
        WrongStringComparison.setStringLiteralsFirst(p, stringLiteralsFirst.isSelected());
    }//GEN-LAST:event_stringLiteralsFirstActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox stringLiteralsFirst;
    private javax.swing.JCheckBox ternaryNullCheck;
    // End of variables declaration//GEN-END:variables

}
