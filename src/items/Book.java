package items;
import java.awt.Image;
import java.time.LocalDate;
import java.time.Year;

public class Book extends Loanable{

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
	private Year releaseDate;
	private int numPages;
	private Image coverImage;

	/**Contructor with default loan state IN_ARCHIVE
	 * @param title
	 * @param author
	 * @param genre
	 * @param releaseDate
	 * @param numPages
	 * @param coverImage
	 */
	public Book(String title, String author,
				Genre genre, Year releaseDate, 
				int numPages, Image coverImage){
		super(LoanState.IN_ARCHIVE, LocalDate.now());
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.numPages = numPages;
		this.coverImage = coverImage;
	}

	/**Constructor with default loan state IN_ARCHIVE and no cover image
	 * @param title
	 * @param author
	 * @param genre
	 * @param releaseDate
	 * @param numPages
	 */
	public Book(String title, String author,
				Genre genre, Year releaseDate, 
				int numPages){
		super(LoanState.IN_ARCHIVE, LocalDate.now());
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.numPages = numPages;
		this.coverImage = null;
	}

	public Book(String title, String author, 
				Genre genre, Year releaseDate, 
				int numPages, Image coverImage, 
				LoanState loanState, LocalDate dueDate){
		super(loanState, dueDate);
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.numPages = numPages;
		this.coverImage = coverImage;
	}

	/**Constructor with no coverImage TODO: set a default image
	 * @param title
	 * @param author
	 * @param genre
	 * @param releaseDate
	 * @param numPages
	 * @param loanState
	 * @param dueDate
	 */
	public Book(String title, String author, 
				Genre genre, Year releaseDate, 
				int numPages, 
				LoanState loanState, LocalDate dueDate){
		super(loanState, dueDate);
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.numPages = numPages;
		this.coverImage = null;
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


	public Year getReleaseDate() {
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

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", genre=" + genre + ", releaseDate=" + releaseDate
				+ ", numPages=" + numPages + "]";
	}

	
	
	
	
}
