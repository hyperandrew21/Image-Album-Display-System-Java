//Used my own menu, as it was said to be allowed in the Q/A file
package part01;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class QUBImages {
	private static ImageManager manager;
	private static String input;
	private static Scanner scanner;
	// This formatter will allow the dates to be input and output in the required
	// dd/mm/yyyy form.
	private static DateTimeFormatter formatter;

	public static void main(String[] args) {
		// setup
		manager = new ImageManager();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		System.out.println("Welcome to QUB Image Manager");

		// Add example images
		try {
			manager.addImage(
					new ImageRecord("Skyline of Belfast", "a painting of the skyline of belfast in a realistic style",
							ImageType.LANDSCAPE, LocalDate.of(2000, 1, 1), "Belfast_Landscape.png"));
			manager.addImage(new ImageRecord("Mars", "Image of mars taken by NASA telescope", ImageType.ASTRONOMY,
					LocalDate.of(2014, 10, 30), "Mars.png"));
			manager.addImage(new ImageRecord("Football", "Image of World Cup 2010 Jabulani Football", ImageType.SPORT,
					LocalDate.of(2010, 11, 20), "Football.png"));
			manager.addImage(new ImageRecord("Building", "Picture of a modern building in London",
					ImageType.ARCHITECTURE, LocalDate.of(2022, 7, 21), "building.png"));
			manager.addImage(new ImageRecord("Abstract Art", "Strange art piece",
					ImageType.OTHER, LocalDate.of(2022, 7, 21), "abstract.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		scanner = new Scanner(System.in);
		// program loop, containing menu, with each option doing its function or calling
		// a method to do its function.
		boolean running = true;
		while (running) {
			System.out.println("-".repeat(28) + "\n1. Add Image\n2. Search\n3. Display All\n4. Exit");
			System.out.print("Select Option(1-4):");

			try {
				input = scanner.next();
				System.out.println();

				switch (Integer.valueOf(input)) {
				case 1:// add new image
					menuAddImage();
					break;
				case 2:// search for images
					menuSearch();
					break;
				case 3:// display all images
					ImageAlbum images = manager.searchTitle("");
					menuDisplay(images);
					break;
				case 4:// exit system
					running = false;
					scanner.close();
					break;
				default:
					System.out.println("Invalid input");
					break;
				}
			} catch (Exception ex) {
				System.out.println("Invalid input");
				System.out.println();
			}
			System.out.println();
		}

	}

	// A submenu which contains the code required to add a new image to the image
	// manager, containing additional validation alongside the validation
	// in the ImageRecord, for instances such as when converting between a string
	// and another data type, e.g. String --> LocalDate
	public static void menuAddImage() {
		String title = null;
		String description = null;
		ImageType genre = null;
		LocalDate dateTaken = null;
		String thumbnail = null;
		String input;

		System.out.print("Enter Image Title: ");
		scanner.nextLine();
		title = scanner.nextLine();
		System.out.print("Enter Image Description: ");
		description = scanner.nextLine();

		while (genre == null) {
			System.out.print("Enter Image Genre (Astronomy, Archietecture, Sport, Landscape, "
					+ "Portrait, Nature, Aerial, Food, Other): ");
			input = scanner.next().toUpperCase().trim();
			try {
				genre = ImageType.valueOf(input);
			} catch (IllegalArgumentException ex) {
				System.out.println("Invalid genre. Please try again");
			}
		}
		while (dateTaken == null) {
			System.out.print("Enter Date Taken (dd-mm-yyyy, cannot be in future): ");
			try {
				input = scanner.next().trim();
				dateTaken = LocalDate.parse(input, formatter);
			} catch (Exception ex) {
				System.out.println("Invalid Date Format. Please try again");
			}
		}
		System.out.print("Enter thumbnail file name (Including .png): ");
		thumbnail = scanner.next();

		try {
			ImageRecord newImage = new ImageRecord(title, description, genre, dateTaken, thumbnail);
			manager.addImage(newImage);
			System.out.println("Image successfully added to system");
		} catch (Exception ex) {
			System.out.println("Could not store new image for the following reason(s):");
			System.out.println(ex.toString());
		}
	}

	// A submenu for searching for images with certain criteria, creating an image
	// album and using calling the method to display them
	public static void menuSearch() {
		boolean inSearch = true;
		while (inSearch) {
			System.out.println("1. Unique ID\n2. Title\n3. Description\n4. Type\n5. Date Range\n6. Return to menu");
			System.out.print("Enter search criteria (1-5), or 6 to return to menu: ");
			try {
				ImageAlbum resultantImages;
				input = scanner.next();
				switch (Integer.valueOf(input)) {
				case 1:
					System.out.print("Enter unique ID: ");
					input = scanner.next();
					resultantImages = manager.searchID(Integer.valueOf(input));
					menuDisplay(resultantImages);
					break;
				case 2:
					System.out.print("Enter Title: ");
					input = scanner.next();
					resultantImages = manager.searchTitle(input);
					menuDisplay(resultantImages);
					break;
				case 3:
					System.out.print("Enter Description: ");
					input = scanner.next();
					resultantImages = manager.searchDescription(input);
					menuDisplay(resultantImages);
					break;
				case 4:
					System.out.print("Enter Genre (Astronomy, Archietecture, Sport, Landscape, "
							+ "Portrait, Nature, Aerial, Food, Other): ");
					input = scanner.next().trim().toUpperCase();
					try {
						resultantImages = manager.searchGenre(ImageType.valueOf(input));
						menuDisplay(resultantImages);
					} catch (Exception ex) {
						System.out.println("Invalid Type, please try again.");
					}
					break;
				case 5:
					LocalDate start, end;

					try {
						System.out.print("Enter Start Date (dd-mm-yyyy): ");
						input = scanner.next();
						start = LocalDate.parse(input, formatter);

						System.out.print("Enter End Date (dd-mm-yyyy): ");
						input = scanner.next();
						end = LocalDate.parse(input, formatter);

						resultantImages = manager.searchDates(start, end);
						menuDisplay(resultantImages);
					} catch (Exception ex) {
						System.out.println("Invalid Dates");
					}
					break;
				case 6:
					inSearch=false;
					break;
				default:
					System.out.println("Invalid Input");
					break;
				}

			} catch (Exception ex) {
				System.out.println("Invalid Input");
			}
			System.out.println();
		}

	}

	// Used to display the contents of an ImageAlbum, or return a message if it is
	// empty
	public static void menuDisplay(ImageAlbum images) {
		if (images.getFirst() == null) {
			System.out.println("No images match the search criteia");
		} else {
			System.out.println("\nSelected images: \n");
			System.out.println(images.getFirst().toString());
		}
		images.getFirst();
		while (images.getNext() != null) {
			images.getPrevious();
			System.out.println(images.getNext().toString());
		}
	}

}
