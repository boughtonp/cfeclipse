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

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import org.antlr.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.cfeclipse.cfml.core.parser.antlr.ANTLRNoCaseStringStream;

public class CFMLASTViewer
{
	private JFrame window;
	private JTextArea text;
	private JTextArea log;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		CFMLASTViewer ast = new CFMLASTViewer();
	}
	
	public CFMLASTViewer()
	{
		window = new JFrame("AST Viewer");
		JFrame logger = new JFrame("Log");
		
		JButton button = new JButton("Parse >>");
		
		window.addWindowListener(new WindowListener() 
										{   
											public void windowClosed(WindowEvent e)
											{
											}
											
											public void windowActivated(WindowEvent e)
											{
											}
												
											public void windowClosing(WindowEvent e)
											{
												System.out.println("closing");
												System.exit(0);
											}
											public void windowDeactivated(WindowEvent e)
											{
											}
											public void windowDeiconified(WindowEvent e)
											{
											}
											public void windowOpened(WindowEvent e)
											{
											}
											public void windowIconified(WindowEvent e)
											{
											}
										}
										);
		window.setSize(300, 300);
		
		window.setLayout(new BorderLayout());
		
		text = new JTextArea();
		window.add(new JScrollPane(text), BorderLayout.CENTER);
		
		window.add(button, BorderLayout.SOUTH);
		
		button.addActionListener(
									new ActionListener()
									{
										public void actionPerformed(ActionEvent event)
										{
											parseCFML(text.getText());
										}
									}
								);
		
		window.setVisible(true);
		
		//do the logger
		
		logger.setSize(300, 600);
		logger.setLocation(320, 0);
		
		log = new JTextArea();
		
		logger.setLayout(new BorderLayout());
		
		logger.add(new JScrollPane(log), BorderLayout.CENTER);
		
		logger.setVisible(true);
	}
	
	private void parseCFML(String cfml)
	{
		try
		{
				//this is pretty quick n' dirty... oh well.
				IErrorObserver observer = new IErrorObserver()
				{
					public void actionCFMLParserError(ErrorEvent event)
					{
						log.append("Error at: " + event.getException().line + ":");
						log.append(event.getException().charPositionInLine + "\n");
						log.append(event.getErrorMsg() + "\n");
					}
				};

				log.append(cfml + "\n");
				CharStream input = new ANTLRNoCaseStringStream(cfml);
		        CFMLLexer lexer = new CFMLLexer(input);
		        
		        lexer.addObserver(observer);
		        
		        CommonTokenStream tokens = new CommonTokenStream(lexer);
		        CFMLParser parser = new CFMLParser(tokens);
		        
		        parser.addObserver(observer);
		        
		        CFMLParser.script_return root = parser.script();
		        
		        Tree ast = (Tree)root.getTree();
		        
		        JFrame astWindow = new JFrame("Tree...");
		        
		        astWindow.setLayout(new BorderLayout());
		        
		        JTree tree = new JTree(buildTree(ast));
		        
		        astWindow.add(new JScrollPane(tree), BorderLayout.CENTER);
		        
		        astWindow.setSize(300, 300);
		        astWindow.setLocation(640, 0);
		        
		        astWindow.setVisible(true);
		        
		        //cleanup afterwoulds
		        lexer.removeObserver(observer);
		        parser.removeObserver(observer);
		}
		catch(Throwable t)
	    {
	        System.out.println("Exception: "+t.getMessage());
	        t.printStackTrace();
	    }
	}
	
	private DefaultMutableTreeNode buildTree(Tree tree)
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("[" + tree.getType() + "] " + tree.getText());
		buildTree(tree, node);
		
		return node;
	}
	
	private void buildTree(Tree tree, DefaultMutableTreeNode node)
	{
		for(int counter = 0; counter < tree.getChildCount(); counter++)
		{
			Tree child = tree.getChild(counter);
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("[" + child.getType() + "] " + child.getText());
			node.add(childNode);
			buildTree(child, childNode);
		}
	}
}
