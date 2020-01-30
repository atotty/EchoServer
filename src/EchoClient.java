import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a client that connects to an EchoServer and sends requests.
 */
public class EchoClient {
    public static void main(String[] args) {
        // You may choose to change these values for testing your code
        String server = "127.0.0.1"; // 127.0.0.1 is localhost on most systems
        int port = 80;
        String requestPath= "/this/is/a/test";

        String request = null;
        String response = null;

        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        String[] opts = {"-h", "--help", "-r", "--request", "-p", "--port", "-s", "--server"};


        //////////////////////////////////////////////
        // Parse args
        //////////////////////////////////////////////

        List<String> argsList = Arrays.asList(args);

        // check for help arg
        if (argsList.contains("--help") || argsList.contains("-h")) {
            printHelpMessage();
            return;
        }

        // check all other args have form "--command param" or "-c param"
        for (int i = 0; i < args.length; i+=2)
            if (!Arrays.asList(opts).contains(args[i]))
                throw new IllegalArgumentException("Invalid arguments, pass \"--help\" or \"-h\" for help");

        // check for server arg
        if (argsList.contains("--server")) {
            server = argsList.get(argsList.indexOf("-server")+1);
        }
        else if (argsList.contains("-s")) {
            server = argsList.get(argsList.indexOf("-s")+1);
        }

        // check for port arg
        if (argsList.contains("--port")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("-port")+1));
        }
        else if (argsList.contains("-p")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("-p")+1));
        }

        // check for get arg
        if (argsList.contains("--request")) {
            requestPath = argsList.get(argsList.indexOf("--request")+1);
        }
        else if (argsList.contains("-r")) {
            requestPath = argsList.get(argsList.indexOf("-r")+1);
        }

        // args are valid and form a request
        System.out.println("Client requesting " + requestPath);


        //////////////////////////////////////////////
        // Connect to server
        //////////////////////////////////////////////

        // loop until connected to server
        while (true) {
            try {
                socket = new Socket(server, port);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                break;
            } catch (IOException e) {
                System.out.println("Could not connect to server. Do you have the correct host name and port? \nTrying again...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }


        //////////////////////////////////////////////
        // Send and receive messages
        //////////////////////////////////////////////

        try {
            // TODO: STUDENT WORK
            // create the request from the path extracted from args



            // END STUDENT WORK

            // send request
            output.writeObject(request);

            // print response
            response = (String)input.readObject();

            // close socket and print response before exiting
            socket.close();
            System.out.println("Server response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private static void printHelpMessage() {
        System.out.println(
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                "\nEchoClient \n\tRepresents a client that connects to an EchoServer and sends requests." +
                "\n\nOptions:" +
                        "\n\t-s, --server: Specify the server to contact." +
                        "\n\t-p, --port : Specify the server's port for establishing a connection." +
                        "\n\t-r, --request : Specify the resource you wish to request." +
                        "\n\t-h, --help : Print this message." +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        );
    }
}
