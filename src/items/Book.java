import java.awt.Image;
import java.util.Date;
import java.util.UUID;

public class Book {

	public static enum Genre{
		ACTION,
		FANTASY,
		ADVENTURE,
		ROMANCE,
		COMEDY,
		SCIFI,
		MYSTERY,
		THRILLER,
		HISTORICAL,
		COMIC,
		MANGA,
		CHILDREN
	}
	
	private String title;
	private String author;
	private Genre genre;
	private Date releaseDate;
	private int numPages;
	private Image coverImage;
	private final String ID;


	public Book(String title, String author, Genre genre, Date releaseDate, int numPages){
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.numPages = numPages;
		this.ID = createID(title , author, genre);
	}


	private static String createID(String title, String author, Genre genre){
		StringBuffer newID = new StringBuffer();
		newID.append(title.length() > 1 ? title.substring(0, 2) : title.charAt(0));
		newID.append(author.length() > 1 ? author.substring(0, 2) : author.charAt(0));
		newID.append("-");
		newID.append(genre.toString().substring(0, 4));
		newID.append("-");
		newID.append(UUID.randomUUID().toString());
		return newID.toString();
	}


	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Book))
			return false;
		//Book other = (Book)obj;
		return this.ID.equalsIgnoreCase(((Book)obj).ID);/* &&
			   this.title.equalsIgnoreCase(other.title) &&
			   this.author.equalsIgnoreCase(other.author) &&
			   this.genre.equals(other.genre) &&
			   this.releaseDate.equals(other.releaseDate) &&
			   this.numPages == other.numPages;*/
	}

	











	public String getTitle() {
		return title;
	}


	public String getAuthor() {
		return author;
	}


	public Genre getGenre() {
		return genre;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}


	public int getNumPages() {
		return numPages;
	}


	public Image getCoverImage() {
		return coverImage;
	}


	public String getID() {
		return ID;
	}
	
	
	
}
