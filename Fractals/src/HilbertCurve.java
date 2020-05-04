import processing.core.PApplet;
import processing.core.PVector;

public class HilbertCurve {
	private float scale;
	private int order, dir;
	private static float pi;
	
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;

	

	
	public HilbertCurve(int order, float scale, int dir) {
		this.order = order;
		this.scale = scale;
		this.dir = dir;
	}
	
	public void draw(PApplet marker) {
		pi = marker.PI;
		//drawFailedExperiment(marker.width/2, marker.height/2, 3 * pi/2, scale, order, marker);

		drawHilbertCurve(marker.width/2 - scale/2, marker.height/2 - scale/2,  dir, scale, order, marker); 

		
	}
	
	/*
	private float[][] drawHilbertCurve(float x, float y, int dir, float scale, int order, PApplet marker) {
		marker.rect(x, y, scale, scale);
		marker.line(x + scale/2, y, x + scale/2, y + scale);
		marker.line(x, y + scale/2, x + scale, y + scale/2);
		/*
		 * i want to draw an order one curve, but i want to draw it in the correct order, and return the start and end points
		 * is there a pattern to what segments to start/end with?
		 * 		yes: up: left down right - counterclockwise - (+1)
		 * 			 left: up right down - clockwise - (-1)
		 * 			 down: right up left - counterclockwise (+1)
		 * 			 right: down, left, up - clockwise (-1)
		 * the state of angle will be determined as a int: 1,2,3,4
		 *
		if(order <= 1) { 
			return new float[][] { // a set of start/end points
				{},
				{}
			};
		}
		/*
		 * draw 4 hilbert curves
		 * depending on this.dir (if the method was an instance of an object), 
		 * if right, both right curves should be rotated. When connecting lines, i could connect via formula!
		 *
		drawHilbertCurve(x, y, dir, scale, order, marker);
		drawHilbertCurve(x, y, dir, scale, order, marker);
		drawHilbertCurve(x, y, dir, scale, order, marker);
		drawHilbertCurve(x, y, dir, scale, order, marker);


	}
	*/
	
