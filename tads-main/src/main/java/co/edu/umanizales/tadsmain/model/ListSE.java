package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.KidDTO;
import co.edu.umanizales.tadsmain.controller.dto.ListKidsByLocationGenderDTO;
import co.edu.umanizales.tadsmain.controller.dto.ReportKidsByAgeRangeDTO;
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
    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
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
    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void addInPosition(Kid kid, int position) {
        if (head != null) {
            if (position == 1) {
                addToStart(kid);
            } else {
                Node temp = head;
                int count = 1;
                while (count < position - 1 && temp.getNext() != null) {
                    temp = temp.getNext();
                    count++;
                }
                Node newNode = new Node(kid);
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

    public void deleteByIdentification(String identification) {
        if (head != null) {
            if (head.getData().getIdentification().equals(identification)) {
                head = head.getNext();
                size--;
            }
            Node temp = head;
            while (temp.getNext() != null && temp.getNext().getData().getIdentification().equals(identification)) {
                temp = temp.getNext();
            }
            if (temp.getNext() != null) {
                temp.setNext(temp.getNext().getNext());
                size--;
            }
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
    public void invert() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }

    public int getCountKidsByLocationCode(String code) {
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

    public void deleteByAge(byte age) {
        if (head != null && head.getData().getAge() == age) {
            head = head.getNext();
            size--;
        }

        Node temp = head;
        while (temp != null && temp.getNext() != null) {
            if (temp.getNext().getData().getAge() == age) {
                temp.setNext(temp.getNext().getNext());
                size--;
            } else {
                temp = temp.getNext();
            }
        }
    }

    public void getMixBoyAndGirl() {
        if (head != null) {
            ListSE listCopy = new ListSE();
            int countGirl = 0;
            int countBoy = 0;
            Node temp = head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCopy.addInPosition(temp.getData(), countBoy + 1);
                    countBoy = countBoy + 2;
                }
                if (temp.getData().getGender() == 'F') {
                    listCopy.addInPosition(temp.getData(), countGirl + 2);
                    countGirl = countGirl + 2;
                }

                temp = temp.getNext();
            }
            head = listCopy.getHead();
        }
    }

    public void getLosePosition(String identification, int position) {
        if (head == null || position <= 0) {
            return;
        }
        Node temp = head;
        Node prev = null;
        int count = 1;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(identification)) {
                Kid kidCopy = temp.getData();
                int newPosition = count + position;
                if (newPosition > size) {
                    // Si la nueva posición no es válida, no hacemos nada
                    return;
                } else if (prev == null) {
                    // Si el nodo a mover es el primer nodo de la lista
                    head = head.getNext();
                    addInPosition(kidCopy, newPosition);
                } else {
                    // Si el nodo a mover no es el primer nodo de la lista
                    prev.setNext(temp.getNext());
                    addInPosition(kidCopy, newPosition);
                }
                break;
            }
            prev = temp;
            temp = temp.getNext();
            count++;
        }
    }


    public void getGainPosition(String identification, int position) {
        if (head == null || position <= 0) {
            return;
        }

        Node temp = head;
        Node prev = null;
        int count = 1;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(identification)) {
                Kid kidCopy = temp.getData();
                if (count - position < 1) {
                    // Si la nueva posición no es válida, no hacemos nada
                    return;
                } else if (prev == null) {
                    // Si el nodo a mover es el primer nodo de la lista
                    head = head.getNext();
                } else {
                    // Si el nodo a mover no es el primer nodo de la lista
                    prev.setNext(temp.getNext());
                }
                addInPosition(kidCopy, count - position);
                break;
            }
            prev = temp;
            temp = temp.getNext();
            count++;
        }
    }

    public int getPositionById(String identification) {
        int position = 1;
        Node temp = head;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(identification)) {
                return position;
            }
            temp = temp.getNext();
            position++;
        }
        return 0;
    }

    public void getOrderToEndKidByLetter(String letter) {
        if (head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listCp.add(temp.getData());
                } else {
                    listCp.addToStart(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public ReportKidsByAgeRangeDTO getGenerateAgeRangeReport(byte minAge, byte maxAge) {
        int numKidsByRange = 0;
        List<KidDTO> kidsByRange = new ArrayList<>();

        if (head != null) {
            Node temp = head;
            while (temp != null) {
                KidDTO kid = temp.getData().kidDTO();
                int age = kid.getAge();
                if (age >= minAge && age <= maxAge) {
                    numKidsByRange++;
                    kidsByRange.add(kid);
                }
                temp = temp.getNext();
            }
        }
        ReportKidsByAgeRangeDTO report = new ReportKidsByAgeRangeDTO(numKidsByRange, minAge, maxAge, kidsByRange);
        return report;
    }

}