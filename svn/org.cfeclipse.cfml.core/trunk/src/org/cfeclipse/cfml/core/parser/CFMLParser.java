package org.cfeclipse.cfml.core.parser;

/*
Copyright (c) 2007 Mark Mandel, Mark Drew

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.	
*/

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.cfeclipse.cfml.core.parser.antlr.ANTLRNoCaseStringStream;

import java.util.*;

/**
 * @author mark
 *
 */
public class CFMLParser extends org.cfeclipse.cfml.core.parser.antlr.CFMLParser implements IErrorObserver
{
	private ErrorObservable observable;
	private ICFMLDictionary dictionary;
	
	public CFMLParser(TokenStream input, ICFMLDictionary dictionary)
	{
		super(input);
		setObservable(new ErrorObservable());
		setDictionary(dictionary);
	}
	
	/**
	 * Add a error event observer
	 * @param observer the error observer
	 */
	public void addObserver(IErrorObserver observer)
	{
		getObservable().addObserver(observer);
	}
	
	/**
	 * Remove an error observer
	 * @param observer the error observer
	 */
	public void removeObserver(IErrorObserver observer)
	{
		getObservable().removeObserver(observer);
	}
	
	public void displayRecognitionError(String[] tokenNames, RecognitionException e)
	{
		reportError(e, getErrorMessage(e, tokenNames));
		
		super.displayRecognitionError(tokenNames, e);
	}
	
	protected void reportError(RecognitionException e, String errorMessage)
	{
		ErrorEvent event = new ErrorEvent(e, errorMessage);
		
		getObservable().notifyObservers(event);		
	}

	public void actionCFMLParserError(ErrorEvent event)
	{
		//bubble it up
		getObservable().notifyObservers(event);		
	}

	protected boolean containsCFScript(String name)
	{
		return getDictionary().conatinsCFScript(name);
	}	
	
	protected boolean isColdFusionTag(String name)
	{
		//strip off the top layer
		return getDictionary().isColdFusionTag(name);
	}

	protected boolean isCustomTag(String name)
	{
		return name.startsWith("cf_");
	}

	protected boolean isImportTag(String name)
	{
		return name.contains(":");
	}
	
	protected boolean allowsCFMLAssignment(String tagName)
	{
		return getDictionary().allowsCFMLAssignment(tagName);
	}

	protected boolean allowsCFMLCondition(String tagName)
	{
		return getDictionary().allowsCFMLCondition(tagName);
	}

	protected boolean usesAttributes(String name)
	{
		return getDictionary().usesAttributes(name);
	}

	/**
	 * Island parser for CFScript blocks
	 * @param start the token that it starts at
	 * @param stop the token that the cfscript stops at
	 */
	protected Tree parseCFScript(Token start, Token stop)
	{
		Tree ast = null;
		org.antlr.runtime.BitSet bit = new org.antlr.runtime.BitSet();
		bit.add(OTHER);
		List otherTokens = ((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex(), bit);
		
		//in case something goes wrong.
		if(otherTokens == null)
		{
			return ast;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		for(Object t : otherTokens)
		{
			buffer.append(((Token)t).getText());
		}
		
		CharStream input = new ANTLRNoCaseStringStream(buffer.toString());
        CFScriptLexer lexer = new CFScriptLexer(input);
        
        lexer.addObserver(this);
        
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CFScriptParser parser = new CFScriptParser(tokens);
        
        parser.addObserver(this);
        
        try
        {
        	CFScriptParser.script_return root = parser.script();
        	ast = (Tree)root.getTree();
        }
        catch(RecognitionException exc)
        {
        	ErrorEvent event = new ErrorEvent(exc, "CFScript Error");
        	getObservable().notifyObservers(event);
        }
        
        lexer.removeObserver(this);
        parser.removeObserver(this);
		
		return ast;
	}	
	
	private ErrorObservable getObservable()
	{
		return observable;
	}

	private void setObservable(ErrorObservable observable)
	{
		this.observable = observable;
	}

	private ICFMLDictionary getDictionary()
	{
		return dictionary;
	}

	private void setDictionary(ICFMLDictionary dictionary)
	{
		this.dictionary = dictionary;
	}
}