package com.test;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Comparator;

class NameComparator implements Comparator<Player>{
    public int compare(Player p1, Player p2) {
        return p1.getName().compareTo(p2.getName());
    }
}

class AverageScoreComparator implements Comparator<Player>{
    public int compare(Player p1, Player p2) {
        return (int) (p1.getAvg() - p2.getAvg());
    }
}

public class PlayerData {
    Map<String,Player> totalPlayers;
    Map<String,Player> team;

    public PlayerData(Map<String, Player> totalPlayers, Map<String, Player> team) {
        this.totalPlayers = totalPlayers;
        this.team = team;
    }

    public void displaySinglePlayer(Player player){
        System.out.println(player.getId() + "\t" + player.getName() + "\t" + player.getMatch() + "\t" + player.getScore() + "\t" + player.getWicket() +"\t" + player.getZero_out() + "\t" + player.getPlayer_type());
    }

    public void displaytotalPlayers(){
        System.out.println("All Players: ");
        System.out.println("ID\tName\tMatches\tRuns_Scored\tWickets\tZero_out\tPlayer-type");
        totalPlayers.values().stream().collect(Collectors.toList()).stream().sorted(new NameComparator()).forEach(player -> displaySinglePlayer(player));
    }

    public boolean acceptAllPlayerDetails(){

        totalPlayers.clear();
        team.clear();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 20 players containing at least 3 bowlers and 1 wicketkeeper : ");
        int nb = 0;
        int nwk = 0;
        for(int i =0 ; i < 20; i++)
        {
            Player player = new Player();
            System.out.println("Id : ");
            player.setId(sc.nextInt());
            sc.nextLine();
            System.out.println("Name : ");
            player.setName(sc.nextLine());
            System.out.println("Match : ");
            player.setMatch(sc.nextInt());
            System.out.println("Score : ");
            player.setScore(sc.nextInt());
            player.setAvg((double) (player.getScore()/ player.getMatch()));
            System.out.println("Wicket : ");
            player.setWicket(sc.nextInt());
            System.out.println("Zero_out : ");
            player.setZero_out(sc.nextInt());
            System.out.println("Enter type of player-  1: Bowler , 2: Batsman , 3:Wicket-Keeper , 4: All-Rounder");
            switch (sc.nextInt()){
                case 1:
                    player.setPlayer_type("Bowler");
                    nb++;
                    break;
                case 2:
                    player.setPlayer_type("Batsman");
                    break;
                case 3:
                    player.setPlayer_type("Wicket-Keeper");
                    nwk++;
                    break;
                case 4:
                    player.setPlayer_type("All-Rounder");
                    break;
                default:
                    System.out.println("Player is set as batsman.");
                    player.setPlayer_type("Batsman");
                    break;

            }
            totalPlayers.put(player.getName(),player);
        }
        if(nb>=3 && nwk>=1)
            return true;
        else
            return false;
    }

        public void updatePlayerByName() throws PlayerNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Player name to be updated : ");
        String playerName = sc.nextLine();


        Player player = totalPlayers.get(playerName);
        if(player == null){
            PlayerNotFoundException playerNotFoundException = new PlayerNotFoundException("Player not found.");
            throw (playerNotFoundException);
        }
        totalPlayers.remove(playerName);
        System.out.println("Enter updated player id : ");
        player.setId(sc.nextInt());
        sc.nextLine();
        System.out.println("Enter updated player name : ");
        player.setName(sc.nextLine());
        System.out.println("Enter updated number of matches played : ");
        player.setMatch(sc.nextInt());
        System.out.println("Enter updated number of runs scored : ");
        player.setScore(sc.nextInt());
        System.out.println("Enter updated number of wickets taken : ");
        player.setWicket(sc.nextInt());
        System.out.println("Enter updated number of outs on zero : ");
        player.setZero_out(sc.nextInt());
        System.out.println("Enter updated type of player - 1: Bowler , 2: Batsman , 3:Wicket-Keeper , 4: All-Rounder");
        switch (sc.nextInt()){
            case 1:
                player.setPlayer_type("Bowler");
                break;
            case 2:
                player.setPlayer_type("Batsman");
                break;
            case 3:
                player.setPlayer_type("Wicket-Keeper");
                break;
            case 4:
                player.setPlayer_type("All-Rounder");
                break;
            default:
                System.out.println("Please select valid type");
                break;
        }
        totalPlayers.put(player.getName(),player);
    }

    public void displayFinalTeam(){
        System.out.println("Selected Players: ");
        System.out.println("Id\tName\tMatches\tScore\tWicket\tOut on zero\tPlayer type");
        team.values().stream().collect(Collectors.toList()).stream().sorted(new NameComparator()).forEach(player -> displaySinglePlayer(player));
    }
    public static void main(String[] args) throws PlayerNotFoundException {
        Map<String,Player> allPlayers = new TreeMap<>();
        Map<String,Player> selectedTeam = new TreeMap<>();
        PlayerData pd = new PlayerData(allPlayers,selectedTeam);

        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println(" 1: Display All Players \n 2: Update Player Information By Name \n 3: Display Final Team \n 4: Add Player Information \n 5: Exit");
            switch (sc.nextInt()){
                case 1:
                    pd.displaytotalPlayers();
                    break;
                case 2:
                    pd.updatePlayerByName();
                    break;
                case 3:
                    //pd.selectTeam();
                    break;
                case 4:
                    while(!pd.acceptAllPlayerDetails()){
                        System.out.println("Please ensure at least 3 bowlers and 1 wicketkeeper.");
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
