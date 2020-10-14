package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		this.antall = 0;
		this.gpspoints = new GPSPoint[n];
		
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;

		if(antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;
			return !inserted;
		}

		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);

		return insertGPS(gpspoint);
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		double t1 = 1;
		double t2 = 2;
		double sum = t1 + t2;
		
		for(int i = 1; i < gpspoints.length; i++) {
			
			System.out.println(i + " (" + t1 + "," + t2 + ") " + sum);
			
			t1 += 3;
			t2 += 3;
			sum += t1+t2;
		}

		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
