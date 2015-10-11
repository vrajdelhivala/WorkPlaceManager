import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;


public class tracer implements Runnable 
{

	String[] tasks;
	
	public tracer(String[] tasks) 
	{
		this.tasks=tasks;
	}

	@Override
	public void run() 
	{
		int i=0;
		// TODO Auto-generated method stub
		System.out.println("Starting to assign tasks with "+tasks.length+ "  tasks");
		SocketClientHandler sc=new SocketClientHandler();
		int number=tasks.length;
		for(int j=0;j<number;j++)
		System.out.println(tasks[j]);
		FileWriter file = null;
		try {
			System.out.println("tracer try success");
			 file = new FileWriter(SocketClientHandler.filename,true);
			 System.out.println("definately one");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	
		
		while(true)
		{
			try{
				//String response= SocketClientHandler.stdIn.readLine();
				//if(response==null|| response.contains("has been completed") || response.contains("Sorry Task couldnt be assigned "))
				
					SocketClientHandler.out.print("Task:"+tasks[i]);
					
					System.out.println("Task:"+tasks[i]);
					SocketClientHandler.out.println("");

					String r2= SocketClientHandler.stdIn.readLine();
					//SocketClientHandler.out.println(" ");
					if(!r2.contains("Sorry Task couldnt be assigned") ||r2.contains("has been completed"))
					{
						System.out.println("this is r2 "+r2);
						i++;	
						//SocketClientHandler.out.print("Task:"+tasks[i]);
						if(r2.contains("has been completed"))
						{	
							sc.out.println("");
							sc.messageArea.append(r2+"\n");
							BufferedWriter br= new BufferedWriter(file);
							br.write(r2);
							br.newLine();
							br.close();
						}
						
					}
					
					if(i==number)
					{
						
						JOptionPane.showMessageDialog(sc.frame,"All assigned Tasks have been completed", "Project Management", JOptionPane.NO_OPTION);
						break;
					}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			
		}
		

}
	public boolean assigned(int n,String s)
	{
		if(s.contains("Sorry Task couldnt be assigned"))
		{
		
			return true;
			
		}
		else
			n++; return false;
	}
}
