/**
 * Реализация класса "Аудитория" со свойствами: "Номер" и "Вместимость".
 */
public class Auditorium {

    private int auditoriumNumber;
    private int capacity;
    private Auditorium next;

    public Auditorium(int number, int capacity) {
        this.capacity = capacity;
        this.auditoriumNumber = number;
        this.next = null;
    }

    public Auditorium() {
        this(-1,-1);
        this.next = null;
    }

    public int getAuditoriumNumber() {
        return auditoriumNumber;
    }

    public void setAuditoriumNumber(int auditoriumNumber) {
        this.auditoriumNumber = auditoriumNumber;
    }

    public void setAuditoriumCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAuditoriumCapacity() {
        return capacity;
    }

    public Auditorium getNext() {
        return next;
    }

    public void setNext(Auditorium next) {
        this.next = next;
    }

    public void destruct() {
        this.auditoriumNumber = -1;
        this.capacity = -1;
        this.next = null;
    }

    @Override
    public String toString() {
        return " Аудитория = " + auditoriumNumber +
                ", вместимость = " + capacity + ".";
    }
}