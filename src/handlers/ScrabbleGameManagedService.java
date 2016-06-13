package handlers;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.atmosphere.cpr.ApplicationConfig.MAX_INACTIVE;

/**
 * Created by rostifar on 6/11/16.
 */

@ManagedService(path="/scrabbleGame", atmosphereConfig = MAX_INACTIVE + "=120000")
public class ScrabbleGameManagedService {
    private final Logger logger = LoggerFactory.getLogger(ScrabbleGameManagedService.class);
    private String gameCode;

    @Inject
    private AtmosphereResource atmosphereResource;

    @Inject
    private Broadcaster broadcaster;

    @Inject
    private AtmosphereResourceEvent event;

    @Ready
    public void onReady() {
        AtmosphereRequest request = atmosphereResource.getRequest();
        System.out.println("hu");
        HttpSession session = request.getSession();
        broadcaster = ScrabbleGameCache.lookupGame(gameCode).getGameManager().getGameBroadcaster().addAtmosphereResource(atmosphereResource);
        atmosphereResource.setBroadcaster(broadcaster);
    }

    @Disconnect
    public void onDisconnect() {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    public Message onMessage(Message message) throws IOException{
        logger.info("hi");
        return message;
    }
}
