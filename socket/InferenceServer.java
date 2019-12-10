import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class InferenceServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        ServerSocket listenSocket = new ServerSocket(portNumber);

        while (true) {
            try {
                Socket connectSocket = listenSocket.accept();
                System.out.println("A client joined: " + connectSocket);
                InputStream ist = connectSocket.getInputStream();
                OutputStream ost = connectSocket.getOutputStream();

                ClientHandler thread = new ClientHandler(ist, ost);

                thread.start();

            } catch (Exception e) {
                listenSocket.close();
                e.printStackTrace();
            }

        }
    }
}

class ClientHandler extends Thread {

    //    private final Socket welcomeSocket;
    private final InputStream inStream;
    private final OutputStream outStream;

    ClientHandler(InputStream inStream, OutputStream outStream) {

//        this.welcomeSocket = s;
        this.inStream = inStream;
        this.outStream = outStream;
    }

    public void run() {

        try (

                ObjectOutputStream os = new ObjectOutputStream(this.outStream);
//                ObjectInputStream is = new ObjectInputStream(this.inStream);

        ) {

            // TODO: read image from client
            BufferedImage rcvBufferedImage = ImageIO.read(inStream);
            File outfile = new File("saved.png");
            ImageIO.write(rcvBufferedImage, "png", outfile);

            // TODO: send label to client
            String sndLabel = "cat";
            os.writeObject(sndLabel);

        } catch (IOException e) {
            System.err.println("IO Exception in Client Handler");
            System.exit(1);
        }

    }
}
