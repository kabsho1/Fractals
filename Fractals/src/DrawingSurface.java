import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.event.MouseEvent;


public class DrawingSurface extends PApplet {

	private KochCurve koch;
	private Mandelbrot fractal;
	private HilbertCurve hilbert;
	private BanachCurve banach;
	private int kLevel, kLength, hLevel, hScale, direction, bLevel, bScale;
	double zoom;
	
	public DrawingSurface() {
		kLevel = 0;
		kLength = 200;
		hScale = 100;
		hLevel = 1;
		zoom = Math.pow(0.25, 1d/3);
		direction = HilbertCurve.UP;
		bLevel = 1;
		bScale = 50;
		
		fractal = new Mandelbrot(zoom);
		koch = new KochCurve(kLevel, kLength);
		hilbert = new HilbertCurve(hLevel, hScale, direction);
		banach = new BanachCurve(bLevel, bScale);
	}
	
	public static void main(String[] args) {
		PApplet.main("DrawingSurface");
	}
	
	public void settings() {
		size(700, 500);
	}
	

	public void setup() {
		//colorMode(HSB, 360, 100, 100);
		surface.setResizable(true);

	}
	

	public void draw() { 
		background(255);
		
		textSize(12);
		fill(0);
		
		// (uncomment if its in the way)
		text("Use the mouse wheel to change length, use UP/DOWN keys to change level, use RIGHT/LEFT keys to rotate.",0,15);
		
		stroke(0);
		noFill();
		
		// note to user: uncomment on of the draw methods below at a time.
		
		//koch.draw(this);	   // snowflake
		fractal.draw(this);    // mandelbrot set
		hilbert.draw(this);  // hilbert curve
		//banach.draw(this); // banach curve
	}
	
	
	public void mouseWheel(MouseEvent event) {
		  int num = event.getCount();
		  
		  kLength -= num*10;
		  koch = new KochCurve(kLevel,kLength);

		  hScale -= num*10;
		  hilbert = new HilbertCurve(hLevel, hScale, direction);

		  bScale -= num*10;
		  banach = new BanachCurve(bLevel, bScale);

	}
	
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_UP) {
			kLevel++;
			koch = new KochCurve(kLevel,kLength);
			hLevel++;
			hilbert = new HilbertCurve(hLevel, hScale, direction);
			bLevel++;
			banach = new BanachCurve(bLevel, bScale);

			
			zoom++;
			fractal = new Mandelbrot(zoom);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			kLevel--;
			koch = new KochCurve(kLevel,kLength);
			hLevel--;
			hilbert = new HilbertCurve(hLevel, hScale, direction);
			bLevel--;
			banach = new BanachCurve(bLevel, bScale);

			
			zoom--;
			fractal = new Mandelbrot(zoom);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			direction++;
			hilbert = new HilbertCurve(hLevel, hScale, direction);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			direction--;
			hilbert = new HilbertCurve(hLevel, hScale, direction);
		}
	}
	
	
}










