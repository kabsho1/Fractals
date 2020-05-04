
import processing.core.PApplet;

public class Mandelbrot {
	
	private double zoom;
	
	private static final int ITERATIONS = 500;
	private static final int INFINITY = 50;
	
	private static final double OFF_SET_X = 0.360240443437614; //- 1.76938317919;
	private static final double OFF_SET_Y = -0.641313061064;   //0.0042368479187367722;



	public Mandelbrot(double zoom) {
		this.zoom = zoom;
		
	}
	
	public void draw(PApplet marker) {
		marker.colorMode(marker.HSB, 360, 100, 100);
		
		//marker.line(marker.width/2, 0, marker.width/2, marker.height);
		//marker.line(0, marker.height/2, marker.width, marker.height/2);


		double scale = (4f - (4-1/(zoom*zoom*zoom)))/marker.height;
		
		int temp;
		for(double x = -marker.width/2f; x < marker.width/2f; x++) {
			for(double y = -marker.height/2f; y < marker.height/2f; y++) {
				temp = checkC(x * scale + OFF_SET_X, y * scale + OFF_SET_Y);
				
				if(temp == -1) {
					marker.stroke(0, 0, 0);
					marker.point((float)(x + marker.width/2), (float)(y + marker.height/2));
				} else { 
					marker.stroke((temp*5)%ITERATIONS, 100, 100);
					marker.point((float)(x + marker.width/2), (float)(y + marker.height/2));
				}

				//System.out.println(x * scale + " " + y * scale);
			}
		}

		//checkC(1, 0);
		
	}
	
	public int checkC(double ca, double cb) {
		double za = 0;
		double zb = 0;
		double temp;
		int i = 0; // iterator
		while(Math.abs(za + zb) < INFINITY && i < ITERATIONS) {
			// z = z*z + c
			temp = za;
			za = za * za - zb * zb + ca;
			zb = 2*(temp * zb) + cb;
			i++;
		}
		
		if(i == ITERATIONS) { //does not diverge
			return -1;
		}
		return i;
			
	}
	
	
}
