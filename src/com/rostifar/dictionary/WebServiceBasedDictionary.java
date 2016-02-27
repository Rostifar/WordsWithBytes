package com.rostifar.dictionary;

import com.rostifar.gamecontrol.ScrabbleGameException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.StAXStreamBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * A class that uses a web service client look up words and their definitions via
 * the services.aonaware.com dictionary web service.
 * Implements the Dictionary interface so it can be "plugged in" to the Scrabble game.
 * Created by GitLazy (Dad) on 12/28/2015.
 *
 * Example resultant XML using "Cat" as the lookup word:
 *
 */

public class WebServiceBasedDictionary extends AbstractDictionary implements Dictionary {
    private String urlString;
    public final static String DICTIONARY_ID = "gcide";  //Collaborative International Dictionary of English


    HttpURLConnection conn;
    String result = "";

    @Override
    protected Dictionary initialize() throws Exception{

        urlString = "http://services.aonaware.com/DictService/DictService.asmx/DefineInDict?dictId=" + DICTIONARY_ID;
      //  invokeService("cat"); //invoke the service to test that it will work
        return this;
    }

    /**
     * Invoke the web service to validate the word and fetch the various definitions for the word.
     * Note: we are not using any external libraries to make this web service client call.
     * @param lookupWord the word to lookup in the dictionary
     * @return not sure yet
     */
    private String invokeService(String lookupWord) {
        try {
            result = "";
            URL url = new URL(urlString + "&word=" + lookupWord);
         /*   Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.xxx.com", 8080));
            conn = (HttpURLConnection) url.openConnection(proxy);*/
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/xml");
            //conn.set

            //conn = url.openConnection(proxy);
            conn.connect();

            System.out.println("Invoking: " + url);

            if (conn.getResponseCode() != 200) {
                InputStream error = conn.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(error));
                String msg;
                while ((msg =reader.readLine()) != null)
                    System.out.println(msg);
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Consuming output from Server .... \n");

            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                result += output;
            }

            conn.disconnect();

        } catch (IOException ioExp) {
            ioExp.printStackTrace();

            if (conn != null)
                conn.disconnect();
        }

        return result;
    }

    @Override
    public DictionaryLookupResult lookupWord(String lookupWord) throws ScrabbleGameException {
        return parseResult(lookupWord, invokeService(lookupWord));
    }

    /**
     * Example XML tto be parsed:
     *
     <?xml version="1.0" encoding="utf-8"?>
     <WordDefinition xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://services.aonaware.com/webservices/">
        <Word>dog</Word>
        <Definitions>
            <Definition>
                <Word>dog</Word>
                <Dictionary>
                    <Id>gcide</Id>
                    <Name>The Collaborative International Dictionary of English v.0.44</Name>
                </Dictionary>
                <WordDefinition>Sundog \Sun"dog`\, n. (Meteorol.)
                     1. A luminous spot occasionally seen a few degrees from the
                     sun, supposed to be formed by the intersection of two or
                     more halos, or in a manner similar to that of halos.
                     [1913 Webster]

                     2. A fragmentary rainbow; a small rainbow near the horizon;
                     -- called also {dog} and {weathergaw}.
                     [Webster 1913 Suppl.]
                </WordDefinition>
            </Definition>
        </Definitions>
    </WordDefinition>


     *
     * @param lookupWord
     * @param xmlResult
     * @return
     * @throws Exception
     */
    private DictionaryLookupResult parseResult(String lookupWord, String xmlResult) throws ScrabbleGameException {
        DictionaryLookupResult lookupResult = new DictionaryLookupResult(lookupWord);
        XMLStreamReader xmlStreamReader;
        StAXStreamBuilder builder;
        Document jdomDoc;

        try {
        XMLInputFactory factory = XMLInputFactory.newFactory();
            xmlStreamReader = factory.createXMLStreamReader(new StringReader(xmlResult));
            builder = new StAXStreamBuilder();
            jdomDoc = builder.build(xmlStreamReader);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ScrabbleGameException(exp.getMessage());
        }

        Element wroot = jdomDoc.getRootElement();

        if (wroot.getChildren().isEmpty()) {
            return lookupResult; //No results were found
        }

        XPathFactory xFactory = XPathFactory.instance();
        Filter filter = new ElementFilter();
        XPathExpression expr = xFactory.compile("//dict:Definitions/dict:Definition/dict:WordDefinition", filter,
                null, Namespace.getNamespace("dict", "http://services.aonaware.com/webservices/"));
        List<Element> links = expr.evaluate(jdomDoc);

        WordDefinitions definitions = new WordDefinitions();

        for (Element link : links) {
            System.out.println("Definition: " + link.getValue());
            lookupResult.getDefinitions().addDefinition(link.getValue());
        }

        return lookupResult;
    }

}
