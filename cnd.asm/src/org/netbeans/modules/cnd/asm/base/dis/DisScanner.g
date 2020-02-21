/*
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

header {
package org.netbeans.modules.cnd.asm.base.generated;

import org.netbeans.modules.cnd.asm.base.dis.*;
import org.netbeans.modules.cnd.asm.base.syntax.*;

}

{
@SuppressWarnings({"unchecked", "cast", "fallthrough"})
}
class DisScanner extends Lexer;
options {
    k = 3;  
    importVocab=Antlr;    
    testLiterals = false;
    charVocabulary = '\0'..'\ufffe';
    classHeaderSuffix = "AntlrScanner";
    filter = true;
}


{
    private enum InternalState {
        INITAL, OP_CODES, INSTRUCTIONS;
    };
   
    private InternalState intState = InternalState.INITAL;

    private String commentLiterals = ";/!";

    private PartState partState =  AntlrScanner.PartState.DEFAULT;

    public void setCommentLiterals(String commentLiterals) {
        this.commentLiterals = commentLiterals;
    }

    public void setIntState(Object state) {
        if (state instanceof InternalState) {
            intState = (InternalState) state;
        }
    }

    public Object getIntState() {
        return intState;
    }

    public PartState getPartState() {
        return partState;
    }

    public int getOffset() {
        return offset;
    }

    public Token createToken(int type) {
        Token t = new AntlrToken();
        t.setType(type);
        return t;
    }

    public void resetText() {
        super.resetText();
        tokenStartOffset = offset;
    }
   
    public void consume() {
        super.consume();
        if (guessing == 0) {
            offset++;
        }
    }

    public int mark() {
        mkOffset = offset;
        return super.mark(); 
    }

    public void rewind(int mark) {
        super.rewind(mark);
        offset = mkOffset;
    }
   

    int offset = 0;
    int tokenStartOffset = 0;
    int mkOffset = 0;

    protected Token makeToken(int t) {
        Token tok = super.makeToken(t);
        AntlrToken k = (AntlrToken)tok;
        k.setStartOffset(tokenStartOffset);
        k.setEndOffset(offset);      
        return k;
    }
    
    private void deferredNewline() 
    {
        super.newline();
    }
   
}


protected
COMMA      options { constText=true; } : ',' ;


protected
STAR        options { constText=true; }  : '*' ;


protected
AT          options { constText=true; }  : '@' ;

protected 
EQ          options { constText=true; }  : '=' ;


protected
PLUS        options { constText=true; }  : '+' ;


protected
TILDE       options { constText=true; }  : '~' ;

protected 
FLSQUARE    options { constText=true; }  : '{' ;

protected 
FRSQUARE    options { constText=true; }  : '}' ;

protected 
DOLLAR      options { constText=true; }  : '$' ;

protected 
PERCENT     options { constText=true; }  : '%' ;

protected 
COLON       options { constText=true; }  : ':' ;

protected
BITWISEOR   options { constText=true; } :  '^' ;

protected 
QUESTIONMARK options { constText=true; }  : '?' ;

protected
AMPERSAND    options { constText=true; }  : '&';

protected 
LESS         options { constText=true; }  : '<' ;

protected 
OR           options { constText=true; }  : '|' ;

protected 
UPPER        options { constText=true; }  : '>' ;

protected
MINUS       options { constText=true; }  : '-' ;


protected
LPAREN      options { constText=true; }  : '(' ;


protected
RPAREN      options { constText=true; }  : ')' ;


protected
LSPAREN     options { constText=true; }  : '[' ;


protected
RSPAREN     options { constText=true; }  : ']' ;

Mark        : RSPAREN | LSPAREN | RPAREN | LPAREN | MINUS | PLUS |
              AT  | STAR | COMMA | EQ | LESS | UPPER | DOLLAR |
              BITWISEOR | AMPERSAND | QUESTIONMARK | PERCENT |
              FRSQUARE | FLSQUARE | OR | COLON
            ;


protected
Digit       : '0'..'9'
            ;

protected
HexDigit   : Digit
           | 'a'..'f'
           | 'A'..'F'
           ;

protected  
OctDigit   : '0'..'7'
           ;

protected 
BinDigit    : '0' | '1'
            ;

protected
DecIntegerLiteral   :  '0'
                       |  ('1'..'9') ('0'..'9')*
                       ;

protected    
HexIntegerLiteral      : '0' ('x' | 'X')  (HexDigit)+                     
                       ;
protected
BinIntegerLiteral      : '0' ('b' | 'B') (BinDigit)+
                       ;
 
protected
IntegerLiteral         :                          
                        ( {LA(2)=='b' || LA(2)=='f'}? Digit ('b' | 'f') 
                                              { $setType(DigitLabel); } 
                        )
                       | DecIntegerLiteral                         
                       | HexIntegerLiteral
                       | BinIntegerLiteral
                       ;

NumberOperand          : '$' ('+' | '-')? ('0' 'x')? (HexDigit)+
                       ;
                                            
protected
Exponent               : ('E' | 'e') ('-' | '+')? ('0'..'9')+
                       ;
protected
Escape  
        :'\\'
		('a' | 'b' | 'f' | 'n' | 'r' | 't' | 'v' | '"' | '\'' | '\\' | '?' | 
                    ('0'..'3') (options{greedy=true;}: Digit)? (options{greedy=true;}: Digit)?
		| ('4'..'7') (options{greedy=true;}: Digit)?
		| 'x' (options{greedy=true;}: Digit | 'a'..'f' | 'A'..'F')+
		)
	;

protected
EndOfLine
	:	(	options{generateAmbigWarnings = false;}:
			"\r\n"  {offset--;}// MS
		|	'\r'    // Mac
		|	'\n'    // Unix
		) { intState = InternalState.INITAL; }
	;
 
Whitespace  : (' '
            | '\t'
            | EndOfLine)+
            ;

CharLiteral
        :   
            '\'' (Escape | ~( '\'' | '\\' ))* '\''
        ;

 
StingLiteral
        :
            '"' StringLiteralBody 
        ;



protected
StringLiteralBody
        :
		(       
                        '\\'                        
                        (   options{greedy=true;}:
                            (	"\r\n" // MS 
                            |	"\r"     // MAC
                            |	"\n"     // Unix
                            ) {deferredNewline();}
                        | 
                            '"'
                        |   
                            '\\'    
                        )?
		|	
                         ~('"' | '\r' | '\n' | '\\')
		)*
            '"'
        ;

 
protected                      
Ident_                 : ( ('a'..'z' | 'A'..'Z' | '_' | '.')
                           ('a'..'z' | 'A'..'Z' | '_' | '0'..'9' | '.')* 
                         )     
                       ;

protected
IncompleteCComment:  "/*";
                
protected 
CComment: 
		IncompleteCComment { partState = AntlrScanner.PartState.IN_COMMENT; } 
		( options {greedy=false;}:
			EndOfLine {deferredNewline();}
                        | . )*
		"*/" { partState = AntlrScanner.PartState.DEFAULT; }             
	;



