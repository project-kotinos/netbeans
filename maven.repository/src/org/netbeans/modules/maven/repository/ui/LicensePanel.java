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
 * LicensePanel.java
 *
 * Created on Dec 28, 2008, 9:25:33 AM
 */

package org.netbeans.modules.maven.repository.ui;

import javax.swing.UIManager;

/**
 *
 * @author mkleint
 */
public class LicensePanel extends javax.swing.JPanel {

    /** Creates new form LicensePanel */
    public LicensePanel() {
        initComponents();
        if( "Aqua".equals(UIManager.getLookAndFeel().getID()) ) { //NOI18N
            setBackground(UIManager.getColor("NbExplorerView.background")); //NOI18N
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblURL = new javax.swing.JLabel();
        btnURL = new javax.swing.JButton();

        lblName.setText(org.openide.util.NbBundle.getMessage(LicensePanel.class, "LicensePanel.lblName.text")); // NOI18N

        txtName.setEditable(false);

        lblURL.setText(org.openide.util.NbBundle.getMessage(LicensePanel.class, "LicensePanel.lblURL.text")); // NOI18N

        btnURL.setText("license url"); // NOI18N
        btnURL.setBorder(null);
        btnURL.setBorderPainted(false);
        btnURL.setContentAreaFilled(false);
        btnURL.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblURL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnURL, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblURL)
                    .addComponent(btnURL))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnURL;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblURL;
    public javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

}
