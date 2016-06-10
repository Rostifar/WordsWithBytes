package handlers;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.servlets.ScrabbleServletHelper;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Dad on 5/25/2016.
 */
@AtmosphereHandlerService(path = "/scrabbleGame", interceptors = org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor.class)
public class ScrabbleGameAtmosphereHandler implements AtmosphereHandler, Serializable {
    private String gameCode;
    BroadcasterFactory broadcasterFactory;
    Broadcaster gameBroadCaster;

    /**
     * This method broadcasts state changes to other scrabble clients who are listening (participating in the game)?
     */
    @Override
    public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
        AtmosphereRequest request = atmosphereResource.getRequest();
        HttpSession httpSession = request.getSession();
        BroadcasterFactory broadcasterFactory = atmosphereResource.getAtmosphereConfig().getBroadcasterFactory();
        gameCode = (String)httpSession.getAttribute("GameCode");

        if(broadcasterFactory.lookup(gameCode) == null) {
            gameBroadCaster = broadcasterFactory.lookup(gameCode, true);
        }
        gameBroadCaster.addAtmosphereResource(atmosphereResource);
        httpSession.setAttribute("GameBroadcaster", gameBroadCaster);
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {

    }

    public Broadcaster getGameBroadcaster() {
        return gameBroadCaster;
    }

    @Override
    public void destroy() {

    }
}

