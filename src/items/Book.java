package items;

import java.time.LocalDate;
import java.time.Year;

import exceptions.InvalidAdminException;
import users.Admin;
import users.User;
import users.managers.PersonManager;

public class Book extends Loanable{

	/**Enum representing various book genres
	 */
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

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can update a book's information";
	
	private String title;
	private String author;
	private Genre genre;
	private Year releaseYear;
	private int numPages;


	/**Constructor with default loan state IN_ARCHIVE
	 * @param title of the book
	 * @param author of the book
	 * @param genre of the book
	 * @param releaseYear of the book
	 * @param numPages number of pages of the book
	 */
	public Book(String title, String author,
				Genre genre, Year releaseYear, 
				int numPages, String id){
		super(title, id);
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.numPages = numPages;
	}


	/**Constructor
	 * @param title of the book
	 * @param author of the book
	 * @param genre of the book
	 * @param releaseYear of the book
	 * @param numPages number of pages of the book
	 * @param loanState of the book
	 * @param borrower of the book if ON_LOAN
	 * @param dueDate of the loan
	 */
	public Book(String title, String author, 
				Genre genre, Year releaseYear, 
				int numPages, 
				LoanState loanState, String id,
				User borrower, LocalDate dueDate){
		super(title, loanState, borrower, dueDate, id);
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.numPages = numPages;
	}

	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Book))
			return false;
		return getID().equalsIgnoreCase(((Book)obj).getID());
	}

	/**Setter of the book's title
	 * @param applicant must be an admin
	 * @param title
	 * @throws InvalidAdminException if the admin is not accredited
	 */
	public void setTitle(Admin applicant, String title) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.setName(applicant, title);
		this.title = title;
	}


	/**Setter of the book's author
	 * @param applicant must be an admin
	 * @param author
	 * @throws InvalidAdminException if the admin is not accredited
	 */
	public void setAuthor(Admin applicant, String author) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.author = author;
	}


	/**Setter of the book's genre
	 * @param applicant must be an admin
	 * @param genre
	 * @throws InvalidAdminException if the admin is not accredited
	 */
	public void setGenre(Admin applicant, Genre genre) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.genre = genre;
	}


	/**Setter of the book's release year
	 * @param applicant must be an admin
	 * @param year
	 * @throws InvalidAdminException if the admin is not accredited
	 */
	public void setReleaseYear(Admin applicant, Year releaseYear) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.releaseYear = releaseYear;
	}


	/**Setter of the book's number of pages
	 * @param applicant must be an admin
	 * @param numPages
	 * @throws InvalidAdminException if the admin is not accredited
	 */
	public void setNumPages(Admin applicant, int numPages) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.numPages = numPages;
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


	public Year getReleaseYear() {
		return releaseYear;
	}


	public int getNumPages() {
		return numPages;
	}


	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", genre=" + genre + ", releaseYear=" + releaseYear
				+ ", numPages=" + numPages + ", getState()=" + getState() + ", getDueDate()=" + getDueDate()
				+ ", getBorrower()=" + getBorrower() + ", getID()=" + getID() + "]";
	}



	
	
}
