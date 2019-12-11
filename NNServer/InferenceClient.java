import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ByteArrayOutputStream;


public class InferenceClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);

                OutputStream outstream = echoSocket.getOutputStream();
                ObjectInputStream is = new ObjectInputStream(echoSocket.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(echoSocket.getOutputStream());
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {

            // Read image from path input by user
            String imagePath;
            System.out.print("Please enter your image path: ");
            imagePath = stdIn.readLine();

            BufferedImage bufferedImage = null;
            ImageIcon image = null;

            try {
                File imageFile = new File(imagePath);
                bufferedImage = ImageIO.read(imageFile);

                // Send image object to server.
                String ext = imagePath.substring(imagePath.length()-3);

                System.out.println("Sending image to server");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, ext, bos );
                bos.flush();
                byte[] buffer = bos.toByteArray();
                os.writeObject(buffer);
                System.out.println("Successful!");

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Read label from server
            String label = (String) is.readObject();
            System.out.println("Prediction: " + label);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

