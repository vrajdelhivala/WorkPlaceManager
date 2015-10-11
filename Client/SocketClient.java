


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient {

    private String hostname;
    private int port;
    JFrame frame = new JFrame("Client");
	 JTextField dataField = new JTextField(40);
	 static JTextArea messageArea = new JTextArea(8, 60);
	 static Socket socket;
	 JPanel controlpanel=new JPanel();
	 JCheckBox task1=new JCheckBox();
	 JCheckBox task2=new JCheckBox();
	 static String client_name;
	 static String filedir;
     Date teller= new Date();
     static FileOutputStream fos = null;
     static BufferedOutputStream bos = null;
     public final static int FILE_SIZE = 1024; 
	 
	
	 static PrintWriter out ;
	 static BufferedReader in;

    public SocketClient(String hostname, int port) throws UnknownHostException, IOException
    {
        this.hostname = hostname;
        this.port = port;
        //createWindow
        client_name = JOptionPane.showInputDialog(
                frame,
                "Enter name",
                "Welcome to the Project Management Service",
                JOptionPane.QUESTION_MESSAGE);
        filedir=JOptionPane.showInputDialog(
                frame,
                "Enter default directory to store Files",
                "Welcome to the Project Management Service",
                JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showMessageDialog(frame,"Starting", "welcome to Pm", JOptionPane.QUESTION_MESSAGE);
        messageArea.setEditable(false);
        //frame.getContentPane().add(dataField, "North");
        //frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(3,0));
        frame.add(dataField);
        frame.add(new JScrollPane(messageArea));
        frame.add(controlpanel);
        controlpanel.setLayout(new GridLayout(2,0));
        
        controlpanel.add(task1);
        //task1.setText("cool task1");
        controlpanel.add(task2);
        
       
        this.connect();

        dataField.addActionListener(new ActionListener() {
            
            
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input =dataField.getText();
            	out.println(input); 
                dataField.setText("");
                messageArea.append("You : "+input+"\n");
               
			}
        });
       
        task1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {             
              if(e.getStateChange()==1 && task1.getText()!="" )
              {
            	  
            	  
            	  //String temp="-> has been completed at ";
            		  //String temp3=teller.getHours().to+" : "+teller.getMinutes();
            	 
            		  String temp1=task1.getText();
            	  out.println(temp1+"-> has been completed");
            	  
            	  task1.setSelected(false);
            	  task1.setText("");
            	  out.println("");
             }
            }           
         });
        task2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {             
              if(e.getStateChange()==1 && task2.getText()!="")
              {
            	  //String temp="-> has been completed at ";
            	  String temp2=task2.getText();
            	  //out.println(temp2);
            	  out.println(temp2+"-> has been completed");
            
            	  task2.setSelected(false);
            	  task2.setText("");
            	  out.println("");

              }
            }           
         });
       
        //is.createWindow();
    }

    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
       
        socket = new Socket(this.hostname, this.port);
        out= new PrintWriter(socket.getOutputStream(),true);
       
        System.out.println("Connection Established");
       
    }
   /* public void confirm()
    {
    	
    	if(task1.isSelected() && task1.getText()!="")
    	{
    		out.println(task1.getText()+"-> has been completed at "+teller.getHours()+" : "+teller.getMinutes());
    		task1.setText("");
    		task1.setSelected(false);

    	}
    	else if(task2.isSelected() && task2.getText()!="")
    	{
    		out.println(task2.getText()+"-> has been completed at "+teller.getHours()+" : "+teller.getMinutes());
    		task2.setText("");
    		task2.setSelected(false);
    	
    	}
    	else
    	{
    		return;
    	}
    }
    */
    public void checkbox(String check) throws IOException
    {
    	
    	if (check.startsWith("Task:"))
    	{
    		if(task1.getText()=="")
    		{	
    			String task1line=check.substring(5,check.length());
    			task1.setText(task1line+"   assigned at  : "+ teller.getHours()+":"+teller.getMinutes());
    			//messageArea.append("Task: ++ assigned \n");
    			 JOptionPane.showMessageDialog(frame,"Task Assigned,pls check", "Project Management", JOptionPane.NO_OPTION);
    		}
    		else if(task2.getText()=="")
    		{
    			String task2line=check.substring(5,check.length());
    			task2.setText(task2line+"   assigned at  : "+ teller.getHours()+":"+teller.getMinutes());
        		//messageArea.append("Task2 assigned \n");
        		JOptionPane.showMessageDialog(frame,"Task Assigned,pls check", "Project Management", JOptionPane.NO_OPTION);

        
    		}
    		else if(task1.getText()!="" && task2.getText()!="" )
    
    		{
    			out.println("Sorry Task couldnt be assigned,waiting for empty slot");
    		}
    		else{return;}
    		
    	}
    	else
    	{
    		return;
    	}
    }
    /*
    public static void recfile() throws IOException
    {
    	int bytesRead;
    	int current=0;
    	String FILE_TO_RECEIVED="/home/vraj/Desktop/java/Client/me.txt";
    	messageArea.append("its on");
    	byte [] mybytearray  = new byte [FILE_SIZE];
         InputStream is = socket.getInputStream();
         fos = new FileOutputStream(FILE_TO_RECEIVED);
         bos = new BufferedOutputStream(fos);
         bytesRead = is.read(mybytearray,0,mybytearray.length);
         current = bytesRead;

         do {
            bytesRead =
               is.read(mybytearray, current, (mybytearray.length-current));
            if(bytesRead >= 0) current += bytesRead;
         } while(bytesRead > -1);

         bos.write(mybytearray, 0 , current);
         bos.flush();
         messageArea.append("File " + FILE_TO_RECEIVED
             + " downloaded (" + current + " bytes read)");
    }

    */
    public static void main(String arg[]) throws UnknownHostException, IOException
    {
        //Creating a SocketClient object
        SocketClient client = new SocketClient ("localhost",9991);
        in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //trying to establish connection to the server
        out.println(client_name);
        //out.println(department_name);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.pack();
		client.frame.setVisible(true);
	
		while(true)
		{ 
			

			String response= in.readLine();
			
			
				if(response.contains("file"))
				{			
	    			// JOptionPane.showMessageDialog(client.frame,"File Received", "Project Management", JOptionPane.NO_OPTION);
	
					int hour =client.teller.getHours();
			    	int mint=client.teller.getMinutes();
			    	int sec=client.teller.getSeconds();
			    	String naming="RecvAT_"+hour+"_"+mint+"_"+sec;
					String FILE_TO_RECEIVED=filedir+naming;
			    	



					 byte[] mybytearray = new byte[1024];
					 InputStream is = client.socket.getInputStream();
					 FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED);
					 BufferedOutputStream bos = new BufferedOutputStream(fos);
					 int bytesRead = is.read(mybytearray, 0, mybytearray.length);
					 bos.write(mybytearray, 0, bytesRead);
					 bos.close();
					
			    	
	    			 JOptionPane.showMessageDialog(client.frame,"File Received", "Project Management", JOptionPane.NO_OPTION);

					/*
					 */
				}
				
				
				
				else{
					client.checkbox(response);
					if(response.equals(""))
					{
						
					}
					else
					client.messageArea.append("From Admin :" +response+"\n");
				}
				}
    }
		
    }
