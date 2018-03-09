import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;


public class UDPServer 
{
    public DatagramSocket socket = null;
    public static ArrayList <DatagramPacket> clients = new ArrayList<DatagramPacket>();
    public static ArrayList <InetAddress> IPs = new ArrayList <InetAddress>();
    public static ArrayList <Integer> ports = new ArrayList <Integer>();
    
//  public static HashMap <InetAddress, Integer> ipports = new HashMap <InetAddress, Integer>();

    public UDPServer() 
    {

    }
    public void createAndListenSocket() throws IOException 
    {
        try 
        {
            socket = new DatagramSocket(9876);
            byte[] incomingData = new byte[1024];
            System.out.println(clients.size());
            
        	socket.setSoTimeout(5000);

        	// Kati bela while loop rokne?
            while(true)
            {
            	
	                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
	                socket.receive(incomingPacket);               
	                InetAddress IPAddress = incomingPacket.getAddress();
	                int port = incomingPacket.getPort();  
	                
	                clients.add(incomingPacket);
	                IPs.add(IPAddress);
	                ports.add(port);
	                //ipports.put(IPAddress, port);	                
	                System.out.println(clients.size());
            }
            
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        }
    }
    
    public void sendPacket() throws IOException, InterruptedException
    {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	ObjectOutputStream os = new ObjectOutputStream(out);
    	os.writeObject(clients);
    	byte[] data = out.toByteArray();
    	
    	for (int j = 0; j < IPs.size(); j++) 
    	{
    		//Packet send garna lai IP Address?
			DatagramPacket replyPacket = new DatagramPacket(data, data.length); 
        	socket.send(replyPacket);
		} 
    }

    public static void main(String[] args) throws IOException, InterruptedException 
    {
        UDPServer server = new UDPServer();
        server.createAndListenSocket();
        server.sendPacket();        
    }
}
