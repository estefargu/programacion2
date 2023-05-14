package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.exception.ListSEException;
import lombok.Data;

import java.util.Random;

@Data
public class ListDECircular {
    private NodeDE head;
    private int size;

    /*
    Agregar al inicio ( me entra por parametro la mascota)
    Si hay datos
        primero verifico si ya existe esa identificacion
        meto a la mascota a un nuevo costal
        nuevo nodo coje con su siguiente a cabeza
        nuevo nodo con su previuos coje al anterior de cabeza
        el anterior de cabeza coje con su siguiente a nuevo nodo
        cabeza coje con su anterior a nuevo nodo
        nuevo costal seria la nueva cabeza
    No hay datos
        meto a la mascota en un nuevo costal
        nuevo costal es igual a cabeza
        el anterior de cabeza coje a cabeza y el siguiente de cabeza coje a cabeza
     */

    public void addToStart(Pet pet)throws ListSEException{
        NodeDE newNodeDE = new NodeDE(pet);
        if(head!=null){
            if(!getConfirmKidById(pet.getId())) {
                throw new ListSEException("Ya existe una mascota con esa identificacion");
            }
            newNodeDE.setNext(head);
            newNodeDE.setPrevious(head.getPrevious());
            head.getPrevious().setNext(newNodeDE);
            head.setPrevious(newNodeDE);
            head =newNodeDE;
        }else{
            head = new NodeDE(pet);
            head.setNext(head);
            head.setPrevious(head);
        }
        size++;
    }

    /*
  Agregar en posicion(me entra por parametro la mascota y la posicion en la que quiero que sea agregada la mascota)
  si la posicion ingresada es mayor que el tamano de la lista
    que diga que no se puede agregar en esa posicion
  si hay datos
    si la posicion es igual a 1
        verifico si ya existe esa identificacion
        lo agrego al inicio
    si la posicion es igual al tamano de la lista
        verifico si ya existe esa identificacion
        lo agrego al final
    llamo ayudante para que se pare en cabeza
    creo contador en 1
    mientras ayudante sea diferente de cabeza y el contador es menor que la posicion
        verifico si ya existe esa identificacion
        sigo
        incremento contador
    Meto la mascota en nuevo costal
    nuevo costal coje con su siguiente al siuiente de ayudante
    nuevo costal coje con su previous a ayudante
    el siguiente de ayudante con su anterior coje a nuevo nodo
    ayuante con su siguiente coje a nuevo nodo
  No hay datos
    meto a la mascota en un nuevo costal
        nuevo costal es igual a cabeza
        el anterior de cabeza coje a cabeza y el siguiente de cabeza coje a cabeza


   */
    public void addInPosition(Pet pet, int position) throws ListSEException {
        if(position>size){
            throw new ListSEException("No se puede agregar la mascota en esa posicion");
        }
        if (head != null) {
            if (position == 1) {
                if(!getConfirmKidById(pet.getId())) {
                    throw new ListSEException("Ya existe una mascota con esa identificacion");
                }
               addToStart(pet);
            }else if (position== size) {
                if(!getConfirmKidById(pet.getId())) {
                    throw new ListSEException("Ya existe una mascota con esa identificacion");
                }
                addToEnd(pet);
            }else {
                NodeDE temp = head;
                int count = 1;
                while (temp.getNext() != head && count < position-1) {
                    if(!getConfirmKidById(pet.getId())) {
                        throw new ListSEException("Ya existe una mascota con esa identificacion");
                    }
                    temp = temp.getNext();
                    count++;
                }
                NodeDE newNodeDE = new NodeDE(pet);
                newNodeDE.setNext(temp.getNext());
                newNodeDE.setPrevious(temp);
                temp.getNext().setPrevious(newNodeDE);
                temp.setNext(newNodeDE);
            }

        } else {
            head = new NodeDE(pet);
            head.setPrevious(head);
            head.setNext(head);
        }
        size++;
    }

