/**
 * Реализация класса "Здание" со свойствами: "Номер".
 */
public class Building {

    private Building next;
    private int buildingNumber;
    private Auditorium head;
    private Auditorium tail;

    public Building(int buildingNumber) {
        this.buildingNumber = buildingNumber;
        this.head = null;
        this.tail = null;
    }

    public Building() {
        this.buildingNumber = -1;
        this.head = null;
        this.tail = null;
    }

    public void setHead(Auditorium head) {
        this.head = head;
    }

    public Auditorium getHead() {
        return head;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setTail(Auditorium tail) {
        this.tail = tail;
    }

    public Auditorium getTail() {
        return tail;
    }

    public Building getNext() {
        return next;
    }

    public void setNext(Building next) {
        this.next = next;
    }

    public void add(int auditoriumNumber, int capacity) {
        Auditorium newAuditorium = new Auditorium(auditoriumNumber, capacity);

        if (head == null) {
            head = newAuditorium;
            tail = newAuditorium;
            return;
        }

        Auditorium current = head;
        Auditorium prev = null;

        while (current != null && (current.getAuditoriumNumber() < auditoriumNumber ||
                (current.getAuditoriumNumber() == auditoriumNumber && current.getAuditoriumCapacity() <= capacity))) {
            prev = current;
            current = current.getNext();
        }

        if (prev == null) {
            newAuditorium.setNext(head);
            head = newAuditorium;
        }

        else {
            prev.setNext(newAuditorium);
            newAuditorium.setNext(current);
            if (current == null) {
                tail = newAuditorium;
            }
        }
    }

    @Override
    public String toString() {
        return "№" + buildingNumber;
    }

    public Auditorium find(int auditoriumNumber, int capacity) {
        Auditorium current = head;
        while (current != null) {
            if (current.getAuditoriumNumber() == auditoriumNumber && current.getAuditoriumCapacity() == capacity) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    public void delete(int auditoriumNumber, int capacity) {
        Auditorium current = head;
        Auditorium prev = null;

        while (current != null && (current.getAuditoriumNumber() != auditoriumNumber || current.getAuditoriumCapacity() != capacity)) {
            prev = current;
            current = current.getNext();
        }

        if (prev == null) {
            head = current.getNext();
            if (head == null) {
                tail = null;
            }
        } else {
            prev.setNext(current.getNext());
            if (current.getNext() == null) {
                tail = prev;
            }
        }
        current.destruct();
    }

    public void destruct() {
        while (head != null) {
            Auditorium temp = head;
            head = head.getNext();
            temp.destruct();
        }
        tail = null;
        buildingNumber = -1;
    }
}