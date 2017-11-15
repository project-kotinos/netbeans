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
package org.netbeans.modules.nativeexecution.support;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/* https://jna.dev.java.net/ */
/* http://golesny.de/wiki/code:javahowtogetpid */

public interface Win32APISupport extends StdCallLibrary {

    final Map<String, Object> DEFAULT_OPTIONS = Collections.unmodifiableMap(new HashMap<String, Object>() {

        {
            put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
            put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
        }
    });
    final Win32APISupport instance = (Win32APISupport) Native.loadLibrary("kernel32", Win32APISupport.class, DEFAULT_OPTIONS); // NOI18N

    /* http://msdn.microsoft.com/en-us/library/ms683179(VS.85).aspx */
    HANDLE GetCurrentProcess();

    /* http://msdn.microsoft.com/en-us/library/ms683215.aspx */
    int GetProcessId(HANDLE process);

    public class HANDLE extends PointerType {

        @Override
        public Object fromNative(Object nativeValue, FromNativeContext context) {
            Object o = super.fromNative(nativeValue, context);
            if (InvalidHandle.equals(o)) {
                return InvalidHandle;
            }
            return o;
        }
    }
    public final static HANDLE InvalidHandle = new HANDLE() {

        {
            super.setPointer(Pointer.createConstant(-1));
        }

        @Override
        public void setPointer(Pointer p) {
            throw new UnsupportedOperationException();
        }
    };
}
    
