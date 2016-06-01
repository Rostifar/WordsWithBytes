package handlers;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;

import java.io.IOException;

/**
 * Created by Dad on 5/25/2016.
 */
@AtmosphereHandlerService(path = "/takeTurn", interceptors = org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor.class)
public class ScrabbleGameAtmosphereHandler implements AtmosphereHandler {

    /**
     * This method broadcasts state changes to other scrabble clients who are listening (participating in the game)?
     */
    @Override
        public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
            Broadcaster broadcaster = atmosphereResource.getBroadcaster();
            String input = atmosphereResource.getRequest().getReader().readLine();
            broadcaster.broadcast(input);
        }

        @Override
        /**
         * This method is called when a client (browser) posts a "takeTurn" action.
         */
        public void onStateChange(AtmosphereResourceEvent event) throws IOException {
            AtmosphereResource atmosphereResource = event.getResource();
            if (event.isSuspended()) { //Client has finished ?
                // THIS IS JUST FOR DEMO, use JACKSON instead.
                String body = event.getMessage().toString();
                String author = body.substring(body.indexOf(":") + 2,
                        body.indexOf(",") - 1);
                String message = body.substring(body.lastIndexOf(":") + 2,
                        body.length() - 2);
                event.setMessage("xxx");
            }
        }

        @Override
        public void destroy() {
        }
}

