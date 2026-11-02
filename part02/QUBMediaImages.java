/*
 * System designed for 1920 x 1080 resolution.
 * */
package part02;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;

import console.Console;

public class QUBMediaImages {
	private static String input;
	private static Font titleFont, normalFont, normalItalicFont, normalBoldFont;
	private static ImageManager manager;

	// This formatter will allow the dates to be input and output in the required
	// dd/mm/yyyy form.

	private static DateTimeFormatter formatter;
	// A string which will be used to separate between selected images.
	private static String separator;

	public static void main(String[] args) {
		// setup
		manager = new ImageManager();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String[] options = { "Add Image", "Search", "Display All", "Exit" };
		Console menuConsole = setupMenuConsole();
		Console outputConsole = setupOutputConsole(menuConsole);
		separator = "-".repeat(200);

		// setup Fonts
		titleFont = new Font("Calibri", Font.BOLD, 40);
		normalFont = new Font("Calibri", Font.PLAIN, 30);
		normalItalicFont = new Font("Calibri", Font.ITALIC, 30);
		normalBoldFont = new Font("Calibri", Font.BOLD, 30);

		// test images
		try {
			manager.addImage(new ImageRecord("Apples", "images of many apples", ImageType.FOOD,
					LocalDate.of(2015, 01, 02), "Apples.png"));
			manager.addImage(
					new ImageRecord("Homer", "homer image", ImageType.OTHER, LocalDate.of(2019, 9, 7), "Homer.png"));
			manager.addImage(new ImageRecord("ChatGPT", "chatgpt meme", ImageType.OTHER, LocalDate.of(2020, 1, 20),
					"ChatGPT.png"));
			manager.addImage(new ImageRecord("Meme", "meme about programming", ImageType.OTHER, LocalDate.now(), "Meme.png"));
			manager.addImage(new ImageRecord("Mars", "image of mars", ImageType.ASTRONOMY, LocalDate.of(2010, 7, 18), "mars.png"));

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// program loop, will run until input is 4, therefore exiting the system. Each
		// case calls a method which corresponds to a certain required functionality.
		boolean running = true;

		while (running) {
			menuConsole.clear();
			menuConsole.setFont(titleFont);
			menuConsole.setColour(Color.blue);
			menuConsole.println("Welcome to QUB Media Images");
			menuConsole.setColour(Color.black);
			menuConsole.setFont(normalFont);
			input = menuPromptAnswer(menuConsole, "Please select an option", options);
			menuConsole.clear();

			try {
				switch (Integer.valueOf(input)) {
				case 1:// addImages
					consoleAddImage(menuConsole);
					break;
				case 2:// Search for images
					consoleSearch(menuConsole, outputConsole);
					break;
				case 3:// display all images
					displayImages(menuConsole, outputConsole, manager.searchTitle(""), "All Images");
					break;
				case 4:// exit system prompt
					if (menuExit(menuConsole)) {
						running = false;
						System.exit(0);
					}
					break;
				default:
					consoleMessage(menuConsole, "Invalid Entry, please try again.");
					break;

				}
			} catch (Exception ex) {
				consoleMessage(menuConsole, "Invalid Entry, please try again.");
			}

		}
	}

	// method which gives user options and returns answer, using parameters of a
	// message and array of options
	public static String menuPromptAnswer(Console console, String message, String[] options) {
		for (int i = 0; i < options.length; i++) {
			console.println((i + 1) + ". " + options[i]);
		}
		console.print(message + ": ");

		return console.readLn();
	}

	// first console is setup which will appear at the top
	public static Console setupMenuConsole() {
		Console console = new Console(true);
		console.setBgColour(Color.cyan);
		console.setLocationRelativeTo(null);
		console.setLocation(10, console.getY() - 250);
		console.setSize(new Dimension(1900, 400));
		console.setResizable(false);
		console.setVisible(true);
		console.setTitle("QUB Media Images");
		return console;
	}

	// second console is setup which will appear below the menu console and will be
	// used to output images.
	// Parameter of the menuConsole used to set up position of second console
	public static Console setupOutputConsole(Console menuConsole) {
		Console console = new Console(false);
		console.setBgColour(Color.cyan);
		console.setSize(new Dimension(menuConsole.getWidth() - 16, 550));
		console.setLocation(menuConsole.getX() + 8, menuConsole.getY() + menuConsole.getHeight() - 10);
		console.setResizable(false);
		console.setUndecorated(true);
		console.setFont(normalFont);
		console.setVisible(true);
		return console;
	}

	// allows an ImageIcon to be created using a thumbnail string, with validation
	// on the string also, returning null if not found.
	public static ImageIcon loadImage(String thumbnail) {
		String imagePath = "Images/" + thumbnail;
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			return new ImageIcon(imagePath);
		} else {
			return null;
		}
	}

