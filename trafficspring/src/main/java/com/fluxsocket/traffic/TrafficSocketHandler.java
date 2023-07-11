package com.fluxsocket.traffic;

import java.io.IOException;
import java.time.Duration;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TrafficSocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(TrafficSocketHandler.class);

    private static TrafficLightState [] trafficLightStates = {
	new TrafficLightState(LightState.RED),
	new TrafficLightState(LightState.GREEN),
	new TrafficLightState(LightState.AMBER)
	};

    static int nextRandom(Random random) {
	// 3 - 10 seconds delay
	return 3 + (int)(random.nextFloat() * 7);
    }
    @Override
    public void handleTextMessage(final WebSocketSession session, TextMessage message)
	throws InterruptedException, IOException {
	logger.info("handle text message...");
	final Random random			= new Random();
	final AtomicInteger delaySeconds	= new AtomicInteger(0);
	final AtomicInteger stateTracker	= new AtomicInteger(0);
	final int stateLen			= trafficLightStates.length;
	final Gson gson				= new Gson();
	Disposable fluxDisposable		= null;

	fluxDisposable =
	    Flux.interval(Duration.ofSeconds(1))
	    .doOnError(th -> {
		    logger.info("error due to closing.." + th);
		})
	    .subscribe( v-> {
		    if (delaySeconds.intValue() == 0) {
			int curState = stateTracker.get();
			stateTracker.set( (curState + 1 ) % stateLen);
			delaySeconds.set( nextRandom(random));
			try {
			    TrafficLightState currentLightState = trafficLightStates[curState];
			    String serializedJson		=  gson.toJson(currentLightState);
			    session.sendMessage( new TextMessage(serializedJson));

			    //new TextMessage("hello: " + new Date() + "  " + v + " " + serializedJson)
			    //intFlux.delayElements(Duration.ofSeconds(3));
			} catch(IOException ioex) {
			    logger.info("Exception during send msg" + ioex);
			}
		    } else {
			delaySeconds.getAndDecrement();
		    }
		});
    }

    // @Override
    // protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
    //     System.out.println("New Binary Message Received");
    //     session.sendMessage(new TextMessage("Binary msg received")); //message);
    // }


    // @Override
    // public void handleTransportError(WebSocketSession session,
    // 				     Throwable exception)
    // 	throws Exception {
    // 	System.out.println("transport error:" + exception);
    // 	logger.info("handle tx error ..." + exception);
    // 	super.handleTransportError(session, exception);
    // }

    // @Override
    // public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    // 	throws Exception {
    // 	logger.info("connection closed.." + status);
    // }

    // @Override
    // public void afterConnectionEstablished(WebSocketSession session)
    // 	throws Exception {
    // 	logger.info("connection established.." + this);
    // 	logger.info("protocol: " + session.getAcceptedProtocol());
    // 	super.afterConnectionEstablished(session);
    // }

    // @Override
    // public void handleMessage(WebSocketSession session,
    //                       WebSocketMessage<?> message)
    // 	throws Exception {
    // 	System.out.println("handle message...");
    // 	logger.info("handle message...");
    // 	//super.handleMessage(session, new TextMessage("handle msg hello: " + new Date()));
    // 	session.sendMessage(new TextMessage("hadleMsg" + new Date()));
    // }

}
