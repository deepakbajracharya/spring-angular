package com.fluxsocket.traffic;

public enum LightState {
    RED("RED"),
    AMBER("AMBER"),
    GREEN("GREEN");

    private String value;
    LightState(String value) {
	this.value = value;
    }
    public String toString() {
	return value;
    }
}
