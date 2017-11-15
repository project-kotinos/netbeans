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

package org.netbeans.lib.lexer.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 * Example of language that could be generated by a lexer generator.
 * <br/>
 * More common is a use of enumerated token ids e.g. {@link SimpleLanguage}.
 *
 * @author mmetelka
 */
public final class TestGenLanguage {
    
    public static final int IDENTIFIER = 0;
    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int PLUS_MINUS_PLUS = 3; // For testing lookahead two
    public static final int SLASH = 4;
    // [TEST] Gap in ordinals allowed
    public static final int STAR = 7;
    public static final int ML_COMMENT = 8;
    public static final int WHITESPACE = 9;
    // [TEST] Gap in ordinals allowed
    public static final int SL_COMMENT = 11;
    public static final int ERROR = 12;
    public static final int PUBLIC = 13;
    public static final int PRIVATE = 14;
    public static final int STATIC = 15;
    
    public static final TokenId IDENTIFIER_ID = LanguageHierarchy.newId("IDENTIFIER", IDENTIFIER);
    public static final TokenId PLUS_ID = LanguageHierarchy.newId("PLUS", PLUS, "operator");
    public static final TokenId MINUS_ID = LanguageHierarchy.newId("MINUS", MINUS, "operator");
    public static final TokenId PLUS_MINUS_PLUS_ID = LanguageHierarchy.newId("PLUS_MINUS_PLUS", PLUS_MINUS_PLUS);
    public static final TokenId SLASH_ID = LanguageHierarchy.newId("SLASH", SLASH, "operator");
    public static final TokenId STAR_ID = LanguageHierarchy.newId("STAR", STAR, "operator");
    public static final TokenId ML_COMMENT_ID = LanguageHierarchy.newId("ML_COMMENT", ML_COMMENT, "comment");
    public static final TokenId WHITESPACE_ID = LanguageHierarchy.newId("WHITESPACE", WHITESPACE);
    public static final TokenId SL_COMMENT_ID = LanguageHierarchy.newId("SL_COMMENT", SL_COMMENT, "comment");
    public static final TokenId ERROR_ID = LanguageHierarchy.newId("ERROR", ERROR, "error");
    public static final TokenId PUBLIC_ID = LanguageHierarchy.newId("PUBLIC", PUBLIC, "keyword");
    public static final TokenId PRIVATE_ID = LanguageHierarchy.newId("PRIVATE", PRIVATE, "keyword");
    public static final TokenId STATIC_ID = LanguageHierarchy.newId("STATIC", STATIC, "keyword");

    private static final Language<TokenId> language = new LanguageHierarchy<TokenId>() {
        @Override
        protected Collection<TokenId> createTokenIds() {
            return Arrays.asList(new TokenId[] {
                IDENTIFIER_ID,
                MINUS_ID, PLUS_ID, SLASH_ID, STAR_ID, // [TEST] reversed ids order should not matter
                SL_COMMENT_ID, PLUS_MINUS_PLUS_ID, ML_COMMENT_ID,
                WHITESPACE_ID, ERROR_ID,
                PUBLIC_ID, PRIVATE_ID, STATIC_ID,
            });
        }
        
        @Override
        protected Map<String,Collection<TokenId>> createTokenCategories() {
            Map<String,Collection<TokenId>> cats = new HashMap<String,Collection<TokenId>>();
            // [TEST] Add extra id into existing category
            cats.put("operator", Arrays.asList(new TokenId[] { PLUS_MINUS_PLUS_ID }));
            // [TEST] Create fresh new categories (one member, two members)
            cats.put("whitespace", Arrays.asList(new TokenId[] { WHITESPACE_ID }));
            cats.put("test-category", Arrays.asList(new TokenId[] { PLUS_ID, MINUS_ID, IDENTIFIER_ID }));
            return cats;
        }

        @Override
        protected Lexer<TokenId> createLexer(LexerRestartInfo<TokenId> info) {
            return null;
        }

        @Override
        protected String mimeType() {
            return "text/x-gen";
        }
    }.language();

    public static Language<TokenId> language() {
        return language;
    }

    private TestGenLanguage() {
        // no instances
    }
    
}
