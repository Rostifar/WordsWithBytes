package com.rostifar.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GitLazy on 1/23/2016.
 */
public class WordDefinitions {
    public static final String UNKNOWN_SOURCE = "Unknown Source";
    private String dictionaryName = UNKNOWN_SOURCE;
    private List<Object> definitions = new ArrayList<>();

    public WordDefinitions() {

    }

    public WordDefinitions(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public List<Object> getDefinitions() {
        return definitions;
    }

    protected void addDefinition(Object definition) {
        definitions.add(definition);
    }

}
