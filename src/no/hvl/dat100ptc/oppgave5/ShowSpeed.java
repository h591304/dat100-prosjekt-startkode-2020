package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {

	private static final int MARGIN = 50;
	private static final int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;

	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length - 1; // number of data points

		makeWindow("Speed profile", 2 * MARGIN + 2 * N, 2 * MARGIN + BARHEIGHT);

		showSpeedProfile(MARGIN + BARHEIGHT, N);
	}

	public void showSpeedProfile(int ybase, int N) {

		// get segments speeds from the GPS computer object
		// double[] speeds = gpscomputer.speeds();

		int x = MARGIN, y;

		// TODO - START
		double speeds[] = new double[gpspoints.length - 1];
		double speed = 0;
		for (int i = 1; i < gpspoints.length; i++) {
			speed = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			speeds[i - 1] = speed;
		}

		for (int i = 0; i < speeds.length; i++) {

			setColor(100, 10, 250);
			drawLine(MARGIN + 2 * i, ybase - (int) speeds[i], MARGIN + 2 * i, ybase);
			;

		}
		double averageSpeed = gpscomputer.averageSpeed();
		setColor(0, 255, 0);
		drawLine(MARGIN, ybase - (int) averageSpeed, MARGIN + 2 * speeds.length, ybase - (int) averageSpeed);

		// TODO - SLUTT
	}
}