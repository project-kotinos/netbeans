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


package org.netbeans.modules.i18n;


import java.awt.Component;
import java.awt.event.KeyEvent;
import java.beans.FeatureDescriptor;
import java.beans.PropertyEditorSupport;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import org.openide.awt.Mnemonics;
import org.openide.util.HelpCtx;
import org.openide.ErrorManager;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.nodes.Node;


/**
 * Custom editor for editing string type formats with help pattern descritions.
 *
 * @author  Peter Zavadsky
 */
public class HelpStringCustomEditor extends JPanel {

    /** Creates new form CodeCustomEditor.
     * @param value value to be customized 
     * @param items for sleecteing in combo box
     * @param helpItems patterns described in list
     * @param comboText label for the combo-box, optionally with an ampersand marking the mnemonic character
     */
    public HelpStringCustomEditor(String value, List items, List helpItems, String comboText, String helpID) {
        initComponents();
        
        combo.setModel(new DefaultComboBoxModel(items.toArray()));
        combo.setSelectedItem(value);

        list.setListData(helpItems.toArray());
        list.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField..disabledBackground")); // NOI18N
//        list.setBackground(new Color(SystemColor.window.getRGB()));
        
        Mnemonics.setLocalizedText(comboLabel, comboText);
        Mnemonics.setLocalizedText(listLabel, I18nUtil.getBundle().getString("LBL_Arguments"));
        
        initAccessibility ();     
        
        HelpCtx.setHelpIDString(this, helpID);
    }