	private void drawHilbertCurve(float x, float y, int dir, float scale, int order, PApplet marker) {
		//marker.rect(x, y, scale, scale);
		//marker.line(x + scale/2, y, x + scale/2, y + scale);
		//marker.line(x, y + scale/2, x + scale, y + scale/2);
//		marker.stroke(hue, 100, 100);
//		if(hue < 360)
//			hue += 0.5 * order;
//		else
//			hue = 0;
		while(dir < 0)
			dir += 4; // make sure its positive
		dir %= 4; // (0-3)

		if(order <= 1) {
			PVector startCurve, start;
			
			if(dir == LEFT || dir == UP) { // dir = left or up (pi, 3*pi/2)
				startCurve = new PVector(x + scale/4, y + scale/4);// top or left
			}
			else { // dir = 0 or pi/2 OR pi (right or down)
				startCurve = new PVector(x + 3*scale/4, y + 3*scale/4);// bottom right
			}
			
			float changeDir;
			if(dir == UP || dir == DOWN) { // up or down (pi/2, 3*pi/2)
				changeDir = -1; 
			} else { // dir = left or right
				changeDir = 1; 
			}
			
			float theta = dir * pi/2;
				
			
			PVector end = new PVector(startCurve.x - scale/2 * marker.cos(theta), startCurve.y - scale/2 * marker.sin(theta)); // initial start + the length * cos(direction facing)
			marker.line(startCurve.x, startCurve.y, end.x, end.y);
			
			start = new PVector(end.x, end.y); // end point is new start point
			dir += changeDir; theta = dir * pi/2;
			end = new PVector(start.x - scale/2 * marker.cos(theta), start.y - scale/2 * marker.sin(theta));

			marker.line(start.x, start.y, end.x, end.y);
			
			start.x = end.x; start.y = end.y; // end point is new start point
			dir += changeDir; theta = dir * pi/2;
			end = new PVector(start.x - scale/2 * marker.cos(theta), start.y - scale/2 * marker.sin(theta));
			
			marker.line(start.x, start.y, end.x, end.y);
			return;
		}
		
		//if facing up, rotate top left, top right
		//if facing left, rotate top left and 
		int changeTopLeft; int changeBottomLeft; int changeBottomRight; int changeTopRight;
		if(dir == UP) { // up
			changeTopLeft = -1;
			changeBottomLeft = 0;
			changeBottomRight = 0;
			changeTopRight = 1;
		} else if(dir == RIGHT) { // right
			changeTopLeft = 0;
			changeBottomLeft = 0;
			changeBottomRight = 1;
			changeTopRight = -1;
		} else if(dir == DOWN) { // down
			changeTopLeft = 0;
			changeBottomLeft = 1;
			changeBottomRight = -1;
			changeTopRight = 0;
		} else { 
			
			changeTopLeft = 1;
			changeBottomLeft = -1;
			changeBottomRight = 0;
			changeTopRight = 0;
		}

		//marker.stroke(255,0,0);
		float piece = (float)Math.pow(2, order+1); 
		float shiftToCenter = piece/2-1;
		
		if(dir == RIGHT) { //right
			marker.line(x + shiftToCenter*scale/piece, y + scale/piece, x + (shiftToCenter+2)*scale/piece, y + scale/piece);
			marker.line(x + (shiftToCenter)*scale/piece, y + shiftToCenter*scale/piece, x + (shiftToCenter)*scale/piece, y + (shiftToCenter+2)*scale/piece);
			marker.line(x + shiftToCenter*scale/piece, y + scale - scale/piece, x + (shiftToCenter+2)*scale/piece, y + scale - scale/piece);	
		} else if(dir == UP) { // up
			marker.line(x + scale/piece, y + shiftToCenter*scale/piece, x + scale/piece, y + (shiftToCenter+2)*scale/piece);
			marker.line(x + shiftToCenter*scale/piece, y + (shiftToCenter+2)*scale/piece, x + (shiftToCenter+2)*scale/piece, y + (shiftToCenter+2)*scale/piece);
			marker.line(x + scale - scale/piece, y + shiftToCenter*scale/piece, x + scale - scale/piece, y + (shiftToCenter+2)*scale/piece);
		} else if(dir == LEFT) { // left
			marker.line(x + shiftToCenter*scale/piece, y + scale/piece, x + (shiftToCenter+2)*scale/piece, y + scale/piece);
			marker.line(x + (shiftToCenter+2)*scale/piece, y + shiftToCenter*scale/piece, x + (shiftToCenter+2)*scale/piece, y + (shiftToCenter+2)*scale/piece);
			marker.line(x + shiftToCenter*scale/piece, y + scale - scale/piece, x + (shiftToCenter+2)*scale/piece, y + scale - scale/piece);
		} else { // down
			marker.line(x + scale/piece, y + shiftToCenter*scale/piece, x + scale/piece, y + (shiftToCenter+2)*scale/piece);
			marker.line(x + shiftToCenter*scale/piece, y + (shiftToCenter)*scale/piece, x + (shiftToCenter+2)*scale/piece, y + (shiftToCenter)*scale/piece);
			marker.line(x + scale - scale/piece, y + shiftToCenter*scale/piece, x + scale - scale/piece, y + (shiftToCenter+2)*scale/piece);
		}
		//marker.stroke(0);
		
		drawHilbertCurve(x, y, dir + changeTopLeft, scale/2, order - 1, marker); //top left
		drawHilbertCurve(x, y + scale/2, dir + changeBottomLeft, scale/2, order - 1, marker); //bottom left
		drawHilbertCurve(x + scale/2, y + scale/2, dir + changeBottomRight, scale/2, order - 1, marker); //bottom right
		drawHilbertCurve(x + scale/2, y, dir + changeTopRight, scale/2, order - 1, marker);	//top right
	}
	
