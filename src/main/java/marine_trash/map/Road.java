package marine_trash.map;

public class Road {
    private final Pointt connected;
    private final int length;
    private Boolean Pirate;

    public Road(final Pointt connected, int length) {
        this.connected = connected;
        this.length = length;
    }

    public Pointt getPoint() {return connected;}
    public boolean hasPirate() {return Pirate;}
    public int getLength() {return length;}

    public void setPirate(final boolean pirate) {Pirate = pirate;}


}
