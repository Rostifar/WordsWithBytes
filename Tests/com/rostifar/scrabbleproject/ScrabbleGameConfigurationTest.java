package com.rostifar.scrabbleproject;

import com.rostifar.gamecontrol.ScrabbleGameConfiguration;
import org.junit.Test;

/**
 * Created by D14048 on 2/25/2016.
 */
public class ScrabbleGameConfigurationTest {


    @Test
    public void testLoadProperties() throws Exception {
        ScrabbleGameConfiguration.initialize();
    }

    @Test
    public void testGetDictionaryClassProperty() throws Exception {
        assert (ScrabbleGameConfiguration.getProperties()).getProperty("dictionaryClass") != null;
    }
}