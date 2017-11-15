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
package org.netbeans.jellytools.properties;

import java.io.File;
import org.netbeans.jellytools.NbDialogOperator;
import org.netbeans.jellytools.properties.editors.FileCustomEditorOperator;

/** Operator serving property of type File
 * @author <a href="mailto:adam.sotona@sun.com">Adam Sotona</a> */
public class FileProperty extends Property {

    /** Creates a new instance of FileProperty
     * @param propertySheetOper PropertySheetOperator where to find property.
     * @param name String property name
     */
    public FileProperty(PropertySheetOperator propertySheetOper, String name) {
        super(propertySheetOper, name);
    }
    
    /** invokes custom property editor and returns proper custom editor operator
     * @return FileCustomEditorOperator */    
    public FileCustomEditorOperator invokeCustomizer() {
        openEditor();
        return new FileCustomEditorOperator(getName());
    }
    
    /** setter for File value through Custom Editor
     * @param filePath String file path */    
    public void setFileValue(String filePath) {
        FileCustomEditorOperator customizer=invokeCustomizer();
        customizer.setFileValue(filePath);
        customizer.ok();
    }        
    
    /** setter for File value through Custom Editor
     * @param value File */    
    public void setFileValue(File value) {
        FileCustomEditorOperator customizer=invokeCustomizer();
        customizer.setFileValue(value);
        customizer.ok();
    }        
    
    /** getter for File value through Custom Editor
     * @return File */    
    public File getFileValue() {
        File value;
        FileCustomEditorOperator customizer=invokeCustomizer();
        value=customizer.getFileValue();
        customizer.close();
        return value;
    }

    /** Performs verification by accessing all sub-components */    
    public void verify() {
        invokeCustomizer().verify();
        new NbDialogOperator(getName()).close();
    }        
}
