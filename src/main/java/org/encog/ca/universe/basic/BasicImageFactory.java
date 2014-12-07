package org.encog.ca.universe.basic;

import org.encog.ca.universe.ImageFactory;

import com.github.ojil.core.Image;

public class BasicImageFactory implements ImageFactory {

	@Override
	public Image createImage(int width, int height, int type) {
		throw new RuntimeException("Not yet implemented");
	}
}
