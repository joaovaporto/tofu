package io.tofu.commons;

import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import junit.framework.TestCase;

public class EqualityTest extends TestCase {
    public void testSymbolEqualsWithNull() {
        Symbol<String, String> sym = new Symbol<>("A", "a");
        assertFalse(sym.equals(null));
    }

    public void testSymbolEqualsWithDifferentClass() {
        Symbol<String, String> sym = new Symbol<>("A", "a");
        assertFalse(sym.equals("A"));
    }

    public void testSymbolEqualsTrue() {
        Symbol<String, String> s1 = new Symbol<>("A", "a");
        Symbol<String, String> s2 = new Symbol<>("A", "a");
        assertTrue(s1.equals(s2));
    }

    public void testTerminalEqualsWithNull() {
        Terminal<String, String> t = new Terminal<>("A");
        assertFalse(t.equals(null));
    }

    public void testTerminalEqualsWithDifferentClass() {
        Terminal<String, String> t = new Terminal<>("A");
        assertFalse(t.equals("A"));
    }

    public void testTerminalEqualsTrue() {
        Terminal<String, String> t1 = new Terminal<>("A");
        Terminal<String, String> t2 = new Terminal<>("A");
        assertTrue(t1.equals(t2));
    }
}
