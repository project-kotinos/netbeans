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

package org.netbeans.editor;

/**
* Token-id with the fixed token image. The image text
* can be retrieved by <tt>getImage()</tt>.
* The image token-ids are treated specially in some
* editor parts for example in the indentation engine.
*
* @author Miloslav Metelka
* @version 1.00
*/

public interface ImageTokenID extends TokenID {

    /** Get the string image of the token-id. */
    public String getImage();

}
