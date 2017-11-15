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
package org.netbeans.modules.form;

import java.awt.datatransfer.Transferable;
import javax.swing.JEditorPane;
import org.netbeans.modules.form.project.ClassSource;
import org.netbeans.spi.palette.PaletteActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.Node;

/**
 * Various IDE-specific methods (for which there is no better place)
 * needed by GUI Builder. Implementation of this interface can be obtained
 * from lookup.
 *
 * @author Jan Stola
 */
public interface FormServices {
    JEditorPane createCodeEditorPane();
    void setupCodeEditorPane(JEditorPane editor, FileObject srcFile, int ccPosition);
    PaletteActions createPaletteActions();
    ClassSource getCopiedBeanClassSource(Transferable transferable);
    Node createFormDataNode(FormDataObject formDataObject);
    MultiDataObject.Entry createPrimaryEntry(MultiDataObject obj, FileObject primaryFile);
    boolean isLayoutExtensionsLibrarySupported();
    Class<? extends EditorSupport> getEditorSupportClass(FormDataObject formDataObject);
    EditorSupport createEditorSupport(FormDataObject formDataObject);
}
