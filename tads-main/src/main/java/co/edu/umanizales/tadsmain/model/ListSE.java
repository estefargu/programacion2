package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.ListKidsByLocationGenderDTO;
import co.edu.umanizales.tadsmain.controller.dto.ReportKidsByAgeRangeDTO;
import co.edu.umanizales.tadsmain.exception.ListSEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListSE {
    private Node head;
    private int size;

    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño con esa identificacion");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño con esa identificacion");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid) throws ListSEException{
        if (head != null) {
            if(!getConfirmKidById(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño con esa identificacion");
            }
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void addInPosition(Kid kid, int position) throws ListSEException {
        if(position-1>size){
            throw new ListSEException("Posicion invalida");
        }
        if (head != null) {
            if (head.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño con esa identificación");
            }
            Node temp = head;
            int count = 1;
            while (count < position - 1 && temp.getNext() != null) {
                if (temp.getNext().getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("Ya existe un niño con esa identificación");
                }
                temp = temp.getNext();
                count++;
            }
            Node newNode = new Node(kid);
            if (position == 1) {
               addToStart(kid);
            } else {
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
        } else {
            head = new Node(kid);
        }
        size++;
    }

     /*Agregar en posiscion(Niño y la posicion) -> Entrada por parametro
    Hay datos
    Si
        llamo a un ayudante y le digo que se posicione en la cabeza
            mientras en el brazo exista algo
                pasese al siguiente, esto para recorrer lista de ninos
        Preguntar en que posicion quiere anadir al niño
        meto al niño en un costal (nuevo costal)
        Resto la posicion deseada menos 1 para asi agregar al niño en la posicion que quiero
        le digo al anterior de la posicion que coja a nuevo costal
        Obtengo un niño agregado en la posicion deseada
    No
        Crear nuevo costal que tiene de entrada un niño y le asigno la cabeza
     */

    public void deleteByIdentification(String identification) throws ListSEException {
        if (head != null) {
            if (head.getData().getIdentification().equals(identification)) {
                head = head.getNext();
                size--;
            } else {
                Node temp = head;
                while (temp.getNext() != null && !temp.getNext().getData().getIdentification().equals(identification)) {
                    temp = temp.getNext();
                }
                if (temp.getNext() != null) {
                    temp.setNext(temp.getNext().getNext());
                    size--;
                } else {
                    throw new ListSEException("No se encontró un niño con la identificación " + identification);
                }
            }
        } else {
            throw new ListSEException("La lista está vacía");
        }
    }


    /*Eliminar niño (identificacion niño) -> Entra por parametro
      Cabeza es igual al primer costal o primer nodo
      Llamo a un ayudante y le digo que se posicione en la cabeza
      Pregunto si el ayudante tiene alguien adelante y sea igual a la identificacion pedida
      Si
        Paro y elimino al niño
      No
        Sigo hasta encontarar la posicion a eliminar*/
    public void invert() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        } else {
            throw new ListSEException("La lista está vacía");
        }
    }


    public void orderBoysToStart() throws ListSEException {
            if (this.head != null) {
                ListSE listCp = new ListSE();
                Node temp = this.head;
                while (temp != null) {
                    if (temp.getData().getGender().equals("M")) {
                        listCp.addToStart(temp.getData());
                    } else {
                        listCp.add(temp.getData());
                    }

                    temp = temp.getNext();
                }
                this.head = listCp.getHead();
            }else {
                throw new ListSEException("La lista esta vacia");
            }

    }

    public void changeExtremes() throws ListSEException {
        if (this.head == null || this.head.getNext() == null) {
            throw new ListSEException("No hay suficientes ninos para intercambiar los extremos");
        } else {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // temp está en el último nodo
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
    }

    public int getCountKidsByLocationCode(String code){
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;

    }

    public void getListKidsByLocationGendersByAge(byte age, ListKidsByLocationGenderDTO report) {
        if (head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > age) {
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    public float getAverageAgeByKid() {

        if (head != null) {
            int count = 0;
            byte age = 0;
            Node temp = this.head;

            while (temp.getNext() != null) {
                count = count + 1;
                age += temp.getData().getAge();
                temp = temp.getNext();
            }

            return (float) age / count;
        }
        return (float) 0;
    }

    public boolean getConfirmKidById(String identification) {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                if (temp.getData().getIdentification().equals(identification))
                    return false; //si retorna false es que la identificacion ya exite
                temp = temp.getNext();
            }
        }
        return true; // si retorna true es que la identificacion no existe, osea se puede agregar
    }


    public void deleteByAge(byte age)throws ListSEException {
        boolean exist = false;
        if (head == null) {
            throw new ListSEException("La lista de ninos está vacía");
        }
        while(head != null && head.getData().getAge() == age) {
            head = head.getNext();
            size--;
            exist=true;
        }

        Node temp = head;
        while (temp != null && temp.getNext() != null) {
            if (temp.getNext().getData().getAge() == age) {
                temp.setNext(temp.getNext().getNext());
                size--;
                exist=true;
            } else {
                temp = temp.getNext();
            }
        }
        if(!exist){
            throw new ListSEException("No hay ninos con la edad ingresada");
        }
    }


    public void getMixBoyAndGirl()throws ListSEException {
        if (head != null) {
            ListSE listCopy = new ListSE();
            int countGirl = 0;
            int countBoy = 0;
            Node temp = head;
            while (temp != null) {
                if (temp.getData().getGender().equals("M")) {
                    listCopy.addInPosition(temp.getData(), countBoy + 1);
                    countBoy = countBoy + 2;
                }
                if (temp.getData().getGender().equals("F")) {
                    listCopy.addInPosition(temp.getData(), countGirl + 2);
                    countGirl = countGirl + 2;
                }

                temp = temp.getNext();
            }
            head = listCopy.getHead();
        }else {
            throw new ListSEException("La lista está vacía.");
        }
    }

    public void getLosePosition(String identification, int position) throws ListSEException {
        if (head == null || position <= 0) {
            throw new ListSEException("La lista está vacía");
        }

        Node temp = head;
        Node prev = null;
        int count = 1;

        while (temp != null) {
            if (temp.getData().getIdentification().equals(identification)) {
                Kid kidCopy = temp.getData();
                int newPosition = count + position;

                if (newPosition > size) {
                    throw new ListSEException("El niño con identificación " + identification + " no puede perder "
                            + position + " posiciones");
                } else if (prev == null) {
                    head = head.getNext();
                    addInPosition(kidCopy, newPosition);
                } else {
                    // Si el nodo a mover no es el primer nodo de la lista
                    prev.setNext(temp.getNext());
                    addInPosition(kidCopy, newPosition);
                }

                return;
            }

            prev = temp;
            temp = temp.getNext();
            count++;
        }

        throw new ListSEException("No se encontró la identificación del niño");
    }



   public void getGainPosition(String identification, int position)throws ListSEException {
        if (head == null || position <= 0) {
            throw new ListSEException("La lista esta vacia");
        }
        Node temp = head;
        Node prev = null;
        int count = 1;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(identification)) {
                Kid kidCopy = temp.getData();
                if (count - position < 1) {
                    throw new ListSEException("El nino con identificación " + identification + " no puede ganar "
                            + position + " posiciones");
                } else if (prev == null) {
                    head = head.getNext();
                    addInPosition(kidCopy, count - position);
                } else {
                    prev.setNext(temp.getNext());
                    addInPosition(kidCopy, count - position);
                }
                return;
            }
            prev = temp;
            temp = temp.getNext();
            count++;
        }
       throw new ListSEException("No se encontró la identificación del nino");
    }

    public void getOrderToEndKidByLetter(String letter) throws ListSEException {
        if (head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            int count = 0; // Contador de niños que inician con la letra
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listCp.add(temp.getData());
                    count++;
                } else {
                    listCp.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
            if (count == 0) {
                throw new ListSEException("No se encontraron niños que su nombre inicie con la letra " + letter);
            }
            this.head = listCp.getHead();
        } else {
            throw new ListSEException("La lista esta vacia");
        }
    }


    public ReportKidsByAgeRangeDTO getGenerateAgeRangeReport(byte minAge, byte maxAge)throws ListSEException {
        int numKidsByRange = 0;
        List<Kid> kidsByRange = new ArrayList<>();

        if (head != null) {
            Node temp = head;
            while (temp != null) {
                Kid kidCopy = temp.getData();
                int age = kidCopy.getAge();
                if (age >= minAge && age <= maxAge) {
                    numKidsByRange++;
                    kidsByRange.add(kidCopy);
                }
                temp = temp.getNext();
            }
        }else{
            throw new ListSEException("La lista esta vacia");
        }
        ReportKidsByAgeRangeDTO report = new ReportKidsByAgeRangeDTO(numKidsByRange, minAge, maxAge, kidsByRange);
        return report;
    }

}