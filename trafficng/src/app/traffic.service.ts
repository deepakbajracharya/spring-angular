import { Injectable } from '@angular/core';

import { interval, Observable, pipe } from 'rxjs';

//import { Subject } from 'rxjs/subject';
import { Subject } from 'rxjs';

import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

import { LightState, TrafficLightState, LightStateBuilder } from './models/traffic.models';

@Injectable({
  providedIn: 'root'
})
export class TrafficService {
    private ticks$	: Observable<number>;
    public state$	: Subject<any>;

    // used for browser only test
    private lightStates: Array<TrafficLightState> = [
	{ lightState : LightState.RED  	},
	{ lightState : LightState.GREEN	},
	{ lightState : LightState.AMBER	}
    ];
    private totalStates = this.lightStates.length;
    wsSubject$		= null;

    constructor() { 
	// use either of frontEnd or backEnd logic, not both
	this.useBackEndLogic();
	//this.useFrontEndLogic();
    }
    useBackEndLogic() {
	console.log("subscribe ws..");
	this.wsSubject$ = webSocket({
	    url: "ws://localhost:8080/lights",
	    deserializer: ({data}) => data,
	    serializer: ({data}) => data
	});

	this.state$ = new Subject();
	this.wsSubject$.subscribe(
	    (obs: string) => {
		//console.log("obs", obs);
		let tls : TrafficLightState = JSON.parse(obs);
		//console.log("tls", tls);
		this.state$.next( tls);
	    },
	    error => console.error("sth wrong", error),
	    () => console.log("complete ws..")
	);
	this.wsSubject$.next("Initiate");
    }

    useFrontEndLogic() {

	let stIndex = 0;
	let pushTimeout = null;
	let pushData = (_subscriber ) => {
	    pushTimeout = setTimeout(
		() => {
		    console.log(this.lightStates[ stIndex % this.totalStates]);
		    _subscriber.next(this.lightStates[ stIndex % this.totalStates]);
		    stIndex++;
		    // call recursiveley to reschedule again
		    pushData(_subscriber)
		    //this.wsSubject$.next("sending " + stIndex);
		},
		// 3-10 seconds delay
		3000 + (Math.random() * 7000));
	}

	this.ticks$ = new Observable( (_subscriber) => {
	    pushData(_subscriber);
	    return () => clearTimeout( pushTimeout);
	});

	this.state$ = new Subject();
	this.ticks$.subscribe( valueObserved  => {
	    //this.state$.next( valueObserved.next());
	    // console.log("ticks: " + valueObserved);
	    this.state$.next(valueObserved);
	});
    }
}
