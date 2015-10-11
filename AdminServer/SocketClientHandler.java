import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


	public class SocketClientHandler implements Runnable {

		  private Socket client;
		  JFrame frame = new JFrame("ServerWindow");
			 JTextField dataField = new JTextField(40);
			 JTextArea messageArea = new JTextArea(8, 60);
				static BufferedReader stdIn;
			 static PrintWriter out ;
			 FileInputStream fis = null;
			    BufferedInputStream bis = null;
			    OutputStream os = null;
			    int no_of_tasks;
			    String[] tasks;
			    static String filename;
			    Date teller=new Date();
			    
		  public SocketClientHandler(Socket client) throws IOException {
			  
			this.client = client;
	        out= new PrintWriter(client.getOutputStream(),true);

			messageArea.setEditable(false);
	         frame.getContentPane().add(dataField, "North");
	         frame.getContentPane().add(new JScrollPane(messageArea), "Center");

	         }

		  public SocketClientHandler()
		  {
			  
		  }
		  @Override
		  public void run() {
			  try {
		    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 		frame.pack();
		 		frame.setVisible(true);
			System.out.println("Thread started with name:"+Thread.currentThread().getName());
			dataField.addActionListener(new ActionListener() {
	             
	             
	 			@Override
	 			public void actionPerformed(ActionEvent arg0) {
	 				// TODO Auto-generated method stub
	 				String input =dataField.getText();
	 				if(input.startsWith("file:"))
	 				{
	 					String filelocation=input.substring(5, input.length());
	 					try {
							out.println("file");
							
							if(sendingfile(filelocation))
							messageArea.append("File sent\n");
							dataField.setText("");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 				}
	 				else if(!input.startsWith("file:"))
	 				{
	 					if(input.startsWith("tracer"))
	 					{
	 						
	 						no_of_tasks=Character.getNumericValue(input.charAt(6));
	 						messageArea.append("GOing ahead with "+no_of_tasks+" tasks \n");
	 						tasks=new String[no_of_tasks];
	 						tracerprog();
	 						dataField.setText("");
	 						Thread tracer=new Thread(new tracer(tasks));
	 						tracer.start();
	 						
	 						
	 					}
	 					
	 					else if(input.startsWith("Task"))
	 					{
	 						messageArea.append("Task "+input.substring(5)+" assigned \n");
	 						out.println(input);
	 					}
	 					else
	 					{
	 						
	 			             	 out.println(input); 
	 			                 dataField.setText("");
	 			                 messageArea.append("You : "+input+"\n");
	 			 					
	 					}
	 				}
	 				else
	 					return;
	 			}
	         });
			
		    readResponse();
		   
		       } catch (IOException e) {
			 e.printStackTrace();
		       } catch (InterruptedException e) {
		         e.printStackTrace();
		       }
		   }
		  public boolean sendingfile(String location) throws IOException  
		  {
			  String FILE_TO_SEND= location;
				File myFile = new File (location);
		          byte [] mybytearray  = new byte [(int)myFile.length()];
		          try {
					fis = new FileInputStream(myFile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					messageArea.append("FileNotFound\n")
;					return false;
					
				}
		          bis = new BufferedInputStream(fis);
		          try {
					bis.read(mybytearray,0,mybytearray.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          os =  client.getOutputStream();
		       
		          os.write(mybytearray,0,mybytearray.length);
		          os.flush();
				return true;
		  }
		  public boolean tracerprog()
		  {
			  try {
		            // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(filename);
			System.out.println("this is filename"+filename);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);

		            for(int i=0;i<no_of_tasks;i++)
		            {
		            	tasks[i]= bufferedReader.readLine();
		            }
		            for(int i=0;i<no_of_tasks;i++)
		            {
		            	System.out.println(tasks[i]);
		            }

		            // Always close files.
		            bufferedReader.close();	
		            return true;
		        }
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                filename + "'");	
		            return false;
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + filename + "'");	
		            return false;
		            // Or we could just do this: 
		            // ex.printStackTrace();
		        }

		  }

		   private void readResponse() throws IOException, InterruptedException {
			String userInput;
			int flag=0;
			String name=null;
			stdIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (true) {
				userInput=stdIn.readLine();
				System.out.println("well this is the userInput : "+userInput+flag);
				if(userInput  != null && userInput!="\n")
				 {
					if(flag==0)
					{
						name=userInput;
						
						frame.setTitle(name);
						filename="/home/vrajdelhivala/Desktop/ubu/java/Server/"+name+".txt";
						File file= new File(filename);
			        	file.createNewFile();
						
			        	flag++;
					}
					else if(userInput.contains("has been completed"))
					{
						//int counter=0;
						FileWriter fileWriter =
			                    new FileWriter(filename,true);
						String g=userInput+" at "+teller.getHours()+" : "+teller.getMinutes();
						messageArea.append(g+"\n");
			                // Always wrap FileWriter in BufferedWriter.
						BufferedWriter bufferedWriter =
			                    new BufferedWriter(fileWriter);
			                
			                // Note that write() does not automatically
			                // append a newline character.
			                bufferedWriter.write(g);
			                bufferedWriter.newLine();
//			                counter++;
//			                if(counter==no_of_tasks)
//			                {
//			                	
//			                }

			                // Always close files.
			               bufferedWriter.close();
					}
					else
					{
						if(userInput.equals(""))
						{
							System.out.println("this is not needed");
						}
						else
					messageArea.append("From "+name+" : "+ userInput+"\n");
					}
				 }
				

		      }
		}
	}
