package object;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Client {
	
	private Socket echoSocket;
	private ObjectOutputStream os ;
	private ObjectInputStream is ;
	private BufferedReader stdIn ;
	
	public Client(String hostName, int portNumber) throws IOException {
		echoSocket = new Socket(hostName, portNumber);
        os = new ObjectOutputStream(echoSocket.getOutputStream());
        is = new ObjectInputStream(echoSocket.getInputStream());
        stdIn = new BufferedReader(new InputStreamReader(System.in)) ;   
	}
	
	public String send(String imagePath) throws IOException, ClassNotFoundException {
        BufferedImage bufferedImage = null;
        ImageIcon image = null;

        try {
            File imageFile = new File(imagePath);
            bufferedImage = ImageIO.read(imageFile);

            // Send image object to server.
            System.out.println("Sending image to server");
            ImageIO.write(bufferedImage, "png", echoSocket.getOutputStream());
            System.out.println("Successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO: Read label from server
        String label = (String) is.readObject();
        return label ;

	}
}
