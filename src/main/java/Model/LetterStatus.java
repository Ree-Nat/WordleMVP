package Model;

public enum LetterStatus{
    YELLOW,
    GREEN,
    BLACK,
    GREY;

    public String shortName()
    {
        return switch(this){
            case YELLOW -> "Y";
            case GREEN -> "G";
            case BLACK -> "B";
            case GREY -> "Gr";
        };

    }
}
