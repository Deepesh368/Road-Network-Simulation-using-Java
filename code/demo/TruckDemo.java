package demo19508;

import base.*;

class TruckDemo extends Truck {
	private Highway present_highway;
	private Hub previous_hub, current_hub, start_hub, destination_hub;
	private float total_time;

	public TruckDemo() {
		previous_hub = null;
		present_highway = null;
		current_hub = null;
		total_time = 0;
	}

	@Override
	public String getTruckName() {
		return "Truck19508";
	}

	@Override
	public void enter(Highway hwy) {
		present_highway = hwy;
		previous_hub = hwy.getStart();
		current_hub = null;
	}

	@Override
	public Hub getLastHub() {
		return previous_hub;
	}

	@Override
	protected synchronized void update(int deltaT) {
		start_hub = NetworkDemo.getNearestHub(this.getSource());
		destination_hub = NetworkDemo.getNearestHub(this.getDest());
		if (total_time < this.getStartTime()) {
			total_time += deltaT;
			return;
		}
		if (previous_hub == null && present_highway == null && current_hub == null) {
			if (this.getLoc().distSqrd(start_hub.getLoc()) < 50) {

				if (start_hub.add(this) == true) {
					current_hub = start_hub;
					this.setLoc(start_hub.getLoc());
				}
			}

			else {
				double length = Math.sqrt(this.getLoc().distSqrd(start_hub.getLoc()));
				double sin = (start_hub.getLoc().getY() - this.getLoc().getY()) / (length * 1.0);
				double cos = (start_hub.getLoc().getX() - this.getLoc().getX()) / (length * 1.0);
				double delta_x = 10 * cos * deltaT / 1000.0;
				double delta_y = 10 * sin * deltaT / 1000.0;
				int new_x = (int) (this.getLoc().getX() + delta_x);
				int new_y = (int) (this.getLoc().getY() + delta_y);
				this.setLoc(new Location(new_x, new_y));
			}
			total_time += (deltaT / 1000.0);
			return;
		}

		if (present_highway != null) {
			Hub next_hub = present_highway.getEnd();
			if (this.getLoc().distSqrd(next_hub.getLoc()) < 50) {
				if (next_hub.add(this) == true) {
					current_hub = next_hub;
					present_highway.remove(this);
					present_highway = null;
					this.setLoc(next_hub.getLoc());
				}
			}

			else {
				double length = Math.sqrt(this.getLoc().distSqrd(next_hub.getLoc()));
				double sin = (next_hub.getLoc().getY() - this.getLoc().getY()) / (length * 1.0);
				double cos = (next_hub.getLoc().getX() - this.getLoc().getX()) / (length * 1.0);
				double delta_x = present_highway.getMaxSpeed() * cos * deltaT / 600.0;
				double delta_y = present_highway.getMaxSpeed() * sin * deltaT / 600.0;
				int new_x = (int) (this.getLoc().getX() + delta_x);
				int new_y = (int) (this.getLoc().getY() + delta_y);
				this.setLoc(new Location(new_x, new_y));
			}
			total_time += (deltaT / 1000.0);
			return;
		}

		if (current_hub.equals(destination_hub)) {
			if (this.getLoc().distSqrd(this.getDest()) < 50) {
				this.setLoc(this.getDest());
			}

			else {
				double length = Math.sqrt(this.getLoc().distSqrd(this.getDest()));
				double sin = (this.getDest().getY() - this.getLoc().getY()) / (length * 1.0);
				double cos = (this.getDest().getX() - this.getLoc().getX()) / (length * 1.0);
				double delta_x = 10 * cos * deltaT / 1000.0;
				double delta_y = 10 * sin * deltaT / 1000.0;
				int new_x = (int) (this.getLoc().getX() + delta_x);
				int new_y = (int) (this.getLoc().getY() + delta_y);
				this.setLoc(new Location(new_x, new_y));
			}
			total_time += (deltaT / 1000.0);
			return;
		}
	}
}
