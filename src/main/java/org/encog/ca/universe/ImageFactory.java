package org.encog.ca.universe;

import com.github.ojil.core.Image;

public interface ImageFactory {
	Image createImage(int width, int height, int type);
}
