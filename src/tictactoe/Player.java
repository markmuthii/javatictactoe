package tictactoe;

public class Player {
    private final String playerName;
    
    public Player(String name){
        this.playerName = name;
    }
    
    public String getName(){
        return playerName;
    }
}
