package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cz.uhk.pgrf1.c03.madr.uloha2.CanvasMouse;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;
import cz.uhk.pgrf1.c03.madr.uloha2.sort.Sort;
/**
 * trida pro vyplneni polygonu pomoci ScanLine
 * Doimplementovani kodu ze cviceni 
 */
public class ScanLineRenderer extends Renderer {
	int polColor = CanvasMouse.polColor;
	List<Line> edges = new ArrayList<>();

	public ScanLineRenderer(BufferedImage img) {
		super(img);
	}

	public void fill(Polygon pol) {
		if(pol.getSize()>0)
		{
		LineRenderer lren = new LineRenderer(img);
		// nalezeni extremu
	
		Point min = findMin(pol);
		Point max = findMax(pol);

		List<Integer> points = new ArrayList<>();
		Sort sort = new Sort();
		// prokopiruju vrcholy z polygonu
		copyEdges(pol);

		int yMin = (int) min.getY();
		int yMax = (int) max.getY();

		int x;

		for (int y = yMin; y <= yMax; y++) {
			// projit vsechny hrany a zjistit jestli existuje prusecik
			for (Line e : edges) {
				if (e.isIntersection(y)) {
					x = e.intersection(y);
					points.add(x);
				}

			}

			// Sort
			sort.sort(points,0);
			points=new ArrayList<>(sort.getSortedList());
			//java.util.Collections.sort(points);

			for (int i = 0; i < points.size() - 1; i += 2) {
				Line line = new Line(new Point(points.get(i), y), new Point(points.get(i + 1), y));
				lren.draw(line, 0xFFFFFF);
			}

			points.clear();
		}
	
		// prekresleni hranice Polygonu
		PolygonRenderer pren = new PolygonRenderer(img);
		
		pren.draw(pol,polColor);
		edges.clear();
		}

	}

	private Point findMin(Polygon pol) {
		Point min = pol.getPoint(0);
		for (int i = 0; i < pol.getSize(); i++) {
			if (pol.getPoint(i).getY() < min.getY()) {
				min = pol.getPoint(i);

			}

		}
		return min;

	}

	private Point findMax(Polygon pol) {
		Point max = pol.getPoint(0);
		for (int i = 0; i < pol.getSize(); i++) {
			if (pol.getPoint(i).getY() > max.getY()) {
				max = pol.getPoint(i);
			}

		}
		return max;

	}

	private void copyEdges(Polygon pol) {
		for (int i = 0; i < pol.getSize(); i++) {
			Line e = new Line(pol.getPoint(i), pol.getPoint((i + 1) % pol.getSize()));
			// seznam orientovany hran
			edges.add(e.getOrientedLine());

		}
	}

}
