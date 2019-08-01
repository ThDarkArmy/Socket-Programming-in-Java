import java.net.*;
import java.io.*;

class MessageWrite implements Runnable{
  private static Socket socket=null;
  private BufferedReader input=null;
  private static DataOutputStream sendMsg = null;

  MessageWrite(Socket socket) {
    this.socket = socket;
    try {
        input = new BufferedReader(new InputStreamReader(System.in));
        sendMsg = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
  public void run(){
    String message="";
    try{
      while(!message.equals("over")){
      message=input.readLine();
      sendMsg.writeUTF(message);
      if(message.equals("over")){
        System.exit(0);
      }

     }
      
      input.close();
      sendMsg.close();
      socket.close();
    }catch(IOException e){
      System.out.println(e);
    }
  }
}

class MessageRead implements Runnable{
  private static Socket socket=null;
  private BufferedReader input=null;
  private static DataInputStream rcvMsg = null;
  MessageRead(Socket socket) {
    this.socket = socket;
    try {
        //input = new BufferedReader(new InputStreamReader(System.in));
        rcvMsg = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    } catch (IOException e){
        e.printStackTrace();
    }
}
  public void run(){
    String message="";
    try{
      while(!message.equals("over")){
        message = rcvMsg.readUTF();
        System.out.println("Server: "+ message);
      if(message.equals("over")){
        System.exit(0);
      }
      }
      
      //input.close();
      rcvMsg.close();
      socket.close();
    }catch(IOException e){
      System.out.println(e);
    }
  }
}

class Client{
  private static Socket socket=null;

  Client(String address, int port){
    try{
      socket = new Socket(address,port);
      System.out.println("Connected to the server!");

    }catch(UnknownHostException u){
      System.out.println(u);
    }catch(IOException e){
      System.out.println(e);
    }
    // String message="";
    // try{
    // while(!message.equals("over")){
    //   message=input.readLine();
    //   sendMsg.writeUTF(message);
    //   message=rcvMsg.readUTF();
    //   System.out.println(message);

    // } 
    //   input.close();
    //   rcvMsg.close();
    //   sendMsg.close();
    //   socket.close();
    // }catch(IOException e){
    //   System.out.println(e);
    // }
  }
  public static void main(String[] args) throws Exception{
    Client client = new Client("127.0.0.1", 8000);
    Thread mr = new Thread(new MessageRead(socket));
    Thread mw = new Thread(new MessageWrite(socket));
    mr.start();
    mw.start();
  }
}
