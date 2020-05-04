import processing.core.PApplet;
import processing.core.PConstants;

public class BanachCurve {
	
	private int level;
	private float scale;
	
	public BanachCurve(int level, float scale) {
		this.level = level;
		this.scale = scale;
	}
	
	public void draw(PApplet g) {
		g.ellipseMode(PConstants.RADIUS);
		drawBanachCurve(g.width/2, g.height/2, scale, level, g);
	}
	
	private void drawBanachCurve(float x, float y, float radius, int level, PApplet g) {
		g.ellipse(x, y, radius, radius);

		if(level > 1) {
			
			for(float theta = 0; !(Math.abs(theta-2*Math.PI) < 0.0001); theta += Math.PI/4) { // eight angles
				//g.ellipse(x + radius*(float)Math.cos(theta), y + radius*(float)Math.sin(theta), radius/3, radius/3);
				drawBanachCurve(x + radius*(float)Math.cos(theta), y + radius*(float)Math.sin(theta), radius/3, level-1, g);
			}
			drawBanachCurve(x, y, radius/3, level-1, g);
			//g.ellipse(x, y, radius/3, radius/3);
		}
	}
}
