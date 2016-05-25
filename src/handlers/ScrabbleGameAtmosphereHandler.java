package handlers;

import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

import java.io.IOException;

/**
 * Created by Dad on 5/25/2016.
 */
public class ScrabbleGameAtmosphereHandler implements AtmosphereHandler {

        @Override
        public void onRequest(AtmosphereResource r)
        throws IOException {
            r.getBroadcaster().broadcast(r.getRequest().getReader().readLine());
        }

        @Override
        public void onStateChange(AtmosphereResourceEvent event) throws IOException {
            AtmosphereResource r = event.getResource();
            if (event.isSuspended()) {
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