	/*
	private float[][] drawFailedExperiment(float x, float y, float dir, float scale, int order, PApplet marker) {
		marker.rect(x, y, scale, scale);
		marker.line(x + scale/2, y, x + scale/2, y + scale);
		marker.line(x, y + scale/2, x + scale, y + scale/2);

		//System.out.println((dir % (2*pi)) / pi);
		
		dir %= 2*pi; // dir in range of -2pi to 2pi
		dir += 2*pi; // dir is now positive
		dir %= 2*pi; // dir is now positive and less than 2pi 
			
		if(order <= 1) {
			PVector startCurve, start;
			
			
			if(Math.abs(dir - pi) < 0.0001 || Math.abs(dir - 3*pi/2) < 0.0001) { // dir = left or up (pi, 3*pi/2)
				startCurve = new PVector(x + scale/4, y + scale/4);// top or left
			}
			else { // dir = 0 or pi/2 OR pi (right or down)
				startCurve = new PVector(x + 3*scale/4, y + 3*scale/4);// bottom right
			}
			
			float changeDir;
			if(Math.abs(dir % pi - pi/2) < 0.0001) { // up or down (pi/2, 3*pi/2)
				changeDir = -pi/2; 
			} else { // dir = left or right
				changeDir = pi/2; 
			}
			
			PVector end = new PVector(startCurve.x - scale/2 * marker.cos(dir), startCurve.y - scale/2 * marker.sin(dir)); // initial start + the length * cos(direction facing)
			marker.line(startCurve.x, startCurve.y, end.x, end.y);
			
			start = new PVector(end.x, end.y); // end point is new start point
			dir += changeDir;
			end = new PVector(start.x - scale/2 * marker.cos(dir), start.y - scale/2 * marker.sin(dir));

			marker.line(start.x, start.y, end.x, end.y);
			
			start.x = end.x; start.y = end.y; // end point is new start point
			dir += changeDir;
			end = new PVector(start.x - scale/2 * marker.cos(dir), start.y - scale/2 * marker.sin(dir));
			
			marker.line(start.x, start.y, end.x, end.y);
			return new float[][] {
				{startCurve.x, startCurve.y},
				{end.x, end.y}
			};
		}
		
		//if facing up, rotate top left, top right
		//if facing left, rotate top left and 
		float changeTopLeft; float changeBottomLeft; float changeBottomRight; float changeTopRight;
		if(Math.abs(dir - 3*pi/2) < 0.0001) { // up
			changeTopLeft = -pi/2;
			changeBottomLeft = 0;
			changeBottomRight = 0;
			changeTopRight = pi/2;
		} else if(Math.abs(dir - 0) < 0.0001) { // right
			changeTopLeft = 0;
			changeBottomLeft = 0;
			changeBottomRight = +pi/2;
			changeTopRight = -pi/2;
		} else if(Math.abs(dir - pi/2) < 0.0001) { // down
			changeTopLeft = 0;
			changeBottomLeft = pi/2;
			changeBottomRight = -pi/2;
			changeTopRight = 0;
		} else { 
			
			changeTopLeft = pi/2;
			changeBottomLeft = -pi/2;
			changeBottomRight = 0;
			changeTopRight = 0;
		}

		float[][] startEnd1, startEnd2, startEnd3, startEnd4; // arrays with start point and end point
		
		if(Math.abs(dir - 0) < 0.0001) { //right
			startEnd1 = drawFailedExperiment(x + scale/2, y, dir + changeTopRight, scale/2, order - 1, marker);	//top right
			startEnd2 = drawFailedExperiment(x, y, dir + changeTopLeft, scale/2, order - 1, marker); //top left
			startEnd3 = drawFailedExperiment(x, y + scale/2, dir + changeBottomLeft, scale/2, order - 1, marker); //bottom left
			startEnd4 = drawFailedExperiment(x + scale/2, y + scale/2, dir + changeBottomRight, scale/2, order - 1, marker); //bottom right
		} else if(Math.abs(dir - 3*pi/2) < 0.0001) {
			startEnd1 = drawFailedExperiment(x, y, dir + changeTopLeft, scale/2, order - 1, marker); //top left
			startEnd2 = drawFailedExperiment(x, y + scale/2, dir + changeBottomLeft, scale/2, order - 1, marker); //bottom left
			startEnd3 = drawFailedExperiment(x + scale/2, y + scale/2, dir + changeBottomRight, scale/2, order - 1, marker); //bottom right
			startEnd4 = drawFailedExperiment(x + scale/2, y, dir + changeTopRight, scale/2, order - 1, marker);	//top right
		} else if(Math.abs(dir - pi) < 0.0001) {
			startEnd1 = drawFailedExperiment(x, y + scale/2, dir + changeBottomLeft, scale/2, order - 1, marker); //bottom left
			startEnd2 = drawFailedExperiment(x + scale/2, y + scale/2, dir + changeBottomRight, scale/2, order - 1, marker); //bottom right
			startEnd3 = drawFailedExperiment(x + scale/2, y, dir + changeTopRight, scale/2, order - 1, marker);	//top right
			startEnd4 = drawFailedExperiment(x, y, dir + changeTopLeft, scale/2, order - 1, marker); //top left
		} else { // down
			startEnd1 = drawFailedExperiment(x + scale/2, y + scale/2, dir + changeBottomRight, scale/2, order - 1, marker); //bottom right
			startEnd2 = drawFailedExperiment(x + scale/2, y, dir + changeTopRight, scale/2, order - 1, marker);	//top right
			startEnd3 = drawFailedExperiment(x, y, dir + changeTopLeft, scale/2, order - 1, marker); //top left
			startEnd4 = drawFailedExperiment(x, y + scale/2, dir + changeBottomLeft, scale/2, order - 1, marker); //bottom left
		}
		
		
		//marker.line(startEnd1[1][0], startEnd1[1][1], startEnd2[0][0], startEnd2[0][1]); // end of 1, start of 2
		//marker.line(startEnd2[1][0], startEnd2[1][1], startEnd3[0][0], startEnd3[0][1]); // end of 2, start of 3
		//marker.line(startEnd3[1][0], startEnd3[1][1], startEnd4[0][0], startEnd4[0][1]);
		return new float[][] {
			{startEnd1[0][0], startEnd1[0][1]}, // start of 1
			{startEnd4[1][0], startEnd4[1][1]} // end of 4
		};

	}
	*/
	
