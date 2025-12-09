package EPOS;

/**
 *
 * @author Ryan
 */
public class SpeciesLine {
    
    int Number;
    String Name;

    public SpeciesLine() {
    }

    public SpeciesLine(int Number, String Name) {
        this.Number = Number;
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }
    
    
}
