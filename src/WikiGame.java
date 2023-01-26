import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class WikiGame implements ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta;
    private int WIDTH=1000;
    private int HEIGHT=700;
    private JPanel P1;
    private JPanel P2;
    private JTextField startLinkField;
    private JTextField endLinkField;
    private JTextArea results;
    private JButton Go;
    private JTextField searcher;
    private int maxDepth;
    private ArrayList<String> path = new ArrayList<>();
 //   public String crumbs="*";

    public static void main(String[] args) {
        WikiGame w = new WikiGame();
    }

    public WikiGame() {
        prepareGUI();
        String startLink = "";  // beginning link, where the program will start
        String endLink = "";    // ending link, where the program is trying to get to
        maxDepth = 1;           // start this at 1 or 2, and if you get it going fast, increase

        if (findLink(startLink, endLink, 0,"START=")) {
            System.out.println("found it********************************************************************");
            path.add(startLink);
        } else {
            System.out.println("did not found it********************************************************************");
        }

        // print path'
        System.out.println("path is"+path);

    }
    private void prepareGUI() {
        mainFrame = new JFrame("Angel's Layout");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2,1));



        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
//        cut.addActionListener(this);
//        copy.addActionListener(this);
//        paste.addActionListener(this);
//        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);

//        ta = new JTextArea();
//        ta.setBounds(50, 5, WIDTH-100, HEIGHT-50);
//        mainFrame.add(mb);
//        mainFrame.add(ta);
//        mainFrame.setJMenuBar(mb);

        P1= new JPanel();
        P1.setLayout(new GridLayout(1,2));
        //P2= new JPanel();
        // P2.setLayout(new GridLayout(1,1));
        startLinkField = new JTextField("Actor 1 Here");
        endLinkField = new JTextField("Actor 2 Here");
        searcher= new JTextField("Igore this");
        Go = new JButton("Go");
        Go.setActionCommand("Go");
        Go.addActionListener(new ButtonClickListener());
        results= new JTextArea("result link:");
        //Go.setActionCommand(new ButtonClickListener());  //Add button listening

        P1.add(startLinkField);
        P1.add(endLinkField);
        P1.add(searcher);
        //P2.add(Go);



        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.add(results);
        mainFrame.add(P1);
        mainFrame.add(Go);

        //   mainFrame.add()               You can add specific components into a panel and then add the panel to mainframe
        mainFrame.setVisible(true);


    }
//    public void HTMLRead()
//    {
//        try
//        {
//            System.out.println();
//            System.out.println("hello \n");
//            //URL url = new URL("https://www.milton.edu/");
//            URL url = new URL(textField.getText());
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(url.openStream())
//            );
//
//            String line;
//            while ((line = reader.readLine()) != null ) {
//
//                if(line.contains("href=\"") && line.contains("/wiki/")&& line.contains(searcher.getText()))
//                {
//                    int start = line.indexOf("href=\"") + 6;
//                    int end = line.indexOf("\"", start);
//                    String linePart = line.substring(start,end);
//                    System.out.println(linePart);
//                    // findLink(linePart, depth + 1);
//                    results.setText(results.getText() + "\n"+linePart);
//                    // linePart = new JLabel()
//                }
//
//            }
//        } catch (Exception ex)
//        {
//            System.out.println("Error: " + ex);
//        }
//    }

    // recursion method
    public boolean findLink(String startLink, String endLink, int depth, String trail) {

       System.out.println("depth is: " + depth + ", link is: " + startLink);
       System.out.println("end link is: " + endLink);
        //https://en.wikipedia.org/wiki/Arnold_Schwarzenegger
        //https://en.wikipedia.org/wiki/Joe_Weider
        // BASE CASE
        if (endLink.equals(startLink))
        {
            System.out.println("FOUND " + endLink);
            System.out.println("found!= "+trail+endLink);
            results.setText(results.getText() + "\n"+trail);

            return true;
        }
        else if (depth==2)
        {
            System.out.println("stop_______________________________________");
            return false;
        }

        // GENERAL RECURSIVE CASE
        else
        {
            try
            {
                System.out.println();
                System.out.println("hello \n");
                URL url = new URL(startLink);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );

                String line;
                while ((line = reader.readLine()) != null ) {

                    if(line.contains("href=\"") && line.contains("/wiki/"))
                    {
                        int start = line.indexOf("/wiki/");
                        int end = line.indexOf("\"", start);
                        String linePart = line.substring(start,end);
                        System.out.println("linePart is: " + linePart);
                        if (findLink("https://en.wikipedia.org" + linePart, endLink , depth + 1,trail+"https://en.wikipedia.org" + linePart+"\n")) {
                            System.out.println("FOUND IT");
                            path.add("https://en.wikipedia.org" + linePart);
                            return true;
                        }
                       // System.out.println("depth is: " + depth + ", link is: https://en.wikipedia.org" + startLink);

                        // linePart = new JLabel()
                    }

                }
            } catch (Exception ex)
            {
                System.out.println("Error: " + ex);
            }
        }

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("OK")) {
                statusLabel.setText("Ok Button clicked.");

            } else if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked.");
            } else if(command.equals("Cancel")) {
                statusLabel.setText("Cancel Button clicked.");
            }
            else if(command.equals("Go")){
                System.out.println("startlinkfield url is " + startLinkField.getText());
                System.out.println("endlinkfield url is " + endLinkField.getText());
//               findLink(startLinkField.getText(), endLinkField.getText(), 0);
                findLink(startLinkField.getText(), endLinkField.getText(), 0,"");
//                findLink(startLinkField.getText(), endLinkField.getText(), 0,"");

                System.out.println("path is: *"+path);


            }
            else{
                statusLabel.setForeground(Color.blue);
                statusLabel.setText(" = mx + b ");
            }
        }
    }
}