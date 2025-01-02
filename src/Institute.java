/**
 * Реализация класса "Институт" со свойствами: "Наименование".
 */
import java.util.Objects;

public class Institute {

    private String instituteName;
    private Building head;
    private Institute next;

    public Institute(String instituteName) {
        this.instituteName = instituteName;
        this.head = null;
        this.next = null;
    }

    public Institute() {
        this.instituteName = "";
        this.head = null;
        this.next = null;
    }

    public void setNext(Institute next) {
        this.next = next;
    }

    public Institute getNext() {
        return next;
    }

    public void setHead(Building head) {
        this.head = head;
    }

    public Building getHead() {
        return head;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public void add(int buildingNumber) {
        Building newBuilding = new Building(buildingNumber);
        if (head == null) {
            head = newBuilding;
        } else {
            Building current = head;
            Building prev = null;

            while (current != null && current.getBuildingNumber() < buildingNumber) {
                prev = current;
                current = current.getNext();
            }

            if (prev == null) {
                newBuilding.setNext(head);
                head = newBuilding;
            } else {
                prev.setNext(newBuilding);
                newBuilding.setNext(current);
            }
        }
    }

    public Building find(int buildingNumber) {
        Building current = head;
        while (current != null) {
            if (current.getBuildingNumber() == buildingNumber & Objects.equals(current.getBuildingNumber(), buildingNumber)) {
                break;
            }
            current = current.getNext();
        }
        return current;
    }

    public void delete(int buildingNumber) {
        Building current = head;
        Building prev = null;

        while (current != null) {
            if (current.getBuildingNumber() == buildingNumber) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                current.destruct();
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }

    public void destruct() {
        Building currentBuilding = head;
        while (currentBuilding != null) {
            currentBuilding.destruct();
            currentBuilding = currentBuilding.getNext();
        }
        this.instituteName = "";
        this.head = null;
        this.next = null;
    }
}
