package solitario;
public class Card {

    enum Seed{CUORI,QUADRI,FIORI,PICCHE}
    enum Value{ASSO, DUE,TRE,QUATTRO,CINQUE,SEI,SETTE,OTTO,NOVE, DIECI, FANTE,REGINA,RE}
    enum Color{ROSSO, NERO}
    private boolean isHidden;
    private int realCardValue;
    private Seed seeds;
    private Value values;
    private Color color;
    public Card(Seed seed,Value value, int realCardValue, Color color){
        this.seeds=seed;
        this.values=value;
        this.realCardValue=realCardValue;
        this.color= color;
        this.isHidden=true;
    }

    public Seed getSeeds() {
        return seeds;
    }

    public Value getValues() {
        return values;
    }

    public int getRealCardValue() {
        return realCardValue;
    }

    public Color getColor(){
        return color;
    }

    public boolean getHidden(){
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Override
    public String toString() {
        if (getHidden()){
            return "-------------";
        }else {
            return values + " " +
                    color.toString() + " " +
                    seeds;
        }
    }
}
