import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe que contém informações das cartas
 */
public class Card {

    private final String name;
    private final Color color;
    private Position[] positions;

    /**
     * Construtor que define os principais atributos de uma cara
     * 
     * @param name      Nome da carta
     * @param color     Cor da carta
     * @param positions Todas as posições relativas de movimento
     */
    public Card(String name, Color color, Position[] positions) {
        this.name = name;
        this.color = color;
        this.positions = positions;
    }

    /**
     * Método que devolve o nome da carta
     * 
     * @return String que contém o nome da carta
     */
    public String getName() {
        return name;
    }

    /**
     * Método que devolve a cor da carta
     * 
     * @return Enum Color que contém a cor da carta
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método que devolve todas as possíveis posições relativas de movimento.
     * A posição atual da peça é o ponto de origem (0,0). Uma carta possui as
     * possíveis posições de movimento em relação ao ponto de origem.
     * 
     * @return Um array de Position contendo todas as possíveis posições de
     *         movimento em relação ao ponto de origem
     */
    public Position[] getPositions() {
        return positions;
    }

    /**
     * Método que cria todas as cartas do jogo, embaralha-as e devolve as 5 que
     * serão utilizadas na partida.
     * 
     * @return Vetor de cartas com todas as cartas do jogo
     */
    public static Card[] createCards() {

        List<Card> cards = new ArrayList<>();

        Position[] tigerPositions = { new Position(-2, 0), new Position(+1, 0) };
        Position[] dragonPositions = { new Position(-1, -2), new Position(-1, +2), new Position(+1, -1),
                new Position(+1, +1) };
        Position[] frogPositions = { new Position(-1, -1), new Position(0, -2), new Position(+1, +1) };
        Position[] rabbitPositions = { new Position(+1, -1), new Position(-1, +1), new Position(0, +2) };
        Position[] crabPositions = { new Position(0, -2), new Position(0, +2), new Position(-1, 0) };
        Position[] elephantPositions = { new Position(0, -1), new Position(0, +1), new Position(-1, -1),
                new Position(-1, +1) };
        Position[] goosePositions = { new Position(-1, -1), new Position(+1, +1), new Position(0, -1),
                new Position(0, +1) };
        Position[] roosterPositions = { new Position(+1, -1), new Position(0, -1), new Position(0, +1),
                new Position(-1, +1) };

        Card tigerCard = new Card("Tiger", Color.BLUE, tigerPositions);
        Card dragonCard = new Card("Dragon", Color.RED, dragonPositions);
        Card frogCard = new Card("Frog", Color.RED, frogPositions);
        Card rabbitCard = new Card("Rabbit", Color.BLUE, rabbitPositions);
        Card crabCard = new Card("Crab", Color.BLUE, crabPositions);
        Card elephantCard = new Card("Elephant", Color.RED, elephantPositions);
        Card gooseCard = new Card("Goose", Color.BLUE, goosePositions);
        Card roosterCard = new Card("Rooster", Color.RED, roosterPositions);

        Collections.addAll(cards, tigerCard, dragonCard, frogCard, rabbitCard, crabCard, elephantCard, gooseCard,
                roosterCard);
        Collections.shuffle(cards);

        return cards.subList(0, 6).toArray(new Card[0]);
    }
}
