package utilities;

import java.awt.*;

/**
 * @author Alfred Röttger Rydahl
 * @date 22/04/2020
 * @description This class holds all the placement data for the boundaries of the boxes, which the cards
 * should be placed in.
 **/
public enum BoxBoundaries
{
	//TODO: Relative point coordinates has to be determined!
	DRAW((char)0, new Point(), new Point()),
	
	FOUNDATION0((char)1, new Point(), new Point()),
	FOUNDATION1((char)2, new Point(), new Point()),
	FOUNDATION2((char)3, new Point(), new Point()),
	FOUNDATION3((char)4, new Point(), new Point()),
	
	PILE0((char)5, new Point(), new Point()),
	PILE1((char)6, new Point(), new Point()),
	PILE2((char)7, new Point(), new Point()),
	PILE3((char)8, new Point(), new Point()),
	PILE4((char)9, new Point(), new Point()),
	PILE5((char)10, new Point(), new Point()),
	PILE6((char)11, new Point(), new Point()),
	PILE7((char)12, new Point(), new Point()),
	PILE8((char)13, new Point(), new Point());
	
	/*
	 * Point contains the relative x og y coordinates of the position on the image.
	 *
	 * Index is the position of the given card, when it's returned from the
	 * computer vision module.
	 */
	public final Point ulp, lrp;
	public final char index;
	
	BoxBoundaries(char index, Point upperLeft, Point lowerRight)
	{
		this.ulp = upperLeft;
		this.lrp = lowerRight;
		this.index = index;
	}
}
