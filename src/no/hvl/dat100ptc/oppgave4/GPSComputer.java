package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]); // distansen mellom det forrige punktet og
																			// det nåværende punktet f.kes punkt 3 til
																			// 4.
		}

		return distance;

		// TODO - SLUTT

	}

	// beregn totale hÃ¸ydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		// TODO - START
		for (int g = 1; g < gpspoints.length; g++) {
			double difference = gpspoints[g].getElevation() - gpspoints[g - 1].getElevation();
			if (difference > 0) {
				elevation += difference;
			}

		}

		return elevation;
		// TODO - SLUTT

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int time = (gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime());

		return time;

	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];
		double speed = 0;

		// TODO - START // OPPGAVE - START
		for (int i = 1; i < gpspoints.length; i++) {

			speed = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			speeds[i - 1] = speed;
		}
		return speeds;

		// TODO - SLUTT

	}

	public double maxSpeed() {

		double maxspeed = 0;
		// TODO - Start

		for (int i = 0; i < gpspoints.length; i++) {
			maxspeed = GPSUtils.findMax(speeds());
		}

		return maxspeed;
		// TODO - SLUTT

	}

	public double averageSpeed() {

		double average = (totalDistance() / totalTime() * 3.6);

		return average;
		// TODO - SLUTT

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjÃ¸res med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

//		double [] fart = {10.0, 12.0, 14.0, 16.0, 20.0, 1000.0};
//		double [] forbrenning = {4.0, 6.0, 8.0, 10.0, 12.0, 16.0};
//		
//		for(int i = 0; i < forbrenning.length; i++) {
//			if(speedmph < fart[i]) {
//				met = forbrenning[i];
//				break;
//			}
//		}

		// speed = totalDistance() / totalTime() ---> kladd

		if (speedmph < 10) {
			met = 4.0;
		}
		if (speedmph < 12) {
			met = 6.0;
		}
		if (speedmph < 14) {
			met = 8.0;
		}
		if (speedmph < 16) {
			met = 10.0;
		}
		if (speedmph < 20) {
			met = 12.0;
		}
		if (speedmph >= 20) {
			met = 16.0;
		}

		kcal = met * weight * (secs / 3600.0);

		return kcal;

		// TODO - SLUTT

	}

	public double totalKcal(double weight) {

		double totalkcal = kcal(weight, totalTime(), averageSpeed());

		return totalkcal;

		// kaller på metoden ovenfor. Dette er for å finne den totale energi-mengden som
		// er forbrent på ruten.

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");

		// TODO - START
		String s = String.format(":");

		System.out.println(
				"Totale time" + "     " + s + GPSUtils.formatTime(totalTime()) + "\n" + 
				"Total distance " + " " + s + GPSUtils.formatDouble(totalDistance() / 1000) + " " + "km" + "\n" +
				"Total elevation" + " " + s + GPSUtils.formatDouble(totalElevation()) + " m " + "\n" +
				"Max speed" + "       " + s + GPSUtils.formatDouble(maxSpeed()) + " " + "km/t" + "\n" + 
				"Average speed" + "   " + s + GPSUtils.formatDouble(averageSpeed()) + " " + "km/t" + "\n" + 
				"Energy" + "          " + s + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " " + "kcal");

		System.out.println("==============================================");
		// TODO - SLUTT

	}

}