	/**
	private void drawHilbertCurve(float x, float y, float holeDir, float scale, int order, PApplet marker) {
		holeDir %= 2*pi; // dir in range of -2pi to 2pi
		holeDir += 2*pi; // dir is now positive
		holeDir %= 2*pi; // dir is now positive and less than 2pi 
		if(order <= 1) { //draw just a cup
			if(Math.abs(holeDir - 3*pi/2) < 0.0001 || Math.abs(holeDir - 0) < 0.0001) {  // up or down
				
				PVector start = new PVector(x + scale/4, y + scale/4);
				
				
				
			} else { // left or right
				
			}
		}
		
		if(Math.abs(holeDir - 3*pi/2) < 0.0001 || Math.abs(holeDir - 0) < 0.0001) {  // up or down
			
			PVector start = new PVector(x + scale/4, y + scale/4);
			
			
			
		} else { // left or right
			drawHilbertCurve()
		}
	}
	**/
	
	/**
	private void drawHilbertCurve(float x, float y, float holeDir, float scale, int order, PApplet marker) {
		if(order <= 1) {
			if(Math.abs(holeDir - 3*pi/2) < 0.0001) { // if facing up
				marker.line(x, y, x, y + scale); // left
				marker.line(x, y + scale, x + scale, y + scale); // down
				marker.line(x + scale, y + scale, x + scale, y); // right
			} else if(Math.abs(holeDir - 0) < 0.0001) { // if facing down
				marker.line(x, y, x, y + scale);
				marker.line(x, y, x + scale, y); // up
				marker.line(x + scale, y + scale, x + scale, y);
			}
		}
		
		drawHilbertCurve(x, y, holeDir, scale, order, marker);
		drawHilbertCurve(x, y, holeDir, scale, order, marker);
		drawHilbertCurve(x, y, holeDir, scale, order, marker);

		
	}
	**/

	

	
}
