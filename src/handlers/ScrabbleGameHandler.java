package handlers;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.servlets.ScrabbleServletHelper;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.*;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by rostifar on 6/14/16.
 */
@AtmosphereHandlerService
public class ScrabbleGameHandler implements AtmosphereHandler {

    @Inject
    BroadcasterFactory broadcasterFactory;

    //action when connection is made to the backend

    @Override
    public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
        HttpSession session = atmosphereResource.getRequest().getSession();
        Gson gson = new Gson();
        atmosphereResource.suspend();
        String gameCode = (String)session.getAttribute("GameCode");
        Broadcaster gameBroadcaster = broadcasterFactory.lookup(gameCode, true);
        gameBroadcaster.addAtmosphereResource(atmosphereResource);
        atmosphereResource.setBroadcaster(gameBroadcaster);
        gameBroadcaster.broadcast(gson.toJson(ScrabbleGameCache.lookupGame(gameCode).getGameManager()));
        System.out.println("Called:" + this.getClass().getName());
    }

    //called when Broadcaster broadcasts an event
    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {

    }

    @Override
    public void destroy() {

    }
}
