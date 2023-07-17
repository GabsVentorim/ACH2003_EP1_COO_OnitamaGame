import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameImpl implements Game {

    private Spot[][] board = new Spot[5][5];
    private Card[] matchCards = new Card[5];
    private Player redPlayer;
    private Player bluePlayer;
    private Color playerColorTurn;
    private Card tableCard;

    public GameImpl() {
        initializeBoard();
        matchCards = Card.createCards();
        redPlayer = new Player(null, Color.RED, matchCards[0], matchCards[1]);
        bluePlayer = new Player(null, Color.BLUE, matchCards[2], matchCards[3]);
        tableCard = matchCards[4];
        playerColorTurn = matchCards[4].getColor();
    }

    public GameImpl(String redName, String blueName) {
        initializeBoard();
        matchCards = Card.createCards();
        redPlayer = new Player(redName, Color.RED, matchCards[0], matchCards[1]);
        bluePlayer = new Player(blueName, Color.BLUE, matchCards[2], matchCards[3]);
        tableCard = matchCards[4];
        playerColorTurn = matchCards[4].getColor();
    }

    public GameImpl(String redName, String blueName, Card[] cards) {
        if (cards.length < 5){
            return;
        }

        initializeBoard();
        List<Card> cardsList = Arrays.asList(cards);

        Collections.shuffle(cardsList);
        matchCards = cardsList.toArray(new Card[0]);

        redPlayer = new Player(redName, Color.RED, matchCards[0], matchCards[1]);
        bluePlayer = new Player(blueName, Color.BLUE, matchCards[2], matchCards[3]);
        tableCard = matchCards[4];
        playerColorTurn = matchCards[4].getColor();
    }

    /**
     * Inicializa o tabuleiro no começo do jogo, colocando as peças em seus seus
     * lugares e preechendo a cor dos templos e dos demais espaços
     */
    private void initializeBoard() {
        for (int i = 1; i < 4; i++)
            for (int j = 0; j < 5; j++)
                board[i][j] = new Spot(new Position(i, j));

        for (int j = 0; j < 5; j++) {
            if (j == 2) {
                board[0][j] = new Spot(new Piece(Color.BLUE, true), new Position(0, j), Color.BLUE);
                board[4][j] = new Spot(new Piece(Color.RED, true), new Position(4, j), Color.RED);
            } else {
                board[0][j] = new Spot(new Piece(Color.BLUE, false), new Position(0, j));
                board[4][j] = new Spot(new Piece(Color.RED, false), new Position(4, j));
            }
        }
    }

    public Spot[][] getBoard(){
        return board;
    }

    public Card[] getMatchCards(){
        return matchCards;
    }

    public Color getPlayerColorTurn(){
        return playerColorTurn;
    }

    public Color getSpotColor(Position position) {
        return board[position.getRow()][position.getCol()].getColor();
    }

    public Piece getPiece(Position position) {
        return board[position.getRow()][position.getCol()].getPiece();
    }

    public Card getTableCard() {
        return tableCard;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    /**
     * Método que verifica se a cardPos passado como parâmetro no método
     * makeMove pertence ao vetor Position da carta usada pelo jogador
     * 
     * @param card     Carta usada pelo jogador
     * @param cardMove Posição que a peça poderá executar
     * 
     * @return Um boolean que indica que pertence(true) ou não(false)
     */
    public boolean validCardMove(Card card, Position cardMove) {
        for (Position p : card.getPositions())
            if (p.getRow() == cardMove.getRow() && p.getCol() == cardMove.getCol())
                return true;

        return false;
    }

    public void makeMove(Card card, Position cardMove, Position currentPos)
            throws IncorrectTurnOrderException, IllegalMovementException, InvalidCardException, InvalidPieceException {
        if (getPiece(currentPos) == null)
            throw new InvalidPieceException("A peca nao esta no tabuleiro");

        if (getPiece(currentPos).getColor() != playerColorTurn)
            throw new IncorrectTurnOrderException("Nao esta na vez do jogador "
                    + getPiece(currentPos).getColor());

        if (cardMove.getRow() + currentPos.getRow() > 4 || cardMove.getRow() + currentPos.getRow() < 0
                || cardMove.getCol() + currentPos.getCol() < 0 || cardMove.getCol() + currentPos.getCol() > 4)
            throw new IllegalMovementException("Posicao fora do tabuleiro");

        if (!validCardMove(card, cardMove))
            throw new InvalidCardException("O movimento nao condiz com a carta");

        if (playerColorTurn.equals(Color.RED)) {
            redPlayer.swapCard(card, getTableCard());
            playerColorTurn = Color.BLUE;
        } else {
            bluePlayer.swapCard(card, getTableCard());
            playerColorTurn = Color.RED;
        }

        board[cardMove.getRow() + currentPos.getRow()][cardMove.getCol() + currentPos.getCol()]
                .occupySpot(board[currentPos.getRow()][currentPos.getCol()].getPiece());

        board[currentPos.getRow()][currentPos.getCol()].releaseSpot();
    }

    public boolean checkVictory(Color color) {
        if (color.equals(Color.RED))
            if (getPiece(new Position(0, 2)) != null && getPiece(new Position(0, 2)).getColor().equals(color)
                    && getPiece(new Position(0, 2)).isMaster()) {
                playerColorTurn = Color.NONE;
                return true;
            }

        if (color.equals(Color.BLUE))
            if (getPiece(new Position(4, 2)) != null && getPiece(new Position(4, 2)).getColor().equals(color)
                    && getPiece(new Position(4, 2)).isMaster()) {
                playerColorTurn = Color.NONE;
                return true;
            }

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() != color
                        && board[i][j].getPiece().isMaster() == true)
                    return false;

        playerColorTurn = Color.NONE;
        return true;
    }

    /**
     * Imprime o tabuleiro do jogo com as peças "M" para o Mester, "A" para os
     * Alunos e "-" em espaços sem peças
     */
    public void printBoard() {
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (board[i][j].getPiece() == null)
                    System.out.print("- ");
                else
                    System.out.print((board[i][j].getPiece().isMaster()) ? "M " : "A ");

            }
            System.out.println();
        }
    }

}
