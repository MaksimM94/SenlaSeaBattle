package seaBattle.game;

import seaBattle.game.game.enums.GameMode;
import seaBattle.game.mult.Client;
import seaBattle.game.mult.Server;

import java.util.Scanner;

public class Menu {
    private Scanner scan;
    public Menu() {
        this.scan = new Scanner(System.in);
    }
    public void start() {
        String name = writeName();
        GameMode gameMode = selectGameMode();
        if (gameMode.equals(GameMode.MULTIPLAYER_MODE)) {
            boolean isHost = isHost();
            if (isHost) {
                Server s = new Server(name);
                s.start();
            } else {
                connectToHost(name);
            }
        }

        scan.close();
    }
    private void connectToHost(String name) {
        System.out.print("Please enter the information for the game you wish to connect to (ip:port): ");
        String[] temp = scan.nextLine().split(":");
        String ip = temp[0];
        int port = Integer.parseInt(temp[1]);
        Client c = new Client(ip, port, name);
    }
    private String writeName() {
        System.out.println("Welcome to BattleShip");
        System.out.print("Please enter your name: ");
        String name = scan.nextLine();
        System.out.println("Welcome " + name);
        return name;
    }
    private GameMode selectGameMode() {
        while (true) {
            System.out.print("Plese select game mode (Enter 'multi' or 'single'): ");
            String type = scan.nextLine();
            if (type.equals("multi")) {
                return GameMode.MULTIPLAYER_MODE;
            } else if (type.equals("single")) {
                return GameMode.SINGLE_MODE;
            } else {
                System.out.println("Error - please enter a valid option");
            }
        }
    }
    private boolean isHost() {
        while (true) {
            System.out.print("Would you like to host or connect to a game? (Enter 'host' or 'connect'): ");
            String type = scan.nextLine();
            if (type.equals("host")) {
                return true;
            } else if (type.equals("connect")) {
                return false;
            } else {
                System.out.println("Error - please enter a valid option");
            }
        }
    }
}
