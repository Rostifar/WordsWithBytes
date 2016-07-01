package handlers;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
        AtmosphereResponse response = atmosphereResource.getResponse();

        System.out.println("Called:" + this.getClass().getName());
//        request.get
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("/Index.html");
        try {
            reqDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    //called when Broadcaster broadcasts an event
    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {

    }

    @Override
    public void destroy() {

    }
}
