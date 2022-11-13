package at.kaindorf.lernen_books.database;

import at.kaindorf.lernen_books.pojos.Book;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB_Access {
//    OTHER OBJECTS
    private DB_Database database = DB_Database.getInstance();
    private Connection connection = database.getConnection();

//    SINGLETON
    private static DB_Access theInstance;

    private DB_Access() {
        loadPreparedStatements();
    }

    public static DB_Access getInstance(){
        if(theInstance == null){
            theInstance = new DB_Access();
        }
        return theInstance;
    }

//    ////////////////////////////////
    private PreparedStatement getAutorNamesStmt;
    private PreparedStatement getGenresStmt;
    private PreparedStatement getBookDetailsStmt;
    private PreparedStatement getBookDetailsNoPublisherStmt;
    private PreparedStatement getBooksFromPublisherAndGenreStmt;
    private PreparedStatement searchBooksByNameStmt;
    private PreparedStatement searchBooksByAuthorStmt;

    private void loadPreparedStatements(){
        String getAutorNamesString = """
                SELECT first_name, last_name
                FROM books  INNER JOIN book_authors ba on books.book_id = ba.book_id
                            INNER JOIN authors a on a.author_id = ba.author_id
                WHERE ba.book_id = ?
                ORDER BY last_name;
                """;

        String getGenresString = """
                SELECT genre
                FROM books  INNER JOIN book_genres bg on books.book_id = bg.book_id
                            INNER JOIN genres g on g.genre_id = bg.genre_id
                WHERE books.book_id = ?
                ORDER BY genre;
                """;

        String getBookDetailsString = """
                SELECT title title, isbn isbn , p.name name , total_pages total_pages , rating rating, published_date published_date
                FROM books  INNER JOIN publishers p on p.publisher_id = books.publisher_id
                WHERE books.book_id = ?;
                """;

        String getBookDetailsNoPublisherString = """
                SELECT title title, isbn isbn , total_pages total_pages , rating rating, published_date published_date
                FROM books
                WHERE books.book_id = ?;
                """;

        String searchBooksByNameString = """
                SELECT title, book_id
                FROM books
                WHERE LOWER(title) LIKE '%' || ? || '%'
                ORDER BY title;
                """;


        String searchBooksByAuthorString = """
                SELECT DISTINCT title,  ba.book_id
                FROM books INNER JOIN book_authors ba on books.book_id = ba.book_id
                           INNER JOIN authors a on a.author_id = ba.author_id
                WHERE LOWER(a.last_name||a.first_name) LIKE '%' || ? || '%'
                ORDER BY title;
                """;

        String getBooksFromPublisherAndGenreString = """
                SELECT title, bg.book_id
                FROM books INNER JOIN publishers p on p.publisher_id = books.publisher_id
                           INNER JOIN book_genres bg on books.book_id = bg.book_id
                           INNER JOIN genres g on g.genre_id = bg.genre_id
                WHERE g.genre = ? AND p.name = ?
                ORDER BY title;
                """;

        try {
            getAutorNamesStmt = connection.prepareStatement(getAutorNamesString);
            getGenresStmt = connection.prepareStatement(getGenresString);
            getBookDetailsStmt = connection.prepareStatement(getBookDetailsString);
            getBookDetailsNoPublisherStmt = connection.prepareStatement(getBookDetailsNoPublisherString);
            searchBooksByNameStmt = connection.prepareStatement(searchBooksByNameString);
            searchBooksByAuthorStmt = connection.prepareStatement(searchBooksByAuthorString);
            getBooksFromPublisherAndGenreStmt = connection.prepareStatement(getBooksFromPublisherAndGenreString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    ////////////////////////////////

    public void connect() throws SQLException {
        database.connect();
    }

    public void disconnect() throws SQLException {
        database.disconnect();
    }

//    GET ALL BOOKS
    public List<Book> getAllBooks() throws SQLException {
        Statement statement = database.getStatement();
        String sqlString =  "SELECT book_id, title\n" +
                            "FROM books\n" +
                            "ORDER BY title;";

        List<Book> bookNames = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sqlString);

        while (resultSet.next()){
            bookNames.add(getDetailedInformation(resultSet.getInt("book_id")));
        }

        database.releaseStatement(statement);
        return bookNames;
    }

//    GET ALL PUBLISHERS
    public List<String> getAllPublishers() throws SQLException {
        List<String> publisherList = new ArrayList<>();

        String sqlString = "SELECT name\n" +
                "FROM publishers\n" +
                "ORDER BY name;";
        Statement statement = database.getStatement();

        ResultSet resultSet = statement.executeQuery(sqlString);
        while (resultSet.next()){
            publisherList.add(resultSet.getString("name"));
        }

        database.releaseStatement(statement);
        return publisherList;
    }

//    GET ALL GENRES
    public List<String> getAllGenres() throws SQLException {
        List<String> genreList = new ArrayList<>();

        String sqlString = "SELECT genre\n" +
                "FROM genres\n" +
                "ORDER BY genre;";
        Statement statement = database.getStatement();

        ResultSet resultSet = statement.executeQuery(sqlString);
        while (resultSet.next()){
            genreList.add(resultSet.getString("genre"));
        }

        database.releaseStatement(statement);
        return genreList;
    }


//    GET DETAILED INFORMATION
    public Book getDetailedInformation(int id) throws SQLException {
        Book book = new Book();

//        SET AUTHOR LIST
        getAutorNamesStmt.setInt(1, id);
        ResultSet resultSetAuthor = getAutorNamesStmt.executeQuery();
        while(resultSetAuthor.next()){
            String author = resultSetAuthor.getString("last_name") + " " + resultSetAuthor.getString("first_name");
            book.getAuthors().add(author);
        }

//        SET GENRE LIST
        getGenresStmt.setInt(1, id);
        ResultSet resultSetGenre = getGenresStmt.executeQuery();
        while(resultSetGenre.next()){
            String genre = resultSetGenre.getString("genre");
            book.getGenre().add(genre);
        }

//        SET OTHER INFO
        try{
//            when publishers are set
            getBookDetailsStmt.setInt(1, id);
            ResultSet resultSetInfo = getBookDetailsStmt.executeQuery();
            resultSetInfo.next();

            book.setTitle(resultSetInfo.getString("title"));
            book.setIsbnNr(resultSetInfo.getString("isbn"));
            LocalDate date = resultSetInfo.getDate("published_date").toLocalDate();
            if(date != null) book.setDate(date);
            book.setPublisher(resultSetInfo.getString("name"));
            book.setPages(resultSetInfo.getInt("total_pages"));
            book.setRating(resultSetInfo.getFloat("rating"));
        } catch (PSQLException e){
//            when publishers are NOT set --> error --> so dont set publisher
            getBookDetailsNoPublisherStmt.setInt(1, id);
            ResultSet resultSetInfo = getBookDetailsNoPublisherStmt.executeQuery();
            resultSetInfo.next();

            book.setTitle(resultSetInfo.getString("title"));
            book.setIsbnNr(resultSetInfo.getString("isbn"));
            Date date = resultSetInfo.getDate("published_date");
            if(date != null) book.setDate(LocalDate.parse(String.valueOf(date)));
            book.setPages(resultSetInfo.getInt("total_pages"));
            book.setRating(resultSetInfo.getFloat("rating"));
        }

        return book;
    }

    public List<Book> getBooksByGenreAndPublisher(String genre, String publisher) throws SQLException {
        List<Book> books = new ArrayList<>();

        if(publisher.equals("All Books")){
            return getAllBooks();
        }

        getBooksFromPublisherAndGenreStmt.setString(1, genre);
        getBooksFromPublisherAndGenreStmt.setString(2, publisher);
        ResultSet resultSetBookIds = getBooksFromPublisherAndGenreStmt.executeQuery();
        while(resultSetBookIds.next()){
            int id = resultSetBookIds.getInt("book_id");
            books.add(getDetailedInformation(id));
        }

        return books;
    }

//    FILTER BY BOOK NAME STRING
    public List<Book> getBooksByName(String searchString) throws SQLException {
        List<Book> books = new ArrayList<>();

        searchBooksByNameStmt.setString(1, searchString);

        ResultSet resultSetBookIds = searchBooksByNameStmt.executeQuery();
        while(resultSetBookIds.next()){
            int id = resultSetBookIds.getInt("book_id");
            books.add(getDetailedInformation(id));
        }

        return books;
    }

//    FILTER BY BOOK AUTHOR STRING
    public List<Book> getBooksByAuthorName(String searchString) throws SQLException {
        List<Book> books = new ArrayList<>();

        searchBooksByAuthorStmt.setString(1, searchString);

        ResultSet resultSetBookIds = searchBooksByAuthorStmt.executeQuery();
        while(resultSetBookIds.next()){
            int id = resultSetBookIds.getInt("book_id");
            books.add(getDetailedInformation(id));
        }

        return books;
    }

}
