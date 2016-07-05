package handlers;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;
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
    private ScrabbleGameManager gameManager;
    private Gson gson = new Gson();

    @Inject
    BroadcasterFactory broadcasterFactory;


    //action when connection is made to the backend

    @Override
    public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
        HttpSession session = atmosphereResource.getRequest().getSession();
        String gameCode = (String)session.getAttribute("GameCode");
        Broadcaster gameBroadcaster = broadcasterFactory.lookup(gameCode, true);
        atmosphereResource.suspend();

        gameBroadcaster.addAtmosphereResource(atmosphereResource);
        atmosphereResource.setBroadcaster(gameBroadcaster);
        gameBroadcaster.broadcast("taken");

        System.out.println("Called:" + this.getClass().getName());
    }

    //called when Broadcaster broadcasts an event
    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {
        AtmosphereResource r = atmosphereResourceEvent.getResource();
        AtmosphereResponse res = r.getResponse();
        HttpSession session = r.getRequest().getSession();
        String gameCode = (String)session.getAttribute("GameCode");

        if (atmosphereResourceEvent.isSuspended()) {
            String messageJson = gson.toJson(ScrabbleGameCache.lookupGame(gameCode).getGameManager());
            res.getWriter().write(messageJson);
        }
    }

    @Override
    public void destroy() {

    }
}
