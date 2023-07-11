export enum LightState {
    RED		= "RED",
    AMBER	= "AMBER",
    GREEN	= "GREEN"
}

export interface TrafficLightState {
    lightState: LightState;
}

let lightStateMap  = new Map<string, LightState>([
    ["RED"	, LightState.RED],
    ["AMBER"	, LightState.AMBER],
    ["GREEN"	, LightState.GREEN]
]);


export class LightStateBuilder {
    static build(light: string) : LightState {
	console.log("light", light);
	const upperLight = light? light.toUpperCase() : "RED";
	if (upperLight in lightStateMap) {
	    return lightStateMap[upperLight];
	}
	return LightState.RED;
    }
}
