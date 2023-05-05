package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.ReportPetsByAgeRangeDTO;
import co.edu.umanizales.tadsmain.exception.ListSEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    public void addToStart(Pet pet)throws ListSEException {
        if(head !=null)
        {
            if(head.getData().getId().equals(pet.getId())) {
                throw new ListSEException("Ya existe una mascota con esa identificacion");
            }
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

    public void addInPosition(Pet pet, int position) throws ListSEException {
        if (position <= 0 || position > size + 1) {
            throw new ListSEException("Posición inválida");
        }
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getId().equals(pet.getId())) {
                    throw new ListSEException("Ya existe una mascota con esa identificación");
                }
                temp = temp.getNext();
            }
            if (position == 1) {
                addToStart(pet);
            } else {
                temp = head;
                int count = 1;
                while (count < position - 1 && temp.getNext() != null) {
                    temp = temp.getNext();
                    count++;
                }
                NodeDE newNodeDE = new NodeDE(pet);
                newNodeDE.setNext(temp.getNext());
                newNodeDE.setPrevious(temp);
                if (temp.getNext() != null) {
                    temp.getNext().setPrevious(newNodeDE);
                }
                temp.setNext(newNodeDE);
            }
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void addToEnd(Pet pet) throws ListSEException {
        if (head != null) {
            NodeDE temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getId().equals(pet.getId())) {
                    throw new ListSEException("Ya existe una mascota  con esa identificación");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getId().equals(pet.getId())) {
                throw new ListSEException("Ya existe una mascota con esa identificación");
            }
            // temp es el último nodo de la lista
            NodeDE newNodeDE = new NodeDE(pet);
            temp.setNext(newNodeDE);
            newNodeDE.setPrevious(temp);
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void deleteByIdentification(String identification) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        if (head.getData().getId().equals(identification)) {
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
            size--;
            return;
        }
        NodeDE temp = head.getNext();
        while (temp != null && !temp.getData().getId().equals(identification)) {
            temp = temp.getNext();
        }
        if (temp == null) {
            throw new ListSEException("No se encontró una mascota con la identificación " + identification);
        }
        temp.getPrevious().setNext(temp.getNext());
        if (temp.getNext() != null) {
            temp.getNext().setPrevious(temp.getPrevious());
        }
        size--;
    }

    public void invert()throws ListSEException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }else {
            throw new ListSEException("La lista está vacía");
        }
    }

    public void orderMaleToStart()throws ListSEException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender().equals("M")) {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.addToEnd(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }else {
            throw new ListSEException("La lista esta vacia");
        }
    }

    public void getMixMaleAndFemale() throws ListSEException{
        if (head != null) {
            ListDE listCopy = new ListDE();
            int countFemale = 0;
            int countMale = 0;
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getGender().equals("M")) {
                    listCopy.addInPosition(temp.getData(), countMale + 1);
                    countMale = countMale + 2;
                }
                if (temp.getData().getGender().equals("F")) {
                    listCopy.addInPosition(temp.getData(), countFemale + 2);
                    countFemale = countFemale + 2;
                }

                temp = temp.getNext();
            }
            head = listCopy.getHead();
        }else {
            throw new ListSEException("La lista esta vacia");
        }
    }

    public void deleteByAge(byte age) throws ListSEException{
        boolean exist = false;
        if (head == null) {
            throw new ListSEException("La lista de mascotas está vacía");
        }
        while(head != null && head.getData().getAge() == age) {
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
            size--;
            exist=true;
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
                exist=true;
            }
            temp = temp.getNext();
        }if(!exist){
            throw new ListSEException("No hay mascotas con la edad ingresada");
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

    public void getLosePosition(String id, int position) throws ListSEException {
        if (head == null || position <= 0) {
            throw new ListSEException("La lista está vacía");
        }
        NodeDE temp = head;
        int count = 1;

        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                Pet petCopy = temp.getData();
                int newPosition = count + position;

                if (newPosition > size) {
                    throw new ListSEException("El niño con identificación " + id + " no puede perder "
                            + position + " posiciones");
                } else if (temp.getPrevious() == null) {
                    // Si el nodo a mover es el primer nodo de la lista
                    head = head.getNext();
                    addInPosition(petCopy, newPosition);
                } else {
                    // Si el nodo a mover no es el primer nodo de la lista
                    temp.getPrevious().setNext(temp.getNext());
                    if (temp.getNext() != null) {
                        temp.getNext().setPrevious(temp.getPrevious());
                    }
                    addInPosition(petCopy, newPosition);
                }
                return;
            }
            temp = temp.getNext();
            count++;
        }
        throw new ListSEException("No se encontró la identificación del niño");
    }

    public void getGainPosition(String id, int position) throws ListSEException {
        if (head == null || position <= 0) {
            throw new ListSEException("La lista está vacía");
        }
        NodeDE temp = head;
        NodeDE prev = null;
        int count = 1;

        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                Pet petCopy = temp.getData();
                int newPosition = count - position;

                if (newPosition < 1) {
                    throw new ListSEException("La mascota con identificación " + id + " no puede ganar "
                            + position + " posiciones");
                }
                if (prev == null) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrevious(null);
                    }
                } else {
                    prev.setNext(temp.getNext());
                    if (temp.getNext() != null) {
                        temp.getNext().setPrevious(prev);
                    }
                }
                addInPosition(petCopy, newPosition);
                return;
            }
            prev = temp;
            temp = temp.getNext();
            count++;
        }
        throw new ListSEException("No se encontró la identificación de la mascota");
    }

    public void getOrderToEndPetByLetter(String letter)throws ListSEException {
        if (head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            int count = 0;
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listCp.addToEnd(temp.getData());
                    count++;
                } else {
                    listCp.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
            if (count == 0) {
                throw new ListSEException("No se encontraron mascotas que su nombre inicie con la letra " + letter);
            }
            this.head = listCp.getHead();
        }else {
            throw new ListSEException("La lista esta vacia");
        }
    }

    public ReportPetsByAgeRangeDTO getGenerateAgeRangeReport(byte minAge, byte maxAge) throws ListSEException{
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
        }else {
            throw new ListSEException("La lista esta vacia");
        }
        return new ReportPetsByAgeRangeDTO(numPetsByRange, minAge, maxAge, petsByRange);
    }

}
