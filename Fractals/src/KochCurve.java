import processing.core.PApplet;
import processing.core.PVector;


/**
  @(#)KochCurve.java


  @author kABiR batra
  @version huh 2/11?
*/
public class KochCurve {
	private int level;
	private float length;

    public KochCurve(int level, int length) {
    	// TO DO
    	this.length = length;
    	this.level = level;
    }
    
    public void draw(PApplet marker) {
    	float angle = marker.PI;
    	
    	PVector end = drawKochCurve(2*marker.width/3, marker.height/3, length, angle, level, marker);
    	
    	angle = marker.PI/3;
    	end = drawKochCurve(end.x, end.y, length, angle, level, marker);
    	angle = -marker.PI/3;
    	drawKochCurve(end.x, end.y, length, angle, level, marker);

    }

    private PVector drawKochCurve(float x, float y, float length, float theta, int level, PApplet marker) {

    	if(level < 1) {
    		PVector end = new PVector((float)(x + length * marker.cos(theta)), (float)(y + length * marker.sin(theta)));
    		marker.line(x, y, end.x, end.y);
    		return end;
    	}

    	PVector end = drawKochCurve(x, y, length/3, theta, level-1, marker);
    	end = drawKochCurve(end.x, end.y, length/3, (float)(theta + marker.PI/3), level-1, marker);
    	end = drawKochCurve(end.x, end.y, length/3, (float)(theta - marker.PI/3), level-1, marker);
    	end = drawKochCurve(end.x, end.y, length/3, (float)(theta), level-1, marker);
    	return end;

    	
    }

}
