package com.fluxsocket.traffic;

public class TrafficLightState {
    private TrafficLightState() {}

    private LightState lightState;

    public TrafficLightState(LightState lightState) {
	this.lightState = lightState;
    }
    public String toString() {
	return "TrafficLightState: lightState <" + lightState + ">";
    }

}