    /**
    * @return property value that is result of <code>CodeCustomEditor</code>.
    */
    public Object getPropertyValue() {
        return ((JTextField) combo.getEditor().getEditorComponent()).getText();
    }
    
    
    private void initAccessibility() {
        this.getAccessibleContext().setAccessibleDescription(I18nUtil.getBundle().getString("ACS_HelpStringCustomEditor"));
        combo.getAccessibleContext().setAccessibleDescription(I18nUtil.getBundle().getString("ACS_HelpStringCombo"));
        list.getAccessibleContext().setAccessibleDescription(I18nUtil.getBundle().getString("ACS_HelpStringList"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        combo = new javax.swing.JComboBox();
        scrollPane = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        comboLabel = new javax.swing.JLabel();
        listLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        combo.setEditable(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        add(combo, gridBagConstraints);

        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listKeyPressed(evt);
            }
        });
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMouseClicked(evt);
            }
        });

        scrollPane.setViewportView(list);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        add(scrollPane, gridBagConstraints);

        comboLabel.setLabelFor(combo);
        comboLabel.setText("comboLabel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(comboLabel, gridBagConstraints);

        listLabel.setLabelFor(list);
        listLabel.setText("listLabel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 11);
        add(listLabel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void listKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listKeyPressed
        String selected = (String)list.getSelectedValue();
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && selected != null) {
            evt.consume();
            insertInFormat(selected);
        }
    }//GEN-LAST:event_listKeyPressed

    private void listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMouseClicked
        String selected = (String)list.getSelectedValue();
        if(evt.getClickCount() == 2 && selected != null) {
            insertInFormat(selected);
        }
    }//GEN-LAST:event_listMouseClicked

    /** Helper method. */
    private void insertInFormat(String selected) {
        int index = selected.indexOf(' ');
        
        if(index < 0 || index > selected.length())
            return;
        
        String replace = selected.substring(0, index);
        
        JTextField textField = (JTextField)combo.getEditor().getEditorComponent();
        try {
            textField.getDocument().insertString(textField.getCaretPosition(), replace, null); // NOI18N
        } catch(BadLocationException ble) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ble);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combo;
    private javax.swing.JLabel comboLabel;
    private javax.swing.JList list;
    private javax.swing.JLabel listLabel;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

    /** Nested class. <code>PropertyEditor</code>. 
     * @see I18nOptions#PROP_INIT_JAVA_CODE */
    public static class InitCodeEditor extends StringEditor {
        /** Overrides superclass method. */
        public Component getCustomEditor() {
            return new HelpStringCustomEditor(
                (String)getValue(), 
                I18nUtil.getInitFormatItems(), 
                I18nUtil.getInitHelpItems(),
                I18nUtil.getBundle().getString("LBL_InitCodeFormat"),
                I18nUtil.PE_BUNDLE_CODE_HELP_ID
            );
        }
    } // End of nested class InitCodeEditor.
    
    /** Nested class. <code>PropertyEditor</code>.
     * @see I18nOptions#PROP_INIT_REPLACE_CODE */
    public static class ReplaceCodeEditor extends StringEditor {
        /** Overrides superclass method. */
        public Component getCustomEditor() {
            return new HelpStringCustomEditor(
                (String)getValue(),
                I18nUtil.getReplaceFormatItems(),
                I18nUtil.getReplaceHelpItems(),
                I18nUtil.getBundle().getString("LBL_ReplaceCodeFormat"),
                I18nUtil.PE_REPLACE_CODE_HELP_ID
            );
        }
    } // End of nested class ReplaceCodeEditor.

    private static abstract class StringEditor extends PropertyEditorSupport implements ExPropertyEditor {
        private static boolean useRaw = Boolean.getBoolean("netbeans.stringEditor.useRawCharacters");

       // bugfix# 9219 added editable field and isEditable() "getter" to be used in StringCustomEditor    
        private boolean editable=true;   
        /** gets information if the text in editor should be editable or not */
        public boolean isEditable(){
            return (editable);
        }

        /** sets new value */
        public void setAsText(String s) {
            if ( "null".equals( s ) && getValue() == null ) // NOI18N
                return;
            setValue(s);
        }

        public String getJavaInitializationString () {
            String s = (String) getValue ();
            return "\"" + toAscii(s) + "\""; // NOI18N
        }

        public boolean supportsCustomEditor () {
            return customEd;
        }

        public abstract java.awt.Component getCustomEditor ();

        private static String toAscii(String str) {
            StringBuffer buf = new StringBuffer(str.length() * 6); // x -> \u1234
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                switch (c) {
                case '\b': buf.append("\\b"); break; // NOI18N
                case '\t': buf.append("\\t"); break; // NOI18N
                case '\n': buf.append("\\n"); break; // NOI18N
                case '\f': buf.append("\\f"); break; // NOI18N
                case '\r': buf.append("\\r"); break; // NOI18N
                case '\"': buf.append("\\\""); break; // NOI18N
                    //        case '\'': buf.append("\\'"); break; // NOI18N
                case '\\': buf.append("\\\\"); break; // NOI18N
                default:
                    if (c >= 0x0020 && (useRaw || c <= 0x007f))
                        buf.append(c);
                    else {
                        buf.append("\\u"); // NOI18N
                        String hex = Integer.toHexString(c);
                        for (int j = 0; j < 4 - hex.length(); j++)
                            buf.append('0');
                        buf.append(hex);
                    }
                }
            }
            return buf.toString();
        }

        private String instructions=null;
        private boolean oneline=false;
        private boolean customEd=true;
        private PropertyEnv env;

        // bugfix# 9219 added attachEnv() method checking if the user canWrite in text box 
        public void attachEnv(PropertyEnv env) {
            this.env = env;

            FeatureDescriptor desc = env.getFeatureDescriptor();
            if (desc instanceof Node.Property){
                Node.Property prop = (Node.Property)desc;
                editable = prop.canWrite();
                //enh 29294 - support one-line editor & suppression of custom
                //editor
                instructions = (String) prop.getValue ("instructions"); //NOI18N
                oneline = Boolean.TRUE.equals (prop.getValue ("oneline")); //NOI18N
                customEd = !Boolean.TRUE.equals (prop.getValue 
                    ("suppressCustomEditor")); //NOI18N
            }
        }
    }
    
}
