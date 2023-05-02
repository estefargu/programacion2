package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.KidDTO;
import co.edu.umanizales.tadsmain.controller.dto.PetDTO;
import co.edu.umanizales.tadsmain.controller.dto.ReportKidsByAgeRangeDTO;
import co.edu.umanizales.tadsmain.controller.dto.ReportPetsByAgeRangeDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    public void addToStart(Pet pet){
        if(head !=null)
        {
            NodeDE newNodeDE = new NodeDE(pet);
            newNodeDE.setNext(head);
            head.setPrevious(newNodeDE);
            head = newNodeDE;
        }
        else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void addInPosition(Pet pet, int position) {
        if (head != null) {
            if (position == 1) {
                addToStart(pet);
            } else {
                NodeDE temp = head;
                int count = 1;
                while (count < position - 1 && temp.getNext() != null) {
                    temp = temp.getNext();
                    count++;
                }
                NodeDE newNodeDE = new NodeDE(pet);
                newNodeDE.setNext(temp.getNext());
                newNodeDE.setPrevious(temp);
                temp.setNext(newNodeDE);
            }
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void addToEnd(Pet pet){
        if(head != null)
        {
            NodeDE temp = head;
            while(temp.getNext() !=null)
            {
                temp = temp.getNext();
            }
            /// Parado en el último
            NodeDE newNodeDE = new NodeDE(pet);
            temp.setNext(newNodeDE);
            newNodeDE.setPrevious(temp);
        }
        else {
            head = new NodeDE(pet);
        }
        size ++;
    }

    public void deleteByIdentification(String identification) {
        if (head != null) {
            if (head.getData().getId().equals(identification)) {
                head = head.getNext();
                if (head != null) {
                    head.setPrevious(null);
                }
                size--;
            } else {
                NodeDE temp = head.getNext();
                while (temp != null && !temp.getData().getId().equals(identification)) {
                    temp = temp.getNext();
                }
                if (temp != null) {
                    temp.getPrevious().setNext(temp.getNext());
                    if (temp.getNext() != null) {
                        temp.getNext().setPrevious(temp.getPrevious());
                    }
                    size--;
                }
            }
        }
    }


    public void invert() {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderMaleToStart() {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.addToEnd(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void getMixMaleAndFemale() {
        if (head != null) {
            ListDE listCopy = new ListDE();
            int countFemale = 0;
            int countMale = 0;
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCopy.addInPosition(temp.getData(), countMale + 1);
                    countMale = countMale + 2;
                }
                if (temp.getData().getGender() == 'F') {
                    listCopy.addInPosition(temp.getData(), countFemale + 2);
                    countFemale = countFemale + 2;
                }

                temp = temp.getNext();
            }
            head = listCopy.getHead();
        }
    }

    public void deleteByAge(byte age) {
        if (head != null && head.getData().getAge() == age) {
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
            size--;
        }

        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getAge() == age) {
                if (temp.getPrevious() != null) {
                    temp.getPrevious().setNext(temp.getNext());
                }
                if (temp.getNext() != null) {
                    temp.getNext().setPrevious(temp.getPrevious());
                }
                size--;
            }
            temp = temp.getNext();
        }
    }

    public float getAverageAgeByPet() {

        if (head != null) {
            int count = 0;
            byte age = 0;
            NodeDE temp = this.head;

            while (temp.getNext() != null) {
                count = count + 1;
                age += temp.getData().getAge();
                temp = temp.getNext();
            }

            return (float) age / count;
        }
        return (float) 0;
    }

    public int getCountPetsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void getLosePosition(String id, int position) {
        if (head == null || position <= 0) {
            return;
        }
        NodeDE temp = head;
        int count = 1;
        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                Pet petCopy = temp.getData();
                int newPosition = count + position;
                if (newPosition > size) {
                    // Si la nueva posición no es válida, no hacemos nada
                    return;
                } else {
                    // Eliminar el nodo de la posición actual
                    if (temp.getPrevious() == null) {
                        head = temp.getNext();
                    } else {
                        temp.getPrevious().setNext(temp.getNext());
                    }
                    if (temp.getNext() != null) {
                        temp.getNext().setPrevious(temp.getPrevious());
                    }
                    // Insertar el nodo en la nueva posición
                    addInPosition(petCopy, newPosition);
                    break;
                }
            }
            temp = temp.getNext();
            count++;
        }
    }

    public void getGainPosition(String id, int position) {
        if (head == null || position <= 0) {
            return;
        }
        NodeDE temp = head;
        NodeDE prev = null;
        int count = 1;
        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                Pet petCopy = temp.getData();
                int newPosition = count - position;
                if (newPosition < 1) {
                    // Si la nueva posición no es válida, no hacemos nada
                    return;
                } else if (prev == null) {
                    // Si el nodo a mover es el primer nodo de la lista
                    head = head.getNext();
                    head.setPrevious(null);
                } else {
                    // Si el nodo a mover no es el primer nodo de la lista
                    prev.setNext(temp.getNext());
                    if (temp.getNext() != null) {
                        temp.getNext().setPrevious(prev);
                    }
                }
                addInPosition(petCopy, newPosition);
                break;
            }
            prev = temp;
            temp = temp.getNext();
            count++;
        }
    }
    public int getPositionById(String id) {
        int position = 1;
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                return position;
            }
            temp = temp.getNext();
            position++;
        }
        return 0;
    }

    public void getOrderToEndPetByLetter(String letter) {
        if (head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listCp.addToEnd(temp.getData());
                } else {
                    listCp.addToStart(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public ReportPetsByAgeRangeDTO getGenerateAgeRangeReport(byte minAge, byte maxAge) {
        int numPetsByRange = 0;
        List<Pet> petsByRange = new ArrayList<>();

        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                Pet petCopy = temp.getData();
                int age = petCopy.getAge();
                if (age >= minAge && age <= maxAge) {
                    numPetsByRange++;
                    petsByRange.add(petCopy);
                }
                temp = temp.getNext();
            }
        }

        return new ReportPetsByAgeRangeDTO(numPetsByRange, minAge, maxAge, petsByRange);
    }

    public boolean getConfirmKidById(String id) {
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getId().equals(id))
                    return false; //si retorna false es que la identificacion ya exite
                temp = temp.getNext();
            }
        }
        return true; // si retorna true es que la identificacion no existe, osea se puede agregar
    }
}
