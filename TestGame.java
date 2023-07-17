import org.junit.Test;
import static org.junit.Assert.*;

public class TestGame {

    // Game
    GameImpl game1; // teste para construtor 1
    GameImpl game2;// teste para construtor 2
    GameImpl game3;// teste para construtor 3
    GameImpl gameTestInitialize; // teste para o método
    GameImpl gameTestExc;// teste para construtor 1
    GameImpl gameTestMove;// teste para método makeMove de Game
    GameImpl gameTestVic;// este para método checkVictory de Game

    @Test
    public void testGame1() {
        // Construtor 1
        game1 = new GameImpl();

        // Player em game1
        assertNull(game1.getBluePlayer().getName());
        assertNull(game1.getRedPlayer().getName());
        assertEquals(game1.getBluePlayer().getPieceColor(), Color.BLUE);
        assertEquals(game1.getRedPlayer().getPieceColor(), Color.RED);
        assertNotNull(game1.getBluePlayer().getCards());
        assertNotNull(game1.getRedPlayer().getCards());
        assertNotNull(game1.getTableCard());
    }

    @Test
    public void testGame2() {
        // Construtor 2
        game2 = new GameImpl("Cons 2 Player 1", "Cons 2 Player 2");

        // player em game2
        assertEquals(game2.getBluePlayer().getName(), "Cons 2 Player 2");
        assertEquals(game2.getRedPlayer().getName(), "Cons 2 Player 1");
        assertEquals(game2.getBluePlayer().getPieceColor(), Color.BLUE);
        assertEquals(game2.getRedPlayer().getPieceColor(), Color.RED);
        assertNotNull(game2.getBluePlayer().getCards());
        assertNotNull(game2.getRedPlayer().getCards());
        assertNotNull(game2.getTableCard());
    }

    @Test
    public void testGame3() {
        // Construtor 3
        Card[] cardsDeck = Card.createCards();
        game3 = new GameImpl("Cons 3 Player 1", "Cons 3 Player 2", cardsDeck);

        // player em game 3
        assertEquals(game3.getBluePlayer().getName(), "Cons 3 Player 2");
        assertEquals(game3.getRedPlayer().getName(), "Cons 3 Player 1");
        assertEquals(game3.getBluePlayer().getPieceColor(), Color.BLUE);
        assertEquals(game3.getRedPlayer().getPieceColor(), Color.RED);
        assertNotNull(game3.getBluePlayer().getCards());
        assertNotNull(game3.getRedPlayer().getCards());
        assertNotNull(game3.getTableCard());

    }

