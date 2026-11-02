package part02;

import java.util.ArrayList;

public class ImageAlbum {
	private ArrayList<ImageRecord> images;
	private int lastImage;
	//Constructor creates an album containing an array list of image records,
	//also reseting the last image, as well as calling the sort function.
	public ImageAlbum(ArrayList<ImageRecord> images) {
		this.images = images;
		sortImages();
		this.lastImage = -1;
	}
	//Returns the first image record, if there is one.
	public ImageRecord getFirst() {
		if (images.size() > 0) {
			lastImage = 0;
			return images.get(0);
		} else
			return null;
	}
	//Returns the next image record after the last one that was accessed, if there is one.
	public ImageRecord getNext() {
		if (lastImage < images.size() - 1) {
			if (lastImage >= 0) {
				lastImage++;
				return images.get(lastImage);
			}
		}
		return null;
	}
	//Returns the last image record before the last one that was accessed, if there is one.
	public ImageRecord getPrevious() {
		if (lastImage > 0) {
			lastImage--;
			return images.get(lastImage);
		}
		return null;
	}
	//Performs a bubble sort so that the it is sorted by earliest first.
	private void sortImages() {
		int swaps;
		do {
			swaps = 0;
			for (int i = 0; i < images.size() - 1; i++) {
				if (images.get(i).getDateTaken().isBefore(images.get(i + 1).getDateTaken())) {
					ImageRecord temp = images.get(i);
					images.set(i, images.get(i + 1));
					images.set(i + 1, temp);
					swaps++;
				}
			}
		} while (swaps > 0);
	}
}
