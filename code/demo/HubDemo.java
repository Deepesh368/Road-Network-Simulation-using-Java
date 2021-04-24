package demo19508;

import java.util.*;

import base.*;

class HubDemo extends Hub {
	private ArrayList<Truck> queue = new ArrayList<Truck>();

	public HubDemo(Location loc) {
		super(loc);
	}

	@Override
	public synchronized boolean add(Truck truck) {
		if (queue.size() < this.getCapacity()) {
			queue.add(truck);
			return true;
		}
		return false;
	}

	@Override
	protected synchronized void remove(Truck truck) {
		queue.remove(truck);
	}

	private synchronized int bfs(Highway start, Hub destination) {
		Highway h = null;
		Map<Highway, Integer> hashmap = new HashMap<Highway, Integer>();
		Set<Highway> hashset = new HashSet<Highway>();
		LinkedList<Highway> linklist = new LinkedList<Highway>();
		hashmap.put(start, 1);
		hashset.add(start);
		linklist.add(start);
		while (linklist.size() > 0) {
			h = linklist.remove();
			if (h.getEnd().equals(destination)) {
				return hashmap.get(h);
			}
			ArrayList <Highway> highways = h.getEnd().getHighways();
			for (Highway hw : highways) {
				if (hashset.contains(hw) == false) {
					linklist.add(hw);
					hashmap.put(hw, hashmap.get(h) + 1);
					hashset.add(start);
				}
			}
		}
		return -1;
	}

	@Override
	public synchronized Highway getNextHighway(Hub last, Hub dest) {
		ArrayList<Highway> highways = this.getHighways();
		int min = Integer.MAX_VALUE;
		Highway nexthighway = null;
		for (Highway h : highways) {
			int distance = bfs(h, dest);
			if (distance > 0 && distance < min) {
				min = distance;
				nexthighway = h;
			}
		}
		return nexthighway;
	}

	@Override
	protected synchronized void processQ(int deltaT) {
		for (int i = 0; i < queue.size(); i++) {
			Truck tr = queue.get(i);
			Hub destination = NetworkDemo.getNearestHub(tr.getDest());
			if (this.equals(destination)) {
				this.remove(tr);
				continue;
			}
			Highway h = this.getNextHighway(this, destination);
			if (h.add(tr) == true) {
				tr.enter(h);
				this.remove(tr);
			}
		}
	}

}
