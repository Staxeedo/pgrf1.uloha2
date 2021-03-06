package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;

import cz.uhk.pgrf1.c03.madr.uloha2.CanvasMouse;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
/**
 * trida pro vyplneni polygonu pomoci SeedFillu
 * Doimplementovani kodu ze cviceni 
 */
public class SeedFillRenderer extends Renderer {

	private int polCutterColor = CanvasMouse.polCutterColor;
	private int polColor = CanvasMouse.polColor;

	public SeedFillRenderer(BufferedImage img) {
		super(img);

	}

	public void draw(Point p, int color, int[][] pattern) {

		int x = (int) p.getX();
		int y = (int) p.getY();

		// Jsme v hranici okna?
		if ((x >= 0) && (y >= 0) && (x < img.getWidth()) && (y < img.getHeight())) {
			int i, j, pcolor;
			i = x % pattern.length;// u vetsiho pattern(%8) u mensiho patternu (%1)
			j = y % pattern[0].length;// u vetsiho pattern(%3) u mensiho patternu (%1)
			pcolor = pattern[i][j];

			int bgColor = img.getRGB(x, y);
			
			if (bgColor == color && color != polColor && color != polCutterColor) {

				img.setRGB(x, y, pcolor);

				draw(new Point(x + 1, y), color, pattern);
				draw(new Point(x, y + 1), color, pattern);
				draw(new Point(x - 1, y), color, pattern);
				draw(new Point(x, y - 1), color, pattern);

			}

		}

	}

}
