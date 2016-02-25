package com.rostifar.dictionary;

/**
 * Created by GitLazy on 1/23/2016.
 */
public class DictionaryLookupResult {
    private String word;
    private boolean found;
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

    protected boolean isFound() {
        return found;
    }

    protected WordDefinitions getDefinitions() {
        return definitions;
    }
}
