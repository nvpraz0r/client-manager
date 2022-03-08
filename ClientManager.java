import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class ClientManager extends JFrame{

    //private variables
    private JComboBox clientsComboBox;
    private JTextField clientID;
    private JTextField clientName;
    private JTextField clientWallet;
    private String FIELD_SEP = "\t";

    /**
     * main method
     */
    
    public ClientManager() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        //check for file 
        checkFiles();
        //run GUI
        initComponents();
    }

    private void initComponents() {
        
        //
        setTitle("Proving Grounds 3");
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //
        clientsComboBox = new JComboBox();
        clientID = new JTextField();
        clientName = new JTextField();
        clientWallet = new JTextField();

        //set text field dimensions
        Dimension dim = new Dimension(150, 20);
        //set preferred sizes
        clientID.setPreferredSize(dim);
        clientName.setPreferredSize(dim);
        clientWallet.setPreferredSize(dim);
        clientsComboBox.setPreferredSize(dim);
        //set minimum sizes
        clientID.setMinimumSize(dim);
        clientName.setMinimumSize(dim);
        clientWallet.setMinimumSize(dim);
        clientsComboBox.setMinimumSize(dim);

        // for(int i = 0; i < products.length(); i++){
        //     clientsComboBox.addItem(i);
        // }
        // clientsComboBox.setSelectedIndex(0);
        // List<Client> clients = productFile.getAll();


        //declare JPanel
        JPanel panel = new JPanel();
        //set panels
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel("Clients:"), getConstraints(0, 0));
        panel.add(clientsComboBox, getConstraints(1, 0));
        panel.add(new JLabel("Client ID:"), getConstraints(0, 1));
        panel.add(clientID, getConstraints(1, 1));
        panel.add(new JLabel("Client Name:"), getConstraints(0, 2));
        panel.add(clientName, getConstraints(1, 2));
        panel.add(new JLabel("Client Wallet:"), getConstraints(0, 3));
        panel.add(clientWallet, getConstraints(1, 3));

        //action listeners for buttons
        JButton addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(e -> {
            addClientButtonClicked();
        });

        JButton deleteClientButton = new JButton("Delete Client");
        deleteClientButton.addActionListener(e -> {
            deleteClientButtonClicked();
        });

        JButton clearFieldsButton = new JButton("Clear Fields");
        clearFieldsButton.addActionListener(e -> {
            clearFieldsButtonClicked();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            exitButtonClicked();
        });

        //add button elements to buttonPanel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addClientButton);
        buttonPanel.add(deleteClientButton);
        buttonPanel.add(clearFieldsButton);
        buttonPanel.add(exitButton);

        //add combobox and textfields to panel
        GridBagConstraints c = getConstraints(0,4);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = 2;
        panel.add(buttonPanel,c);

        //add panel to the window
        add(panel, BorderLayout.CENTER);

        setVisible(true);
        setSize(400, 280);
    }
    
    public static void checkFiles(){
        File myFile = null;
        boolean doesExist = false;
        
        System.out.println("Checking files . .");
        
        try{
            myFile = new File("clientFile.txt");
            doesExist = myFile.exists();
            if(doesExist == true){
                System.out.println("File exists!");
            } else if (doesExist == false){
                System.out.println("File does not exist . .");
                System.out.println("Creating file . .");
                myFile.createNewFile();
                System.out.println("File created!");
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void addClientButtonClicked(){
        Validator sv = new Validator(this);
        if(sv.isInteger(clientID, "Client's ID") &&
           sv.isPresent(clientName, "Client's name") &&
           sv.isDouble(clientWallet, "Client's Wallet")){
               int ID = Integer.parseInt(clientID.getText());
               String name = clientName.getText();
               double wallet = Double.parseDouble(clientWallet.getText());
               try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("clientFile.txt")))){
                   out.write(ID + FIELD_SEP + name + FIELD_SEP + wallet + "\n");
                   out.close();
               }catch(IOException e){
                   return;
               }
        }
    }

    public static void deleteClientButtonClicked(){

    }

    private void clearFieldsButtonClicked() {
        clientID.setText("");
        clientName.setText("");
        clientWallet.setText("");
    }

    private void exitButtonClicked() {
        System.exit(0);
    }

    // method for getting a GridBagConstraints object
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ClientManager frame = new ClientManager();
            frame.setVisible(true);
        });
    }
}