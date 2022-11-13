package at.kaindorf.lernen_books.gui;

import at.kaindorf.lernen_books.database.DB_Access;
import at.kaindorf.lernen_books.pojos.Book;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookshopGUI {
    private DefaultListModel<Book> bookListModel = new DefaultListModel<>();
    private JList<Book> bookList;
    private JScrollPane scrollPane = new JScrollPane(bookList);

    private JPanel mainPanel;
    private JComboBox cbVerlag;
    private JComboBox cbGenre;
    private JTextField tfSearch;
    private JRadioButton rbBuch;
    private JRadioButton rbAutor;
    private JPanel pSuchen;
    private JPanel pBücher;
    private JPanel pDetails;
    private JLabel detailISBN;
    private JLabel detailSeiten;
    private JEditorPane detailGenre;
    private JLabel detailBewertungen;
    private JLabel detailDatum;
    private JLabel detailVerlag;
    private JEditorPane detailTitleAutor;

    private DB_Access dba = DB_Access.getInstance();

    public BookshopGUI() {

    }

    private void initComponents(){

//        TITLED BORDERS
        pSuchen.setBorder(BorderFactory.createTitledBorder("Suchen"));
        pBücher.setBorder(BorderFactory.createTitledBorder("Bücher"));
        pDetails.setBorder(BorderFactory.createTitledBorder("Buchdetails"));

//        RADIO GROUP
        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(rbBuch);
        rbGroup.add(rbAutor);
        rbBuch.setSelected(true);

//        SEARCH BAR
        tfSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tfSearch.setText("");
                tfSearch.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(tfSearch.getText().equals("")){
                    tfSearch.setText("Geben Sie einen Suchbegriff ein...");
                    tfSearch.setForeground(new Color(172,172,172));
                }
            }
        });

//        BOOK LIST
        bookList.setModel(bookListModel);
        pBücher.add(scrollPane, BorderLayout.CENTER);


//        COMBO BOXEN
        try {
            cbVerlag.addItem("All Books");
            dba.getAllGenres().forEach(e -> cbGenre.addItem(e));
            dba.getAllPublishers().forEach(e -> cbVerlag.addItem(e));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        LISTENER
        cbVerlag.addItemListener(this::publisherOrGenreChanged);
        cbGenre.addItemListener(this::publisherOrGenreChanged);
        bookList.addListSelectionListener(this::bookSelected);
        tfSearch.addActionListener(this::searchForBook);

//        START VALUES
        setDetailedView(new Book("Select a book!"));
    }


    //    COMBO BOX
    private void publisherOrGenreChanged(ItemEvent itemEvent) {
        String genre = String.valueOf(cbGenre.getSelectedItem());
        String publisher = String.valueOf(cbVerlag.getSelectedItem());

        List<Book> bookList = new ArrayList<>();

        try {
            bookList = dba.getBooksByGenreAndPublisher(genre, publisher);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        changeBooklist(bookList);
    }

//    DISPLAY BOOK LIST
    private void changeBooklist(List<Book> books){
        bookListModel.clear();
        books.forEach(b -> bookListModel.addElement(b));
    }

//    ON LIST ITEM CLICKED
    private void bookSelected(ListSelectionEvent listSelectionEvent) {
        Book book = bookList.getSelectedValue();
        setDetailedView(book);
    }

//    FORMAT DETAILED VIEW
    private void setDetailedView(Book book){
        if(book == null) return;
        String titleAndAuthor =  "<div style=\"font-family: Arial, Helvetica, sans-serif;\n" +
                "                text-align: center;\n" +
                "                font-size: 18px;\n" +
                "                font-weight: 700;\n" +
                "                padding-bottom: 10px;\"\n" +
                "            >" + book.getTitle() + "</div>\n" +
                "    <div style=\"font-family: Arial, Helvetica;\n" +
                "                font-style: italic;\n" +
                "                text-align: center;\n" +
                "                font-weight: 100;\n" +
                "                font-size: 16px;\"\n" +
                "            >" + book.getFormattedAuthors() + "</div>";

        detailTitleAutor.setText(titleAndAuthor);
        detailISBN.setText(book.getIsbnNr());
        detailSeiten.setText(book.getPages() + "");
        detailBewertungen.setText(book.getRating() + "");
        detailDatum.setText(book.getFormattedDate());
        detailGenre.setText(book.getFormattedGenre());
        detailVerlag.setText(book.getPublisher());
    }

//    SEARCH WITH SEARCH-PATTERN
    private void searchForBook(ActionEvent actionEvent) {
        String text = tfSearch.getText().toLowerCase();

        List<Book> books = new ArrayList<>();

        try {
            if(rbBuch.isSelected()) {
                books = dba.getBooksByName(text);
            }
            else if (rbAutor.isSelected()) {
                books = dba.getBooksByAuthorName(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        changeBooklist(books);
    }


    public static void main(String[] args) {
        BookshopGUI gui = new BookshopGUI();

        JFrame frame = new JFrame("BookshopGUI");
        frame.setContentPane(gui.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        gui.initComponents();
    }
}
