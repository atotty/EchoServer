import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a server that accepts connections from EchoClients
 * and processes requests.
 */
public class EchoServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 8080;
        String request = null;
        String response = null;

        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        String[] opts = {"-h", "--help", "-p", "--port"};


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

        // check for port arg
        if (argsList.contains("--port")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("--port")+1));
        }
        else if (argsList.contains("-p")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("-p")+1));
        }


        //////////////////////////////////////////////
        // Serve clients
        //////////////////////////////////////////////

        // create server socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server listening on port " + port);

        // loop forever
        while (true) {
            try {
                // accept client connection
                socket = serverSocket.accept();

                // setup input and output streams
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());

                // get request from client
                request = (String)input.readObject();

                // TODO: STUDENT WORK
                // parse request message and create response




                // END STUDENT WORK

                // send response
                output.writeObject(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void printHelpMessage() {
        System.out.println(
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                "\nEchoServer \n\tRepresents a server that accepts connections from EchoClients and \n\tprocesses requests." +
                "\n\nOptions:" +
                        "\n\t-p, --port : Specify the port on which the server listens." +
                        "\n\t-h, --help : Print this message." +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        );
    }
}