Comment     :     (options{generateAmbigWarnings = false;}:  
                    ({LA(1)=='/' && LA(2)=='*'}? CComment) 
            | 
                 ('/' | ';' | '!' | '#') 
                 ( { if(commentLiterals.indexOf(LA(0)) == -1) { $setType(Mark); } 
                            else  
                   }: (  ~('\n' | '\r') )* )  )
            ;




Ident                  { boolean wasLetters = false; }:
                       ( ( ( 'g'..'z' |
                              'G'..'Z' |
                              '.'      |
                              '_'      |
                              '~'
                             ) { wasLetters = true; }
                           )
                           |
                           HexDigit
                        )
                        ( ( ( 'g'..'z' |
                              'G'..'Z' |
                              '.'      |
                              '_'      |
                              '+'
                             ) { wasLetters = true; }
                           )
                           |
                           HexDigit
                        )* 
                        
                       (( ':' 
                          { $setType(LabelInst);  intState = InternalState.OP_CODES; 
                          } 
                        )                        
                        | 
                        {   String tempText = $getText;
                            if (LA(1) == '(' && LA(2) == ')') {
                                consume(); consume(); 
                                $setType(LabelInst);  intState = InternalState.OP_CODES;
                            }
                            else if ((tempText.startsWith("0x") || Character.isDigit(tempText.charAt(0))) &&
                                intState == InternalState.INSTRUCTIONS) {
                                $setType(IntegerLiteral);
                                
                            }
                            else if(!wasLetters && tempText.length() == 2 && LA(1) == ' ' &&
                                   intState == InternalState.OP_CODES) {                                    
                                    $setType(Directive);                                                                      
                            }
                            else {
                                 intState = InternalState.INSTRUCTIONS;
                            }
                        } 
                       )                                               
                       ;

Register               : '%' Ident_
                       ;