	// allows the results of the search to be displayed, showing the image itself as
	// well as the image details.
	public static void displayImages(Console menuConsole, Console console, ImageAlbum images, String str) {
		console.clear();
		console.setVisible(true);
		console.setFont(normalFont);
		console.println("Searching for: " + str);
		console.setFont(titleFont);
		console.println("Selected Images (Earliest First): ");
		console.setFont(normalFont);
		console.println(separator);

		if (images.getFirst() == null) {
			console.println("No Images Found");
		} else {
			displayImage(console, images.getFirst());
		}
		images.getFirst();
		while (images.getNext() != null) {
			images.getPrevious();
			displayImage(console, images.getNext());
		}

	}

	// used alongside the displayImages method, focusing on each individual image,
	// also handling the scenario where the image could not be found.
	public static void displayImage(Console console, ImageRecord imageRecord) {
		console.setFont(normalFont);
		if (loadImage(imageRecord.getThumbnail()) != null) {
			console.println(loadImage(imageRecord.getThumbnail()));
		} else {
			console.setFont(normalItalicFont);
			console.println("Could not load image: " + imageRecord.getThumbnail());
			console.setFont(normalFont);
		}
		console.println(imageRecord.toString());
		console.println(separator);
	}

	// used for displaying an error message or other message to a console.
	public static void consoleMessage(Console console, String message) {
		console.clear();
		console.setFont(normalFont);
		console.println(message);
		console.setVisible(true);
		console.print("Press Enter Key to continue: ");
		console.readLn();
	}

	// allows for a pause in the system before returning to the menu, without a
	// message.
	public static void consoleMessage(Console console) {
		console.clear();
		console.setFont(normalFont);
		console.setVisible(true);
		console.print("Press Enter Key to continue: ");
		console.readLn();
	}

	// A submenu which contains the code required to add a new image to the image
	// manager, containing additional validation alongside the validation
	// in the ImageRecord, for instances such as when converting between a string
	// and another data type, e.g. String --> LocalDate
	public static void consoleAddImage(Console console) {
		String title = null;
		String description = null;
		ImageType genre = null;
		LocalDate dateTaken = null;
		String thumbnail = null;
		String input;

		console.clear();

		console.print("Enter Image Title: ");
		title = console.readLn();

		console.clear();

		console.print("Enter Image Description: ");
		description = console.readLn();

		console.clear();

		while (genre == null) {
			console.print("Enter Image Genre(Astronomy, Archietecture, Sport, Landscape, "
					+ "Portrait, Nature, Aerial, Food, Other): ");
			input = console.readLn().toUpperCase().trim();
			try {
				genre = ImageType.valueOf(input);
			} catch (IllegalArgumentException ex) {
				console.println("Invalid genre. Please try again");
			}
		}
		console.clear();
		while (dateTaken == null) {
			console.print("Enter Date Taken (dd-mm-yyyy, cannot be in future): ");
			try {
				input = console.readLn().trim();
				dateTaken = LocalDate.parse(input, formatter);
			} catch (Exception ex) {
				console.println("Invalid Date Format. Please try again");
			}
		}
		console.clear();
		console.print("Enter thumbnail file name (Including .png): ");
		thumbnail = console.readLn();
		console.clear();
		String message;
		try {
			ImageRecord newImage = new ImageRecord(title, description, genre, dateTaken, thumbnail);
			manager.addImage(newImage);
			message = "Image successfully added to system";
		} catch (Exception ex) {
			message = "Could not store new image for the following reason(s):" + ex.toString();

		}
		consoleMessage(console, message);
	}

