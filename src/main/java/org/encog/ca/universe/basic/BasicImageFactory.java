package org.encog.ca.universe.basic;

import org.encog.ca.universe.ImageFactory;

import com.github.ojil.core.Image;
import com.github.ojil.core.ImageType;
import com.github.ojil.core.RgbImage;

public class BasicImageFactory implements ImageFactory {

	@Override
	public Image createImage(int width, int height, ImageType type) {
		Image newImage = null;
		Class<?> awtImageClass = null;
		try {
			awtImageClass = ClassLoader.getSystemClassLoader().loadClass(
					"java.awt.Image");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (null == awtImageClass) {
			switch (type) {
			case INT_RGB:
				newImage = new RgbImage(width, height);
				break;
			default:
				throw new RuntimeException("Not yet implemented");
			}
		} else {
			throw new RuntimeException("Not yet implemented");
		}
		return newImage;
	}
}
