package handlers;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

import java.io.IOException;

/**
 * Created by rostifar on 6/14/16.
 */
@AtmosphereHandlerService
public class ScrabbleGameHandler implements AtmosphereHandler {

    //action when connection is made to the backend

    @Override
    public void onRequest(AtmosphereResource atmosphereResource) throws IOException {

        AtmosphereRequest request = atmosphereResource.getRequest();

        System.out.println("hi");
    }

    //called when Broadcaster broadcasts an event
    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {

    }

    @Override
    public void destroy() {

    }
}
