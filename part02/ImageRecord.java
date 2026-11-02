package part02;

import java.time.LocalDate;

public class ImageRecord {
	private int id; //unique Image iD
	private String title; //Title of image
	private String description; //Description of Image
	private ImageType genre; //Genre of image
	private LocalDate dateTaken; //Date image was taken
	private String thumbnail; //Image file of thumbnail, must be .png

	private static int nextID = 1; //ID of next valid ImageRecord
	private static String errors = ""; //String containing any errors encountered using constructor
	
	//Constructor uses the setters for all data but the ID(it being incremented if a new record is made). The getters
	//allow for validation, with the if statement checking if there is any issues, and throwing an exception if there is, along with
	//an error message.
	public ImageRecord(String title, String description, ImageType genre, LocalDate dateTaken, String thumbnail)
			throws Exception {
		this.id = nextID;
		errors = "";
		if (!(setTitle(title) & setDescription(description) & setGenre(genre) & setDateTaken(dateTaken)
				& setThumbnail(thumbnail))) {
			throw new Exception(errors);
		} else {
			nextID++;
		}
	}

	//checks that title is not null and within suitable length, and will add error message to string if needed.
	public boolean setTitle(String title) {
		if ((title != null) && (title.length() >= 1) && (title.length() <=50)) {
			this.title = title;
			return true;
		}
		errors = errors.concat("[Title] Null or incorrect length ");
		return false;
	}
	//checks that description is not null and within suitable length, and will add error message to string if needed.
	public boolean setDescription(String description) {
		if ((description != null) && (description.length() >= 1) && (description.length() <=300)) {
			this.description = description;
			return true;
		}
		errors = errors.concat("[Description] Null or incorrect length ");
		return false;
	}
	//checks genre is not null, and will add error message to string if needed.
	public boolean setGenre(ImageType genre) {
		if (genre != null) {
			this.genre = genre;
			return true;
		}
		errors = errors.concat("[Genre] Null genre ");
		return false;
	}
	//checks that date is not null, and is not in the future, and will add error message to string if needed.
	public boolean setDateTaken(LocalDate dateTaken) {
		if (dateTaken != null && (dateTaken.isBefore(LocalDate.now()) || dateTaken.equals(LocalDate.now()))) {
			this.dateTaken = dateTaken;
			return true;
		}
		errors = errors.concat("[Date] Null or invalid date ");
		return false;
	}
	//checks thumbnail is not null, and that it is a valid .png file, and will add error message to string if needed.
	public boolean setThumbnail(String thumbnail) {
		if (thumbnail.contains(".png") && thumbnail != null&&(thumbnail.replaceAll(".png", "").length()>=1)) {
			this.thumbnail = thumbnail;
			return true;
		}
		errors = errors.concat("[Thumbnail] Null or invalid format (must be .png) ");
		return false;
	}

	// getters
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public ImageType getGenre() {
		return genre;
	}

	public LocalDate getDateTaken() {
		return dateTaken;
	}

	public String getThumbnail() {
		return thumbnail;
	}
	//Returns a string of the details of ImageRecord appropriately formatted.
	public String toString() {
		return "ID: " + id + ", Title: " + title + ", Description: " + description + ", Genre: " + genre
				+ ", Date Taken: " + dateTaken + ", Thumbnail: " + thumbnail;
	}
}
