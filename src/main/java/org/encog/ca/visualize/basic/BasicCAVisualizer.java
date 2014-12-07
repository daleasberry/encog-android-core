/*
 * Encog(tm) Core v3.3 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core

 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * For more information on Heaton Research copyrights, licenses
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.ca.visualize.basic;

import org.encog.ca.universe.DiscreteCell;
import org.encog.ca.universe.Universe;
import org.encog.ca.universe.UniverseCell;
import org.encog.ca.visualize.CAVisualizer;

import com.github.ojil.core.Image;

public class BasicCAVisualizer implements CAVisualizer {
	private final Universe universe;
	private int currentZoom;
	private int zoom = 1;
	private int width;
	private int height;
	private Integer[] pixels;
	private Integer[] raster;
	private Image currentImage;

	public BasicCAVisualizer(final Universe theUniverse) {
		universe = theUniverse;
	}

	private void fillCell(final int row, final int col, final UniverseCell cell) {

		for (int y = 0; y < currentZoom; y++) {
			int idx = (((row * currentZoom) + y) * (width * currentZoom) * 3)
					+ ((col * currentZoom) * 3);
			for (int x = 0; x < currentZoom; x++) {
				if (cell instanceof DiscreteCell) {
					if (cell.get(0) > 0) {
						pixels[idx++] = 255;
						pixels[idx++] = 255;
						pixels[idx++] = 255;
					} else {
						pixels[idx++] = 0;
						pixels[idx++] = 0;
						pixels[idx++] = 0;
					}
				} else {
					for (int i = 0; i < 3; i++) {
						final double d = (cell.get(i) + 1.0) / 2.0;
						pixels[idx++] = Math.min((int) (d * 255.0), 255);
					}
				}
			}
		}
	}

	@Override
	public Image visualize() {
		currentZoom = zoom;
		width = universe.getColumns();
		height = universe.getRows();

		final int imageSize = width * height * currentZoom * currentZoom * 3;

		if ((pixels == null) || (pixels.length != imageSize)) {

			currentImage = universe.getImageFactory().createImage(
					width * currentZoom, height * currentZoom,
					Image.TYPE_INT_RGB);
			raster = (Integer[]) currentImage.getData();
			pixels = new Integer[imageSize];
		}

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				final UniverseCell cell = universe.get(row, col);
				fillCell(row, col, cell);
			}
		}

		System.arraycopy(pixels, 0, raster, 0, pixels.length);

		return currentImage;
	}

	@Override
	public int getZoom() {
		return zoom;
	}

	@Override
	public void setZoom(final int z) {
		zoom = z;
	}
}
