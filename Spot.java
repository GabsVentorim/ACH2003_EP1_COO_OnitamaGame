/**
 * Classe contendo ações e informações sobre cada espaço (quadrado) no tabuleiro
 */
public class Spot {

    private Piece piece;
    private final Position pos;
    private final Color color;

    /**
     * Construtor para espaços com peça e com cor
     * 
     * @param piece Peça que inicia nesse espaço do tabuleiro
     * @param pos   Posição do espaço no tabuleiro
     * @param color Cor do espaço no tabuleiro (Templo)
     */
    public Spot(Piece piece, Position pos, Color color) {
        this.piece = piece;
        this.pos = pos;
        this.color = color;
    }

    /**
     * Construtor para espaços com peça e sem cor
     * 
     * @param piece Peça que inicia nesse espaço do tabuleiro
     * @param pos   Posição do espaço no tabuleiro
     */
    public Spot(Piece piece, Position pos) {
        this.piece = piece;
        this.pos = pos;
        this.color = Color.NONE;
    }

    /**
     * Construtor para espaços sem peça e sem cor
     * 
     * @param pos Posição do espaço no tabuleiro
     */
    public Spot(Position pos) {
        this.pos = pos;
        this.piece = null;
        this.color = Color.NONE;
    }

    /**
     * Método que devolve a posição (coordenadas) do espaço
     * 
     * @return Objeto Position contendo a posição (coordenadas) do espaço
     */
    public Position getPosition() {
        return pos;
    }

    /**
     * Método que devolve a peça contida neste espaço
     * 
     * @return Objeto Piece caso tenha uma peça ou null caso o espaço esteja vazio
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Método que devolve a cor do espaço
     * 
     * @return Enum Color com a cor do espaço. Caso o espaço não tenha cor, o valor
     *         do enum será NONE
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método que ocupa o espaço atual com a peça passada
     * 
     * @param piece A peça para ocupar este espaço
     * @exception IllegalMovementException Caso o espaço já esteja ocupado por uma
     *                                     peça da mesma cor
     */
    protected void occupySpot(Piece piece) throws IllegalMovementException {

        if (getPiece() != null)
            if (piece.getColor() == getPiece().getColor())
                throw new IllegalMovementException("O espaco ja esta ocupado por uma peca da mesma cor");

        this.piece = piece;
    }

    /**
     * Método que "libera" o espaço atual, ou seja, deixa-o vazio
     */
    protected void releaseSpot() {
        this.piece = null;
    }
}
