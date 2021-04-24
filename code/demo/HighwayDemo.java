package demo19508;

import java.util.ArrayList;

import base.Highway;
import base.Truck;

class HighwayDemo extends Highway {

	private ArrayList<Truck> trucks = new ArrayList<Truck>();

	@Override
	public synchronized boolean hasCapacity() {
		if (trucks.size() < this.getCapacity()) {
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean add(Truck truck) {
		if (this.hasCapacity() == true) {
			trucks.add(truck);
			return true;
		}
		return false;
	}

	@Override
	public synchronized void remove(Truck truck) {
		trucks.remove(truck);
	}

}
