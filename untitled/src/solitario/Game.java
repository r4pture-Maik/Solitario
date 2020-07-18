package solitario;

import java.util.Stack;

public class Game {
    private Stack<Card> gameDeck;
    private Stack<Card> auxDeck;
    private Card[][] gameField;
    private Card[][] finalDecks;

    public Game(){
        Deck cards = new Deck();
        auxDeck = new Stack<>();
        gameField = new Card[20][7];
        finalDecks = new Card[13][4];
        gameDeck = cards.getDeck();
        //Collections.shuffle(gameDeck);
        startGame2();

    }

    private void startGame2() {
        this.gameField[0][0] = new Card(Card.Seed.PICCHE, Card.Value.RE,13, Card.Color.NERO);
        this.gameField[0][0].setHidden(false);
        this.gameField[1][0] = new Card(Card.Seed.QUADRI, Card.Value.REGINA,12, Card.Color.ROSSO);
        this.gameField[1][0].setHidden(false);
        this.gameField[2][0] = new Card(Card.Seed.FIORI, Card.Value.FANTE,11, Card.Color.NERO);
        this.gameField[2][0].setHidden(false);
        this.gameField[3][0] = new Card(Card.Seed.QUADRI, Card.Value.DIECI,10, Card.Color.NERO);
        this.gameField[3][0].setHidden(false);
        this.gameField[0][1] = new Card(Card.Seed.CUORI, Card.Value.REGINA,12, Card.Color.ROSSO);
        this.gameField[0][1].setHidden(false);
    }


    // metodo che inizializza la griglia a inizio gioco
    private void startGame(){
        for (int i = 0; i <= 6; i++){
            for (int j = i; j <= 6; j++){
                this.gameField[i][j] = this.gameDeck.pop();//tolto l'else in quanto superfluo
                if (j==i){
                    this.gameField[i][j].setHidden(false);
                }
            }
        }
    }

    /* metodo che se invocato mostra l'ultima carta del mazzo principale, la sposta in un mazzo ausiliario
       se il mazzo principale è vuoto, verrà rimpiazzato dal mazzo ausiliario che verrà azzerato.
       Appunti: array circolare per il gamedeck.
       Cambiare l'input dello spostamento: da riga e colonna a solo colonna di destinazione
       Appunti2 parte 2: migliorare l'indentazione che ora fa schifo
     */


    public Card showCard(){
        if (gameDeck.size()==0) {
            gameDeck = auxDeck;
            auxDeck.clear();
        }
            auxDeck.push(gameDeck.pop());
            auxDeck.peek().setHidden(false);
            System.out.println(auxDeck.peek());
            return auxDeck.peek();
    }

    //Metodo che ritorna true se la carta da muovere sarà posizionata sopra una carta con colore diverso e valore maggiore di 1
    public boolean canMoveCard(Card c, int destRow, int destCol){
        if (destRow==0 &&
            this.gameField[destRow][destCol] == null &&
            c.getValues()==Card.Value.RE //Aggiunta la condizione che nella riga zero deve stare solo il RE
            // Qui non ho trovato molte informazioni in alcune versioni devi mettere il RE e in altri si mette la prima carta pescata
            || //Dato che i due if ritornano entrambi true li ho messi in OR
            this.gameField[destRow-1][destCol] != null &&
            c.getColor() != this.gameField[destRow-1][destCol].getColor() &&
            c.getRealCardValue() == this.gameField[destRow-1][destCol].getRealCardValue()-1 &&
            this.gameField[destRow][destCol] == null &&
            destCol >= 0 &&
            destCol <= 6) {
               return true;
        } else return false;
    }

    //Metodo che sposta le carte dal mazzo ausiliario alla griglia di gioco
    public void moveCardFromDeck(int destRow, int destCol){
        if (canMoveCard(auxDeck.peek(), destRow, destCol)){
            this.gameField[destRow][destCol] = auxDeck.pop();
        }else System.out.println("Mossa non valida");
    }

    //Metodo che sposta le carte all'interno della griglia di gioco
    public void moveCardFromGrid(int rowStart, int colStart, int rowDest, int colDest){
        if (canMoveCard(this.gameField[rowStart][colStart], rowDest, colDest) && this.gameField[rowStart+1][colStart]==null){
            this.gameField[rowDest][colDest] = this.gameField[rowStart][colStart];
            this.gameField[rowStart][colStart] = null;
        }else System.out.println("Mossa non valida");
    }

//Si deve fare si che si sceglie la carta e poi solo la colonna di destinazione

    public void moveCardGroup(int rowStart, int colStart, int rowDest, int colDest){
                if (canMoveCard(this.gameField[rowStart][colStart],rowDest,colDest)
                    && !this.gameField[rowStart][colStart].getHidden()){
                    do{
                        this.gameField[rowDest][colDest]=this.gameField[rowStart][colStart];
                        this.gameField[rowStart][colStart]=null;
                        rowStart++;
                        rowDest++;
                    }while(this.gameField[rowStart][colStart]!=null);
                }else System.out.println("Errore: impossibile muovere le carte");
    }

    //Bisogna creare il "posto" per gli assi e quindi i 4 mazzi ausiliari per vincere

    public String toString() {
        String result = "";
        for (int i = 0; i < this.gameField.length; i++) {
            result += "\n[";
            for (int j = 0; j < this.gameField[i].length; j++) {
                String value = this.gameField[i][j] != null ?
                        String.valueOf(this.gameField[i][j]) /*"card"*/ : "               ";
                result += "[" + value + "]";
            }
            result += "]";
        }
        return result;
    }
}
