package seaBattle.game.mult;

import seaBattle.game.game.Game;
import seaBattle.game.game.enums.ShutResult;

import java.util.HashSet;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Player {
    protected Game game;
    protected String name;
    protected String oppName;
    protected Socket socket; 
    protected DataInputStream in; 
    protected DataOutputStream out;

    protected boolean isOver;
    protected boolean winner;
    protected boolean myTurn;

    protected HashSet<String> previousMoves;

    private Scanner scan;

    public Player(String name, boolean myTurn) {
        this.name = name;
        this.oppName = "";
        this.myTurn = myTurn;
        this.scan = new Scanner(System.in);
        this.game = new Game();
        this.isOver = false;
        this.winner = false;
        this.previousMoves = new HashSet<String>();


        this.socket = null;
        this.in = null;
        this.out = null;
        
    }

    public void updatePlayerDataStreams() {
        try {
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.in = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {    
        try {
            this.socket.close();
            this.in.close();
            this.out.close();
            this.scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }


    public void receiveMove() {
        System.out.println("Waiting for opponents move...");
        String message = "";
        try {
            message = in.readUTF(); //receive move from other player
            System.out.println("Opponent's move: " + message);
            String[] response = processOpponentMove(message);
            if (response[0].contains("Miss")) {
                myTurn = true;
            } else if (response[3].equals("Over")) {
                isOver = true;
            }
            out.writeUTF(response[0]);
            out.writeUTF(response[1]);
            out.writeUTF(response[2]);
            out.writeUTF(response[3]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMove() {
        String move = "";
        try {
            move = getMove();
            out.writeUTF(move); //send move to other player
            String response = in.readUTF();
            String hits = in.readUTF();
            String miss = in.readUTF();
            String over = in.readUTF();
            System.out.println("Your move was a " + response);
            if(!response.contains("Hit")) {
                myTurn = false;
            }
            if (over.equals("Over")) {
                isOver = true;
                winner = true;
            }
            processPlayerMove(move, response, hits, miss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getMove() {
        String move = "";
        do {
            System.out.println();
            if(!move.equals("")) {
                System.out.println("Invalid Move");
            }
            System.out.print("Please enter your move: ");
            move = scan.nextLine();
            System.out.println();  
        } while (!validMove(move));   
        previousMoves.add(move);    
        return move;
    }

    public boolean validMove(String move) {
        if(move.equals("")) {
            return false;
        }
        if (previousMoves.contains(move)) {
            return false;
        }
        int[] check = game.oppBoard.stringToCoord(move);
        if (check[0] == -1) {
            return false;
        }
        return true;
    }

    public String[] processOpponentMove(String move) {
        String[] rval = new String[4];
        int[] coords = game.board.stringToCoord(move);
        int[] temp = new int[2];
        temp[0] = coords[1];
        temp[1] = coords[0];
        int option = game.board.updateBoard(temp);
        if(game.gameOver()) {
            rval[3] = "Over";
        } else {
            rval[3] = "";
        }
        switch(option) {
            case 1:
                rval[0] = ShutResult.SHUT_INJURED.getResult();
                break;
            case 2:
                rval[0] = ShutResult.SHUT_KILLED.getResult();
                break;
            default:
                rval[0] = ShutResult.SHUT_MISSED.getResult();
        }
        rval[1] = game.board.hits;
        rval[2] = game.board.miss;
        return rval;
    }

    public void processPlayerMove(String move, String response, String hits, String miss) {
        int[] coords = game.oppBoard.stringToCoord(move);
        if (response.equals(ShutResult.SHUT_MISSED.getResult())) {
            game.oppBoard.setValue(coords[1], coords[0], 3);
        } else if (response.equals(ShutResult.SHUT_INJURED.getResult())) {
            game.oppBoard.setValue(coords[1], coords[0], 2);
        } else if (response.equals(ShutResult.SHUT_KILLED.getResult())) {
            String[] hitArray = hits.split(" ");
            String[] missArray = miss.split(" ");
            for (int i = 0; i < hitArray.length; i++) {
                int length = hitArray[i].length();
                int a;
                int b;
                if (length <= 2) {
                    a = hitArray[i].charAt(0) - '0';
                    b = hitArray[i].charAt(1) - '0';
                }
                else {
                    a = Integer.parseInt(hitArray[i].substring(0, length / 2));
                    b = Integer.parseInt(hitArray[i].substring(length/2));
                }

                game.oppBoard.setValue(a, b, 2);
            }
            for (int i = 0; i < missArray.length; i++) {
                int length = missArray[i].length();
                int a;
                int b;
                if (length <= 2) {
                    a = missArray[i].charAt(0) - '0';
                    b = missArray[i].charAt(1) - '0';
                }
                else {
                    a = Integer.parseInt(missArray[i].substring(0, length / 2));
                    b = Integer.parseInt(missArray[i].substring(length/2));
                }
                game.oppBoard.setValue(a, b, 3);
            }
            
        }
    }

}
