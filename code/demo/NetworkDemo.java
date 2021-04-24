package demo19508;

import java.util.ArrayList;

import base.*;

public class NetworkDemo extends Network {
	private ArrayList<Hub> hubs = new ArrayList<Hub>();
	private ArrayList<Highway> highways = new ArrayList<Highway>();
	private ArrayList<Truck> trucks = new ArrayList<Truck>();

	@Override
	public void add(Hub hub) {
		hubs.add(hub);
	}

	@Override
	public void add(Highway hwy) {
		highways.add(hwy);
	}

	@Override
	public void add(Truck truck) {
		trucks.add(truck);
	}

	@Override
	protected Hub findNearestHubForLoc(Location loc) {
		Hub closest = null;
		int distance = Integer.MAX_VALUE;
		for (Hub h : hubs) {
			if (h.getLoc().distSqrd(loc) < distance) {
				distance = h.getLoc().distSqrd(loc);
				closest = h;
			}
		}
		return closest;
	}

	@Override
	public void start() {
		for (Hub h : hubs) {
			h.start();
		}
		for (Truck t : trucks) {
			t.start();
		}
	}

	@Override
	public void redisplay(Display disp) {
		for (Hub h : hubs) {
			h.draw(disp);
		}
		for (Truck t : trucks) {
			t.draw(disp);
		}
		for (Highway high : highways) {
			high.draw(disp);
		}
	}
}
