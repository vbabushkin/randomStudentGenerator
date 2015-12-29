/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package randomstudentgenerator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * @author Wild
 */
public class RandomStudentGenerator extends JFrame {
    final JFileChooser fileChooser = new JFileChooser();
    java.io.File file;
    BufferedWriter writer;
    private JLabel contact;
        private JLabel website;
    private JFrame about = new JFrame();
    
    public RandomStudentGenerator(){
        init();
        Collections.sort(studentList);
        
        
        
        JPanel mainFrame= new JPanel();//(new GridLayout(4,1,5,5));
        JPanel secondaryPanel=new JPanel(new GridLayout(1,2,50,2));
        secondaryPanel.setPreferredSize(new Dimension(450,100));
        JPanel p1 = new JPanel(new GridLayout(3,1,5,5));
        JPanel p2 = new JPanel(new GridLayout(3,1,5,5));
        JPanel p3 = new JPanel(new GridLayout(1,1,5,5));
        JPanel p4 = new JPanel();//(new GridLayout(1,2,2,2));
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        
        JPanel p7 = new JPanel(new GridLayout(1,6,2,2));
        JPanel p8 = new JPanel(new GridLayout(2,1,5,5));
        JButton jbtSave=new JButton("Save");
        JButton jbtSaveAs=new JButton("Save As");
        JButton jbtOpen=new JButton("Open");
        JButton jbtAbout=new JButton("About");
        JButton jbtSelect=new JButton("Generate");
        JButton jbtReset=new JButton("Reset");
        p1.add( new JLabel( "Add a student" ));
        final JTextField newStudentEntry = new JTextField();
        p1.add(newStudentEntry);
        JButton jbtAdd = new JButton("Add");
        p1.add(jbtAdd);
        
        p2.add( new JLabel( "Select a student to remove" ));
        final JComboBox jcbShift = new JComboBox(studentList.toArray());
        jcbShift.setForeground(Color.black);
        jcbShift.setBackground(Color.white);
        
        
        p2.add(jcbShift);
        
        JButton jbtRemove = new JButton("Remove");
        p2.add(jbtRemove);
        
        final JTextArea  textArea = new JTextArea(15, 20);
        JScrollPane  scrollPane = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setEditable(false);
    
        JButton jbtList= new JButton("List");
        
        //p2.add(textArea);
        p4.add(scrollPane);
        
        
        final JLabel randomlySelected=new JLabel();
        randomlySelected.setPreferredSize(new Dimension(450,30));
        p7.add(jbtOpen);
        p7.add(jbtSave);
        p7.add(jbtSaveAs);
        p7.add(jbtList);
        p7.add(jbtSelect);
        p7.add(jbtReset);
        p7.add(jbtAbout);
        
        
        p5.add(randomlySelected, BorderLayout.CENTER);
        
        p6.add(p7);
        
        p8.add(p5);
        p8.add(p6);
        
       
        
        
        
        secondaryPanel.add(p1);
        secondaryPanel.add(p2);
        mainFrame.add(secondaryPanel,BorderLayout.PAGE_START);
        mainFrame.add(p4,BorderLayout.CENTER);
        mainFrame.add(p8,BorderLayout.PAGE_END);
        add(mainFrame);
        
        
        

        
        
        
        
        
        /**
        *  About button
        */
        jbtAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                JPanel aboutP1 = new JPanel(new GridLayout(6,1,5,5));
                JPanel finalFrame=new JPanel();
                aboutP1.add(new JLabel("Random Student Generator v 2.1"));
                aboutP1.add(new JLabel("15 April, 2014"));
                aboutP1.add(new JLabel("author: Vahan Babushkin"));
                aboutP1.add(new JLabel("All Rights Reserved"));
                contact = new JLabel();
                website = new JLabel();

                contact.setText("<html> Contact : <a href=\"\">vahanbabushkin@gmail.com</a></html>");
                contact.setCursor(new Cursor(Cursor.HAND_CURSOR));

                website.setText("<html> Website : <a href=\"\">http://www.vaanbabushkin.narod.ru/</a></html>");
                website.setCursor(new Cursor(Cursor.HAND_CURSOR));
                aboutP1.add(contact);
                aboutP1.add(website);
                
                finalFrame.add(aboutP1);
                about.add(finalFrame);
                about.setTitle( "About" );
                about.setSize( 330 , 160 );
                about.setResizable(false);
                about.setLocationRelativeTo(null);  
                about.setVisible(true);
                sendMail(contact);
                goWebsite(website);
                
            }
        });
        
        /**
        *  Open button
        */
        jbtOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    try {
                        file=fileChooser.getSelectedFile();
                        readFile(file);
                        for (int i = 0; i < studentList.size(); i++)
                        {
                            jcbShift.addItem(studentList.toArray()[i]);
                            randomlySelected.setText(null);
                            textArea.setText(displayStudentList());
                        
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(RandomStudentGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    System.out.println("No file selected");
                }
                
            }
        });
        
        
        /**
        *  Save button
        */
        jbtSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                try {
                    writeFile(file);
                } catch (IOException ex) {
                    Logger.getLogger(RandomStudentGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        
        
        /**
        *  SaveAs button
        */
        jbtSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                 
                        file=fileChooser.getSelectedFile();
                    try {
                        writeFile(file);
                    } catch (IOException ex) {
                        Logger.getLogger(RandomStudentGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     
                }
                else {
                    System.out.println("No file selected");
                }
                
            }
        });
        
        
        
        
        
        
        /**
        *  Add button
        */
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                String newStudent=newStudentEntry.getText();
                addStudent(newStudent);
                printStudents();
                jcbShift.addItem(newStudent);
                newStudentEntry.setText(null);
                }
            });
        
        /**
        *  List button
        */
        jbtList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                
                textArea.setText(displayStudentList());
                }
            });
        
        /**
        *  Reset button
        */
        jbtReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                try {
                    readFile(file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RandomStudentGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int i = 0; i < studentList.size(); i++)
                    {
                        jcbShift.addItem(studentList.toArray()[i]);
                        randomlySelected.setText(null);
                        textArea.setText(displayStudentList());
                        
                    }
               
                }
            });
        /**
        *  Remove button
        */
        jbtRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                
                String selectedStudent=(String) jcbShift.getSelectedItem();
                jcbShift.removeItem(selectedStudent);
                studentList.remove(selectedStudent);
                Collections.sort(studentList);
                
                }
            });
        
        /**
        *  Select button
        */
        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                String res=selectRandom();
                
                randomlySelected.setText(res);
                randomlySelected.setForeground (Color.red);
                randomlySelected.setFont(new Font("Arial Black", Font.PLAIN, 24));
                randomlySelected.setHorizontalAlignment(JTextField.CENTER); 
                if (!res.equals("Reset student list"))
                    jcbShift.removeItem(res);
                //textArea.setText(displayStudentList());
                }
            });
        
    }
    
     
    
    static ArrayList studentList = new ArrayList();
    
    //init student list
    
    static void init(){
        //"Ahmed Al Mulla","Maria Alvez","Dominique L. Russo","Fatimah Modupe Ishowo-Oloko", "Kichul Jung", "Maryam Rashed Al Shehhi", "Sanaa Pirani","Judyta Tillak",  "Nabil Kenan", "Vimitha Manohar", "Tanmay Chaturvedi","Haifa Ben Romdhane", "Reem Al Junaibi"
        studentList = new ArrayList();
    }
    //resets student list
   void reset() throws FileNotFoundException{
        readFile(file);
    }
    
    //adds a student to list
    static void addStudent(String student){
        boolean flag=false;
        for(int i=0;i<studentList.size();i++){
            if(student.equals(studentList.get(i)))
                flag=true;
        }
        if(!flag)
            studentList.add(student);
        Collections.sort(studentList);
    }
    
    
    //returns the content of arraylist
    static void printStudents() {
        System.out.println("\n");
        for(int i=0;i<studentList.size();i++){
        System.out.println("Student" + (i + 1) + ":" + studentList.get(i));
        }    
    }
    //
    
    static String displayStudentList(){
        String res=new String();
        for(int i=0;i<studentList.size();i++){
        res=res+studentList.get(i);
        res=res+"\n";
        } 
        return res;
    }
    
    
    //randomly selects a student
    static String selectRandom()
    {
        String res=new String();
        int n=studentList.size();
        if(n!=0){
            long seed=System.currentTimeMillis();
            Random randomGenerator = new Random(seed);
            int randomStudentIndex = randomGenerator.nextInt(n);

            res=studentList.get(randomStudentIndex).toString();
            studentList.remove(randomStudentIndex);
            
        }
        else
        {
            res="Please reset student list";
        }
        return res;
    }
    
    
    //reading from file
     void readFile(java.io.File file) throws FileNotFoundException{
         Scanner input = new Scanner(file);
         // Read text from the file
         while (input.hasNext()) {
             addStudent(input.nextLine());
         }
        // Close the file
         input.close();
     }
     
     //writting to file
     void writeFile(java.io.File file) throws FileNotFoundException, IOException{
         writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
         for(int i=0;i<studentList.size();i++){
             writer.write(String.format("%s",studentList.get(i)));
             writer.newLine();
         }
         writer.close();
         
     }
     
     private void goWebsite(JLabel website) {
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.vaanbabushkin.narod.ru"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }

    private void sendMail(JLabel contact) {
        contact.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:vahanbabushkin@gmail.com?subject=TEST"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        RandomStudentGenerator frame =  new RandomStudentGenerator();
        frame.setTitle( "Random Student Generator" );
        frame.setSize( 650 , 520 );
        frame.setLocationRelativeTo( null ); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible( true );
        frame.setResizable(false);
        
        
    }
    
    
}
