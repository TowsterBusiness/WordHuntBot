package org.example;

import java.util.HashMap;
import java.util.List;

public class LetterNode {
    public char letter;
    public HashMap<Character, LetterNode> children = new HashMap<>();
    public boolean isWord = false;

    public LetterNode(char letter) {
        this.letter = letter;
    }

    public void addWord(String word) {
        if (word.isEmpty()) {
            isWord = true;
            return;
        };
        char nextChar = word.charAt(0);
        if (!children.containsKey(nextChar)) {
            children.put(nextChar, new LetterNode(nextChar));
        }
        children.get(nextChar).addWord(word.substring(1));
    }

    public boolean contains(String word) {
        char thisChar = word.charAt(0);
        if (children.containsKey(thisChar)) {
            if (word.length() <= 1) return true;
            return children.get(thisChar).contains(word.substring(1));
        }
        return false;
    }

    public boolean containsWord(String word) {
        if (word.isEmpty()) {
            return isWord;
        }
        char thisChar = word.charAt(0);
        if (children.containsKey(thisChar)) {
            return children.get(thisChar).containsWord(word.substring(1));
        }
        return false;
    }
}