    @Test
    public void testInicialize() {
        gameTestInitialize = new GameImpl();

        // teste do inicializeBoard
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(gameTestInitialize.getBoard()[i][j].getColor(), Color.NONE);// da classe spot
                assertEquals(gameTestInitialize.getSpotColor(new Position(i, j)), Color.NONE);// da classe game
                assertNull(gameTestInitialize.getBoard()[i][j].getPiece());// da classe spot
                assertNull(gameTestInitialize.getPiece(new Position(i, j)));// da classe game
                assertEquals(gameTestInitialize.getBoard()[i][j].getPosition().getRow(), i);
                assertEquals(gameTestInitialize.getBoard()[i][j].getPosition().getCol(), j);
            }
        }
        for (int j = 0; j < 5; j++) {
            if (j != 2) {
                assertEquals(gameTestInitialize.getBoard()[0][j].getColor(), Color.NONE);// da classe spot
                assertEquals(gameTestInitialize.getSpotColor(new Position(0, j)), Color.NONE);// da classe game
                assertFalse(gameTestInitialize.getBoard()[0][j].getPiece().isMaster());// da classe spot
                assertFalse(gameTestInitialize.getPiece(new Position(0, j)).isMaster());// da classe game
                assertEquals(gameTestInitialize.getBoard()[0][j].getPosition().getRow(), 0);
                assertEquals(gameTestInitialize.getBoard()[0][j].getPosition().getCol(), j);
                assertEquals(gameTestInitialize.getBoard()[4][j].getColor(), Color.NONE);// da classe spot
                assertEquals(gameTestInitialize.getSpotColor(new Position(4, j)), Color.NONE);// da classe game
                assertFalse(gameTestInitialize.getBoard()[4][j].getPiece().isMaster());// da classe spot
                assertFalse(gameTestInitialize.getPiece(new Position(4, j)).isMaster());// da classe game
                assertEquals(gameTestInitialize.getBoard()[4][j].getPosition().getRow(), 4);
                assertEquals(gameTestInitialize.getBoard()[4][j].getPosition().getCol(), j);
            }
        }
        // testando Mestre Blue
        assertEquals(gameTestInitialize.getBoard()[0][2].getColor(), Color.BLUE);
        assertEquals(gameTestInitialize.getSpotColor(new Position(0, 2)), Color.BLUE);// da classe game
        assertTrue(gameTestInitialize.getBoard()[0][2].getPiece().isMaster());
        assertTrue(gameTestInitialize.getPiece(new Position(0, 2)).isMaster());// da classe game
        assertEquals(gameTestInitialize.getBoard()[0][2].getPosition().getRow(), 0);
        assertEquals(gameTestInitialize.getBoard()[0][2].getPosition().getCol(), 2);
        // testando Mestre Red
        assertEquals(gameTestInitialize.getBoard()[4][2].getColor(), Color.RED);
        assertEquals(gameTestInitialize.getSpotColor(new Position(4, 2)), Color.RED);// da classe game
        assertTrue(gameTestInitialize.getBoard()[4][2].getPiece().isMaster());
        assertTrue(gameTestInitialize.getPiece(new Position(4, 2)).isMaster());// da classe game
        assertEquals(gameTestInitialize.getBoard()[4][2].getPosition().getRow(), 4);
        assertEquals(gameTestInitialize.getBoard()[4][2].getPosition().getCol(), 2);

    }

    @Test
    public void testMakeMove() {

        // player com o construtor com cards
        Card[] cardsPlayerTest = { Card.createCards()[0], Card.createCards()[1] };
        Player playerTest = new Player("Player Name", Color.BLUE, cardsPlayerTest);
        assertArrayEquals(playerTest.getCards(), cardsPlayerTest);

        // teste métodos de Card
        Position[] testPositions = { new Position(0, -1), new Position(0, +1), new Position(-1, 0),
                new Position(+1, 0), new Position(+4, 0), new Position(-4, 0), new Position(-3, 0),
                new Position(+3, 0) };
        Card testCard = new Card("Test Card Name", Color.BLUE, testPositions);
        assertEquals(testCard.getName(), "Test Card Name");
        assertEquals(testCard.getColor(), Color.BLUE);
        assertArrayEquals(testCard.getPositions(), testPositions);
        // vetor de Card para os proxs testes
        Card[] testCards = { testCard, testCard, testCard, testCard, testCard };

        // testes do makeMove de Game
        gameTestExc = new GameImpl("Red Player", "Blue Player", testCards);

        Exception exceptionIlMovOutOfBoard = assertThrows(IllegalMovementException.class, () -> gameTestExc
                .makeMove(gameTestExc.getBluePlayer().getCards()[0], new Position(-1, 0), new Position(0, 2)));
        assertEquals(exceptionIlMovOutOfBoard.getMessage(), "Posicao fora do tabuleiro");

        Exception exceptionInvPie = assertThrows(InvalidPieceException.class, () -> gameTestExc
                .makeMove(gameTestExc.getBluePlayer().getCards()[0], new Position(+1, 0), new Position(2, 2)));
        assertEquals(exceptionInvPie.getMessage(), "A peca nao esta no tabuleiro");

        Exception exceptionIncTurn = assertThrows(IncorrectTurnOrderException.class, () -> gameTestExc
                .makeMove(gameTestExc.getRedPlayer().getCards()[0], new Position(-1, 0), new Position(4, 2)));
        assertEquals(exceptionIncTurn.getMessage(),
                "Nao esta na vez do jogador " + gameTestExc.getPiece(new Position(4, 2)).getColor());

        Exception exceptionInvMovCard = assertThrows(InvalidCardException.class, () -> gameTestExc
                .makeMove(gameTestExc.getBluePlayer().getCards()[0], new Position(+2, 0), new Position(0, 2)));
        assertEquals(exceptionInvMovCard.getMessage(),
                "O movimento nao condiz com a carta");

        Position[] invalidPositions = { new Position(+4, 0) };
        Card invalidCard = new Card("Carta TOP para roubar no jogo", Color.BLUE, invalidPositions);

        Exception exceptionInvCard = assertThrows(InvalidCardException.class,
                () -> gameTestExc.makeMove(invalidCard, new Position(+4, 0), new Position(0, 2)));
        assertEquals(exceptionInvCard.getMessage(),
                "Carta nao esta na mao do jogador");

        Exception exceptionIlMovSameColor = assertThrows(IllegalMovementException.class, () -> gameTestExc
                .makeMove(gameTestExc.getBluePlayer().getCards()[0], new Position(0, -1), new Position(0, 1)));
        assertEquals(exceptionIlMovSameColor.getMessage(), "O espaco ja esta ocupado por uma peca da mesma cor");

        gameTestMove = new GameImpl("Red Player", "Blue Player", testCards);

        Card oldCard = gameTestMove.getBluePlayer().getCards()[0];
        Card newCard = gameTestMove.getTableCard();
        Position oldPosition = new Position(0, 2);
        Position cardMove = new Position(+1, 0);
        Position newPosition = new Position(oldPosition.getRow() + cardMove.getRow(),
                oldPosition.getCol() + cardMove.getCol());
        Piece oldPositionPiece = gameTestMove.getPiece(oldPosition);

        gameTestMove.makeMove(gameTestMove.getBluePlayer().getCards()[0], cardMove, oldPosition);

        assertEquals(gameTestMove.getBluePlayer().getCards()[0], newCard);
        assertEquals(gameTestMove.getTableCard(), oldCard);
        assertEquals(oldPositionPiece, gameTestMove.getPiece(newPosition));
        assertNull(gameTestMove.getPiece(oldPosition));
    }

    @Test
    public void testCheckVictory1() {

        // teste métodos de Card
        Position[] testPositions = { new Position(0, -1), new Position(0, +1), new Position(-1, 0),
                new Position(+1, 0), new Position(+4, 0), new Position(-4, 0), new Position(-3, 0),
                new Position(+3, 0) };
        Card testCard = new Card("Test Card Name", Color.RED, testPositions);
        // vetor de Card para os proxs testes
        Card[] testCards = { testCard, testCard, testCard, testCard, testCard };

        // testes do checkVictory de Game
        // Mestre Blue no templo Red
        gameTestVic = new GameImpl("Red Player", "Blue Player", testCards);
        gameTestVic.makeMove(gameTestVic.getRedPlayer().getCards()[0], new Position(-1, 0), new Position(4, 2));
        gameTestVic.makeMove(gameTestVic.getBluePlayer().getCards()[0], new Position(+4, 0), new Position(0, 2));
        assertTrue(gameTestVic.checkVictory(Color.BLUE));
        assertFalse(gameTestVic.checkVictory(Color.RED));

        // gameTestVic.printBoard();
    }

    @Test
    public void testCheckVictory2() {

        // teste métodos de Card
        Position[] testPositions = { new Position(0, -1), new Position(0, +1), new Position(-1, 0),
                new Position(+1, 0), new Position(+4, 0), new Position(-4, 0), new Position(-3, 0),
                new Position(+3, 0) };
        Card testCard = new Card("Test Card Name", Color.RED, testPositions);
        // vetor de Card para os proxs testes
        Card[] testCards = { testCard, testCard, testCard, testCard, testCard };

        // Mestre Red no templo Blue
        gameTestVic = new GameImpl("Red Player", "Blue Player", testCards);
        gameTestVic.makeMove(gameTestVic.getRedPlayer().getCards()[0], new Position(-1, 0), new Position(4, 2));
        gameTestVic.makeMove(gameTestVic.getBluePlayer().getCards()[0], new Position(+1, 0), new Position(0, 2));
        gameTestVic.makeMove(gameTestVic.getRedPlayer().getCards()[0], new Position(-3, 0), new Position(3, 2));
        assertTrue(gameTestVic.checkVictory(Color.RED));
        assertFalse(gameTestVic.checkVictory(Color.BLUE));

        // gameTestVic.printBoard();
    }

    @Test
    public void testCheckVictory3() {

        // teste métodos de Card
        Position[] testPositions = { new Position(0, -1), new Position(0, +1), new Position(-1, 0),
                new Position(+1, 0), new Position(+4, 0), new Position(-4, 0), new Position(-3, 0),
                new Position(+3, 0) };
        Card testCard = new Card("Test Card Name", Color.RED, testPositions);
        // vetor de Card para os proxs testes
        Card[] testCards = { testCard, testCard, testCard, testCard, testCard };

        // Mestre Blue capturado pelo Mestre Red
        gameTestVic = new GameImpl("Red Player", "Blue Player", testCards);
        gameTestVic.makeMove(gameTestVic.getRedPlayer().getCards()[0], new Position(-1, 0), new Position(4, 2));
        gameTestVic.makeMove(gameTestVic.getBluePlayer().getCards()[0], new Position(+3, 0), new Position(0, 2));
        assertFalse(gameTestVic.checkVictory(Color.RED));
        assertTrue(gameTestVic.checkVictory(Color.BLUE));

        // gameTestVic.printBoard();
    }

}
