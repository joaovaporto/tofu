package io.tofu.tepal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.af.dt.DTState;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;
import io.tofu.commons.*;

public class AL {
	private TS ts;
	private Alphabet alphabet;
	private LinkedList<Character> buffer;
	private BufferedReader bufferedReader;
	private int currentLine;
	private int currentColumn;
	private char currentSymbol;
	private String currentLexeme;
	private ArrayList<DT> dts;

	public AL() {
		this.ts = new TS(); // TODO
		this.buffer = new LinkedList<Character>();
		this.alphabet = new Alphabet();
	}
	
	public ArrayList<Token> out(BufferedReader in) throws IOException {
		setBufferedReader(in);
		
		ArrayList<Token> tokens = new ArrayList<Token>();
		setBufferedReader(in);
		
		while (getNextSymbol() != '$')
			try {
				currentLexeme = new String();
				tokens.add(getNextToken());
			} catch (NotATokenException | UnsupportedSymbolException e) {
				System.err.println(e.getMessage());
			}
		
		return tokens;
	}
	
	public Token getNextToken() throws NotATokenException, IOException, UnsupportedSymbolException {
		ArrayList<DT> dtsReading = new ArrayList<DT>();
		ArrayList<DT> dtsNotReading = new ArrayList<DT>();
		
		dtsReading.addAll(dts);
		
		while (dtsReading.size() > 1) {
			for (DT dt : dtsReading) {
				try {
					char c = popNextSymbol();
					currentLexeme = currentLexeme + c;
					DTState s = dt.read(c);
					
					if (s.isDead())
						dtsNotReading.add(dt);
				} catch (OperarMecanismoException e) {}
			}
			
			dtsReading.removeAll(dtsNotReading);
		}
		
		if (dtsReading.size() == 1) {
			DT dt = dtsReading.get(0);
			
			try {
				while (!dt.getCurrState().isAccept()) {
					char c = popNextSymbol();
					currentLexeme = currentLexeme + c;
					DTState s = dt.read(c);
					
					if (s.isDead())
						throw new NotATokenException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
								currentSymbol + "\"" + " something is wrong here.");
				}						
				
				if (dt.getCurrState().isBackable())
					back();
				
				return yieldToken(dt);
			} catch (OperarMecanismoException e) {System.out.println(e.getMessage()); System.exit(0);}
		}
		
		throw new NotATokenException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
				currentSymbol + "\"" + " something is wrong here.");
	}
	
	private Token yieldToken(DT dt) {
		// TODO Here TS begin
		return null;
	}

	public void back() {
		currentSymbol = currentLexeme.charAt(currentLexeme.length() - 1);
		currentLexeme = currentLexeme.substring(0, currentLexeme.length() - 1);
		currentColumn--;
		buffer.addFirst(currentSymbol);
	}
	
	private void setBufferedReader(BufferedReader bufferedReader) throws IOException {
		if (bufferedReader == null)
			throw new IOException("No bufferedReader to read."); // TODO - is it really a IOException?
		
		this.bufferedReader = bufferedReader;
		
		currentLine = 1;
		currentColumn = 0;
	}
	
	public void setBuffer() throws IOException {
		int r = 0;
		
		for (int i = 0; i < 10 && r != -1; i++)
			if ((r = bufferedReader.read()) != -1)
				buffer.add(Character.valueOf((char) r));
		
		if (r == -1) {
			buffer.add(Character.valueOf('$'));
			bufferedReader.close();
		}
	}
	
	private Character popNextSymbol() throws IOException, UnsupportedSymbolException {
		currentColumn++;
		
		if (buffer.isEmpty())
			setBuffer();

		Character c = buffer.pop();
		
		if (!alphabet.contains(String.valueOf(c)))
			throw new UnsupportedSymbolException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
					c + "\"" + " is not a supported symbol.");

		
		if (c.equals(Character.valueOf('\n'))) {
			currentColumn = 0;
			currentLine++;
		}
		
		currentSymbol = c;
		return c;
	}
	
	private Character getNextSymbol() throws IOException {		
		if (buffer.isEmpty())
			setBuffer();

		return buffer.getFirst();
	}
}