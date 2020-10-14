package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {

		double ystep;

		// TODO - START

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));

		return ystep;

		// TODO - SLUTT

	}

	public void showRouteMap(int ybase) {

		// TODO - START

		double latitude = 0;
		double longitude = 0;
		double latitude2 = 0;
		double longitude2 = 0;
		for (int i = 0; i < gpspoints.length - 1; i++) {
			longitude = (gpspoints[i].getLongitude() * xstep() - 31108); // høyest 850 lavest 50
			latitude = ybase - (gpspoints[i].getLatitude() * ystep() - 1502307); // høyest 800 lavest 0
			longitude2 = (gpspoints[i + 1].getLongitude() * xstep() - 31108);
			latitude2 = ybase - (gpspoints[i + 1].getLatitude() * ystep() - 1502307);

			setColor(055, 250, 031);
			fillCircle((int) longitude, (int) latitude, 3);
			drawLine((int) longitude, (int) latitude, (int) longitude2, (int) latitude2);

			pause(30);
		}

		setColor(0, 0, 180);
		fillCircle((int) (gpspoints[gpspoints.length - 1].getLongitude() * xstep() - 31108),
				(int) (ybase - (gpspoints[gpspoints.length - 1].getLatitude() * ystep() - 1502307)), 10);

		// TODO - SLUTT
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);

		double WEIGHT = 80.0;
		// TODO - START

		String tid = String.format("%02d:%02d:%02d", gpscomputer.totalTime() / 3600, gpscomputer.totalTime() % 60,gpscomputer.totalTime() / 60);
		String distanse = String.format("%.2f", gpscomputer.totalDistance() / 1000);
		String elevasjon = String.format("%.2f", gpscomputer.totalElevation());
		String maxSpeed = String.format("%.2f", gpscomputer.maxSpeed());
		String averageSpeed = String.format("%.2f", gpscomputer.averageSpeed());
		String energy = String.format("%.2f", gpscomputer.totalKcal(80));
		drawString("Total Time       : " + tid, 40, 40);
		drawString("Total Distance   : " + distanse + " km", 40, 60);
		drawString("Total Elevation  : " + elevasjon + " m", 40, 80);
		drawString("Max Speed        : " + maxSpeed + " km/h", 40, 100);
		drawString("Average Speed    : " + averageSpeed + " km/h", 40, 120);
		drawString("Energy           : " + energy + " kcal", 40, 140);

		// TODO - SLUTT;
	}

}