	// A submenu for searching for images with certain criteria, creating an image
	// album and using calling the method to display them
	public static void consoleSearch(Console console, Console outputConsole) {
		boolean inSearch = true;
		while (inSearch) {
			try {
				console.clear();
				ImageAlbum resultantImages;
				console.setColour(Color.blue);
				console.println("Image Search Menu");
				console.setColour(Color.black);
				String[] options = { "Search by Unique ID", "Search by Title", "Search by Description",
						"Search by Type", "Search by Date Range", "Return to main menu" };
				input = menuPromptAnswer(console, "Select option(1-6)", options);
				console.clear();
				switch (Integer.valueOf(input)) {
				case 1:// search for unique ID
					console.print("Enter unique ID: ");
					input = console.readLn();
					resultantImages = manager.searchID(Integer.valueOf(input));
					displayImages(console, outputConsole, resultantImages, "Unique ID: " + input);
					break;
				case 2:// search for title
					console.print("Enter Title: ");
					input = console.readLn();
					resultantImages = manager.searchTitle(input);
					displayImages(console, outputConsole, resultantImages, "Title containing: " + input);
					break;
				case 3:// search for description
					console.print("Enter Description: ");
					input = console.readLn();
					resultantImages = manager.searchDescription(input);
					displayImages(console, outputConsole, resultantImages, "Description containing: " + input);
					break;
				case 4:// Search for genre
					console.print("Enter Genre (Astronomy, Archietecture, Sport, Landscape, "
							+ "Portrait, Nature, Aerial, Food, Other): ");
					input = console.readLn().trim().toUpperCase();
					try {
						resultantImages = manager.searchGenre(ImageType.valueOf(input));
						displayImages(console, outputConsole, resultantImages, "Genre: " + input+" ("+ImageType.valueOf(input).getDescription()+")");
					} catch (Exception ex) {
						consoleMessage(console, "Invalid Type, please try again.");
					}
					break;
				case 5:// Search between dates
					LocalDate start, end;

					try {
						console.print("Enter Start Date (dd-mm-yyyy): ");
						input = console.readLn();
						start = LocalDate.parse(input, formatter);

						console.print("Enter End Date (dd-mm-yyyy): ");
						input = console.readLn();
						end = LocalDate.parse(input, formatter);

						resultantImages = manager.searchDates(start, end);
						displayImages(console, outputConsole, resultantImages,
								"Images between " + start.format(formatter) + " and " + end.format(formatter));
					} catch (Exception ex) {
						consoleMessage(console, "Invalid Dates");
					}
					break;
				case 6: // return to main menu
					inSearch = false;
					break;
				default:
					consoleMessage(console, "Invalid Input");
					break;
				}
			} catch (Exception ex) {
				consoleMessage(console, "Invalid Input");
			}
		}

	}

	// method called to ensure user wants to exit system, using loop until valid
	// input.
	public static boolean menuExit(Console console) {
		input = null;
		while (input == null || (!(input.toLowerCase().equals("y"))) && (!input.toLowerCase().equals("n"))) {
			console.clear();
			console.print("Are you sure you wish to exit? (Y/N): ");
			input = console.readLn();
		}
		if (input.toLowerCase().equals("y")) {
			return true;
		}
		if (input.toLowerCase().equals("n")) {
			return false;
		}
		return false;
	}

}
