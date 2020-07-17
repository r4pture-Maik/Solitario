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
        System.out.println(gameDeck);
        System.out.println(gameDeck.size());
        startGame();

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
            || //dato che i due if ritornano entrambi true li ho messi in OR
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

    public void moveCardGroup(int rowStart, int colStart, int rowDest, int colDest){
                if (canMoveCard(this.gameField[rowStart][colStart],rowDest,colDest)){
                    do{
                        this.gameField[rowDest][colDest]=this.gameField[rowStart][colStart];
                        this.gameField[rowStart][colStart]=null;
                        rowStart++;
                        rowDest++;
                    }while(this.gameField[rowStart+1][colStart]!=null);
                }else System.out.println("Errore: impossibile muovere le carte");
    }

    //controllo se una serie di carte sono selezionabili
    private boolean canMovecardGroup(int i, int j, int x){
        for (int k = i; k < x; k++) {
            if ((this.gameField[x + 1][j] == null) && (this.gameField[x][j] != null) &&
                (this.gameField[k][j].getRealCardValue() == this.gameField[k + 1][j].getRealCardValue() + 1)

                // (this.gameField[k][j].getColor() != this.gameField[k + 1][j].getColor())
            ) {
                return true;
            }
        } return false;
    }

    //metodo che crea un array temporaneo contenente carte della stessa colonna ordinate con valore decrescente
    public Card[] cardGroup(int i, int j, int x){
        //i e j sono la prima carta della colonna e x l'ultima della stessa colonna j
        int columnSize=x-i;
        Card[] auxArray = new Card[columnSize+1];
        int n=0;
        if (canMovecardGroup(i, j, x)) {
        for (int k = i; k <= x; k++) {
                auxArray[n] = this.gameField[k][j];
                n++;
                this.gameField[k][j] = null;
            }
            for (Card c : auxArray) {
                System.out.println("carte selezionate:\n" + c.toString());
            }
        }else System.out.println("gruppo non valido");
        return auxArray;
    }
    // muovere un gruppo di carte l'array in input è preso da "card group" e le coordinate i e j sarà la nuova posizione del nostro array
   /* public void moveCardGroup(int x, int y, int z, int i, int j){

        //if (canMoveCard(cardGroup(x,y,z)[0],i,j)) {
        if (canMoveCard(cardGroup(x,y,z)[0],i,j)) {
            System.out.println("err1");
            /*
            Card[] array = cardGroup(x,y,z);
            for (Card c : array) {
                this.gameField[i][j] = c;
                i++;
            }
            for (int k = 0; k < array.length; k++){
                array[k]=null;
            }
            for (Card c : array) {
                System.out.println(c);
            }


        }else System.out.println("mossa non valida");


        if (canMoveCard(array[0],i,j)) {
            for (Card c : array) {
                this.gameField[i][j] = c;
                i++;
            }
            for (int k = 0; k < array.length; k++){
                array[k]=null;
            }
            for (Card c : array) {
                System.out.println(c);
            }
        }else System.out.println("mossa non valida");



    }*/

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
