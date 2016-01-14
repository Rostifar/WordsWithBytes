package com.rostifar.scrabbleproject.dictionary;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Locale;



/**
 * A class that uses a Jersey based web service client look up words and their definitions via
 * the services.aonaware.com dictionary web service.
 * Implements the Dictionary interface so it can be "plugged in" to the Scrabble game.
 * Created by GitLazy (Dad) on 12/28/2015.
 */

public class WebServiceBasedDictionary implements Dictionary {

    public boolean initialize() {
        Client client = ClientBuilder.newClient(); /*.register(MyClientResponseFilter.class)*/

        WebTarget target = client.target("http://services.aonaware.com");
        target.path("/DictService/DictService.asmx/Define")
              .queryParam("word", "asset") //Lookup the word "asset"
              .request(MediaType.APPLICATION_XML);


        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_XML);
        /*invocationBuilder.header("some-header", "true");*/
        Response response = invocationBuilder.get();
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));
        return response.getStatus() == 200;
    }

    @Override
    public boolean isValidWord(String aWord) {
        return false;
    }

    @Override
    public String getDefinitionForWord(String aWord) throws Exception {
        return null;
    }

    @Override
    public Locale getLanguage() {
        return null;
    }

    private class ClientConfig {
    }
}
