package part01;

import java.time.LocalDate;
import java.util.ArrayList;

public class ImageManager {
	private ArrayList<ImageRecord> images; //ArrayList of all ImageRecords added to manager
	//Standard constructor
	public ImageManager() {
		images = new ArrayList<ImageRecord>();
	}
	//Allows for an ImageManger to be created with images inside of it.
	public ImageManager(ArrayList<ImageRecord> inputImages) {
		images = new ArrayList<ImageRecord>();
		for(int i=0;i<inputImages.size();i++) {
			images.add(inputImages.get(i));
		}
	}
	//Allows for an ImageManger to be created with an image inside of it.
	public ImageManager(ImageRecord image) {
		images = new ArrayList<ImageRecord>();
		images.add(image);
	}
	//adds an ImageRecord to the array list
	public void addImage(ImageRecord image) {
		images.add(image);
	}
	//returns an image album of the record matching the ID, if it exists
	public ImageAlbum searchID(int id) {
		ArrayList<ImageRecord> selectedImages = new ArrayList<ImageRecord>();
		
		for(int i=0;i<images.size();i++) {
			if(images.get(i).getId()==(id)) {
				selectedImages.add(images.get(i));
			}
		}
		return new ImageAlbum(selectedImages);
	}
	//returns and image album of image records containing a string in its title
	public ImageAlbum searchTitle(String title) {
		ArrayList<ImageRecord> selectedImages = new ArrayList<ImageRecord>();
		
		for(int i=0;i<images.size();i++) {
			if(images.get(i).getTitle().toLowerCase().contains(title.toLowerCase())) {
				selectedImages.add(images.get(i));
			}
		}
		return new ImageAlbum(selectedImages);
	}
	//returns an image album of image records containing a string in its description

	public ImageAlbum searchDescription(String description) {
		ArrayList<ImageRecord> selectedImages = new ArrayList<ImageRecord>();
		
		for(int i=0;i<images.size();i++) {
			if(images.get(i).getDescription().toLowerCase().contains(description.toLowerCase())) {
				selectedImages.add(images.get(i));
			}
		}
		return new ImageAlbum(selectedImages);
	}
	//returns an image album of image records of a particular image type
	public ImageAlbum searchGenre(ImageType type) {
		ArrayList<ImageRecord> selectedImages = new ArrayList<ImageRecord>();
		
		for(int i=0;i<images.size();i++) {
			if(images.get(i).getGenre().equals(type)) {
				selectedImages.add(images.get(i));
			}
		}
		return new ImageAlbum(selectedImages);
	}
	//returns an image album of image records with a taken date between 2 particular dates
	public ImageAlbum searchDates(LocalDate start, LocalDate end) {
		ArrayList<ImageRecord> selectedImages = new ArrayList<ImageRecord>();
		
		for(int i=0;i<images.size();i++) {
			if((images.get(i).getDateTaken().isAfter(start)||images.get(i).getDateTaken().isEqual(start))
					&&(images.get(i).getDateTaken().isBefore(end)||images.get(i).getDateTaken().isEqual(end))){
				selectedImages.add(images.get(i));
			}
		}
		return new ImageAlbum(selectedImages);
	}
	
}
