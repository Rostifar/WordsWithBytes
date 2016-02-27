package com.rostifar.dictionary;

/**
 * Created by GitLazy on 1/23/2016.
 */
public class DictionaryLookupResult {
    private String word;
    private boolean found = false;
    WordDefinitions definitions;

    protected DictionaryLookupResult(String word, WordDefinitions definitions) {
        this.word = word;
        this.definitions = definitions;
        found = true;
    }

    protected DictionaryLookupResult(String word) {
        found = false;
        this.word = word;
        definitions = new WordDefinitions();
    }

    public boolean isValidWord() {
        return found;
    }

    public void setIsValidWord(boolean isFound) {
        found = isFound;
    }

    public WordDefinitions getDefinitions() {
        return definitions;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Word:");
        stringBuilder.append(word).append(" Found: ").append(isValidWord());

        return stringBuilder.toString();
    }
}
