package at.kaindorf.Kontoverwaltung.gui;

import at.kaindorf.Kontoverwaltung.Konto;
import at.kaindorf.Kontoverwaltung.KontoBenutzer;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class KontoGUI extends JFrame {
    private DefaultListModel<String> userModel = new DefaultListModel();

    private JLabel accountOutput;
    private JList userList;
    private JTextArea logArea;


    private Konto konto;

    public KontoGUI(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(500, 400);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

//        JList FÜR USER
        userList = new JList();
        userList.setBorder(BorderFactory.createTitledBorder("User"));
        userList.setModel(userModel);
        userList.setPreferredSize(new Dimension(120, 400));
        container.add(userList, BorderLayout.WEST);


//        popup
        JPopupMenu userListPoputMenu = new JPopupMenu();
        JMenuItem miAddUser = new JMenuItem("add user");
        JMenuItem miPerformTest = new JMenuItem("perform account test");

        userListPoputMenu.add(miAddUser);
        userListPoputMenu.add(miPerformTest);
        userList.setComponentPopupMenu(userListPoputMenu);

        miAddUser.addActionListener(this::addUser);
        miPerformTest.addActionListener(this::performTest);


//        TEXT AREA LOG
        logArea = new JTextArea();
        logArea.setBorder(BorderFactory.createTitledBorder("Log output"));
        logArea.setFont(new Font("Courier New", Font.BOLD, 14));
        logArea.setEnabled(false);
        ((DefaultCaret)logArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane(logArea); //SCROLL PANE NOT THREAD SAFE
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(logArea);

        container.add(scrollPane, BorderLayout.CENTER);

//        Popup
        JPopupMenu logAreaPoputMenu = new JPopupMenu();
        JMenuItem miAddKonto = new JMenuItem("renew Account");

        logAreaPoputMenu.add(miAddKonto);
        logArea.setComponentPopupMenu(logAreaPoputMenu);

        miAddKonto.addActionListener(this::addAccount);

//        JLabel für Account
        accountOutput = new JLabel();
        accountOutput.setBorder(BorderFactory.createTitledBorder("Account"));
        accountOutput.setText("0.0 €");
        accountOutput.setHorizontalAlignment(JLabel.RIGHT);
        accountOutput.setFont(new Font("Arial", Font.BOLD, 34));
        accountOutput.setSize(new Dimension(500, 100));

        container.add(accountOutput, BorderLayout.SOUTH);

        addUserStartup("1");
        addUserStartup("2");
        addUserStartup("3");
        addUserStartup("4");
        addUserStartup("5");
        addUserStartup("6");
        addUserStartup("7");
        addUserStartup("8");
        addUserStartup("9");
        addUserStartup("0");

    }

    //basic setup
    private void addUserStartup(String username) {
        userModel.addElement(username);
    }


    private void addUser(ActionEvent actionEvent) {
        JFrame addUserInput = new JFrame();
        String username = JOptionPane.showInputDialog(addUserInput, "Please enter user: ");

        userModel.addElement(username);
    }

    private void performTest(ActionEvent actionEvent) {
        if(konto == null){
            JFrame error = new JFrame();
            JOptionPane.showConfirmDialog(error, "You have to create an Account first!");
            return;
        }

        if(userList.getSelectedValue() != null){
            List username = userList.getSelectedValuesList();
            username.forEach(name -> new Thread(new KontoBenutzer(name.toString(), konto, logArea)).start());
        }


    }

    private void addAccount(ActionEvent actionEvent) {
        konto = new Konto(accountOutput);
        accountOutput.setText(konto.toString());
        logArea.setText("");
    }

    public static void main(String[] args) {
        KontoGUI gui = new KontoGUI();
    }
}