    /*
    Agregar al final(entra por parametro la mascota)
    si hay datos
        verifico si ya existe esa identificacion
        Meto a la mascota en nuevo costal
        nuevo nodo coje con su siguiente a cabeza
        nuevo nodo con su previuos coje al anterior de cabeza
        El anterior de cabeza coje con su siguiente a nuevo nodo
        cabeza coje con su anterior a nuevo nodo
   No hay datos
        Meto a la mascota en nuevo costal
        le digo que es igual a cabeza
        el anterior de cabeza coje a cabeza y el siguiente de cabeza coje a cabeza

        le digo ayudante que se oare en cabeza
        mientras el previous de cabeza sea diferente de nulo noooooo

     */

    public void addToEnd(Pet pet)throws ListSEException{
        NodeDE newNodeDE = new NodeDE(pet);
        if (head != null) {
            if(!getConfirmKidById(pet.getId())) {
                throw new ListSEException("Ya existe una mascota con esa identificacion");
            }
            newNodeDE.setNext(head);
            newNodeDE.setPrevious(head.getPrevious());
            head.getPrevious().setNext(newNodeDE);
            head.setPrevious(newNodeDE);
        } else {
            head = newNodeDE;
            head.setPrevious(newNodeDE);
            head.setNext(newNodeDE);
        }
        size++;
    }

    /*
    Bañar mascota (por parámetro dirección hacia donde quiere ir)
    Si Hay datos
        llamo a la clase random para escoger la posición aleatorio
        Me paro en cabeza con ayudante
        Preguntar hacia donde quiere girar derecha o izquierda

            Derecha
            Creo contador
            Mientras contador sea menor que la posición
                sigo hacia la derecha osea el siguiente de ayudante
                Incrementa Contador
                Si la mascota está sucia
                    La baño
                Si no digo que la mascota ya está bañada

            Izquierda
            Creo contador
            Mientras contador sea menor que la posición
                sigo hacia la izquierda osea el previos de ayudante
                disminuye Contador, ya que vamos hacia el anterior
                Si la mascota está sucia
                    La baño
                Si no digo que la mascota ya está bañada

           si la direccion es invalida que salga un mensaje que solo acepta right o left

    No hay datos
    La lista está vacía
     */

    public void getBathPet(String direction) throws ListSEException {
        if (head != null) {
            Random random = new Random();
            int position = random.nextInt(size) + 1;
            NodeDE temp = head;
            if (direction.equals("right")) {
                for (int i = 1; i < position; i++) {
                    temp = temp.getNext();
                }
            } else if (direction.equals("left")) {
                for (int i = size; i > position; i--) {
                    temp = temp.getPrevious();
                }
            } else {
                throw new ListSEException("Dirección inválida, debe ser 'right' o 'left'");
            }
            if (temp.getData().getBath().equals("dirty")) {
                temp.getData().setBath("clean");
            } else {
                throw new ListSEException("La mascota ya está bañada");
            }
        }else{
            throw new ListSEException("La lista está vacía");
        }
    }

    /*
    Metodo confirmar id de la mascota de tipo boolean (me entra por parametro el id de la mascota)
        si hay datos
            ayudante se para en cabeza
            mientras el siguiente de ayudante de diferente de cabeza
                si el id que ya hay es igual al ingresado
                    me retorna falso
                sigo

           aqui tengo que comprobar el ultimo nodo de la lista
           si el id que ya hay es igual al ingresado
                me retorna falso

         si no me retorna verdadero es decir la identificacion no existe
     */

    public boolean getConfirmKidById(String id) {
        if (head != null) {
            NodeDE temp = head;
            while (temp.getNext() != head) {
                if (temp.getData().getId().equals(id)) {
                    return false;// La identificación ya existe
                }
                temp = temp.getNext();
            }
            // Comprobar la última mascota en la lista
            if (temp.getData().getId().equals(id)) {
                return false;// La identificación ya existe
            }
        }
        return true; // La identificación no existe
    }
}
