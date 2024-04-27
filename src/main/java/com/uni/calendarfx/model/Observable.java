package com.uni.calendarfx.model;

import java.util.List;

import java.util.LinkedList;

/**
* Observable class is supposed to broadcast events and 
*	messages by providing notifyAll() class
*/
public abstract class Observable{

	private List<Observer> observers;
	
	public boolean subscribe(Observer o){
		if(this.observers==null)
			this.observers=new LinkedList<Observer>();
		return this.observers.add(o);
	}

	public boolean unsubscribe(Observer o){
		if(this.observers==null)
			this.observers=new LinkedList<Observer>();
		return this.observers.remove(o);
	}

	public void notifyAll(String msg,Object data){
		if(this.observers!=null)
			for(Observer o:observers)
				o.update(msg,data);
	}
}
