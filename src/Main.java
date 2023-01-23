class Main {
  public static void main(String[] args) {
    // start the server
    new Thread(() -> { 
      EchoServer.main(args); 
    }).start();
    
    // start the client
    new Thread(() -> { 
      String[] request = {"-r", "/this/is/a/test"};
      EchoClient.main(request);
    }).start();

    // additional client calls can go here
    // pass args into main if desired

    // new Thread(() -> { 
    //   String[] request = {"-r", "/this/is/a/test"};
    //   EchoClient.main(request);
    // }).start();
  }
}
