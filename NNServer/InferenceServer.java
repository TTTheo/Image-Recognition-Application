import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;
import java.lang.*;

public class InferenceServer {

    public static int counter = -1; // image counter
    public static String imageFolder = "input/";
    public static String labelFolder = "output/";

    // Helper class to implement multi-thread
    static class ClientHandler extends Thread {

        //    private final Socket welcomeSocket;
        private final InputStream inStream;
        private final OutputStream outStream;

        ClientHandler(InputStream inStream, OutputStream outStream) {

            this.inStream = inStream;
            this.outStream = outStream;
        }

        public void run() {

            try (
                    ObjectOutputStream os = new ObjectOutputStream(outStream);
                    ObjectInputStream is = new ObjectInputStream(inStream);

                    DataInputStream dis = new DataInputStream(is);
                    BufferedInputStream bis = new BufferedInputStream(inStream)
            ) {

                // Get image and save to ./input/
                // BufferedImage rcvBufferedImage = ImageIO.read(bis);
                byte[] buffer = (byte[]) is.readObject();
                BufferedImage rcvBufferedImage = ImageIO.read(new ByteArrayInputStream(buffer));
                // is.flush();
                counter++;
                int myCounter = counter;
//                 Instant start = Instant.now();
                long start = System.currentTimeMillis()

                File outfile = new File(imageFolder + myCounter + ".jpg");
                System.out.println("before");
                ImageIO.write(rcvBufferedImage, "jpg", outfile);
                // outfile.close();
                System.out.println("after");
                // Get output prediction from ./output/
                String labelFile = labelFolder + myCounter + ".txt";
                String prediction;

                File tempFile = new File(labelFile);

                while (true) {
                    if (tempFile.exists()) {
                        prediction = readString(labelFile);
                        break;
                    }
                }

                // delete output
                if (tempFile.delete()) {
                    System.out.println("File" + labelFile + " deleted successfully");
                } else {
                    System.out.println("Failed to delete the file " + labelFile);
                }

                double InferenceTime = Double.parseDouble(prediction.split("_")[1]);
                System.out.println("Inference time " + InferenceTime);
//                 Instant finish = Instant.now();
                long finish = System.currentTimeMillis();
//                 double timeElapsed = Duration.between(start, finish).toMillis();
                long timeElapsed = finish - start;
                System.out.println("timeElapsed " + timeElapsed);
                double myTime = (double)timeElapsed-InferenceTime;
                os.writeObject(prediction.substring(0, prediction.length() - 1) + '_' + myTime);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("IO Exception in Client Handler");
                System.exit(1);
            }

        }
    }

    public static boolean createFolders(String imageFolder, String labelFolder) {
        // create folders if they don't exit
        File imageFolderFile = new File(imageFolder);
        File labelFolderFile = new File(labelFolder);
        boolean imageFolderSuccessful = true;
        boolean labelFolderSuccessful = true;

        if (!imageFolderFile.exists()) {
            imageFolderSuccessful = imageFolderFile.mkdir();
        }

        if (!labelFolderFile.exists()) {
            labelFolderSuccessful = imageFolderFile.mkdir();
        }

        return imageFolderSuccessful && labelFolderSuccessful;

    }


    private static String readString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }


    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }


        int portNumber = Integer.parseInt(args[0]);
        ServerSocket listenSocket = new ServerSocket(portNumber);

        boolean hasFolders = createFolders(imageFolder, labelFolder);


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
                break;
            }

        }
    }
}



