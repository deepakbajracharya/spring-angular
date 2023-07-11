import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';

import { LightState, TrafficLightState, LightStateBuilder } from '../models/traffic.models';

import { TrafficService } from '../traffic.service';

@Component({
    selector	: 'app-traffic-light',
    templateUrl	: './traffic-light.component.html',
    styleUrls	: ['./traffic-light.component.css']
})
export class TrafficLightComponent implements OnInit {

    private state$ : Subscription;
    constructor( private trafficService : TrafficService) { 
    }
    lightState: LightState = LightState.RED;

    ngOnInit(): void {
	this.state$ = this.trafficService.state$.subscribe(
	    (x: TrafficLightState)  => {
		//console.log("componet got", x, x.lightState);
		this.lightState = x.lightState;
	    });

    }
    isRed(): boolean {
	return this.lightState  == LightState.RED;
    }

    isAmber(): boolean {
	return this.lightState  == LightState.AMBER;
    }

    isGreen(): boolean {
	return this.lightState  == LightState.GREEN;
    }
    ngOnDestroy() {
	this.state$.unsubscribe();
    }

}
