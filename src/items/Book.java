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
	 * @param title
	 * @param author
	 * @param genre
	 * @param releaseYear
	 * @param numPages
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
	 * @param title
	 * @param author
	 * @param genre
	 * @param releaseYear
	 * @param numPages
	 * @param loanState
	 * @param dueDate
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

	


	public void setTitle(Admin applicant, String title) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.setName(applicant, title);
		this.title = title;
	}



	public void setAuthor(Admin applicant, String author) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.author = author;
	}



	public void setGenre(Admin applicant, Genre genre) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.genre = genre;
	}



	public void setReleaseYear(Admin applicant, Year releaseYear) throws InvalidAdminException {
		if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
		this.releaseYear = releaseYear;
	}



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
