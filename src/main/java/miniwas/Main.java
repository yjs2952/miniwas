package miniwas;

public class Main {
    public static void main(String[] args) {
        WasServer was = new WasServer(8080);
        was.start();
    }
}