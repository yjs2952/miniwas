package miniwas;

public class Main {
    public static void main(String[] args) {
        Connector was = new Connector(8080);
        was.start();
    }
}