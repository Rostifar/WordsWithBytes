package handlers;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Dad on 5/25/2016.
 */
@AtmosphereHandlerService(path = "/scrabbleGame", interceptors = org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor.class)
public class ScrabbleGameAtmosphereHandler implements AtmosphereHandler, Serializable {
    private String gameCode;
    // @Inject
    BroadcasterFactory broadcasterFactory;

    /**
     * This method broadcasts state changes to other scrabble clients who are listening (participating in the game)?
     */
    @Override
    public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
        ScrabbleGameCache.lookupGame(gameCode).getGameBroadcaster().addAtmosphereResource(atmosphereResource);
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {

    }

    @Override
    public void destroy() {

    }

    public void setGameCode(String code) {
        gameCode = code;
    }
}

