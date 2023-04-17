package co.edu.umanizales.tadsmain.model;

import lombok.Data;

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
    public void add(Kid kid){
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                temp = temp.getNext();
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
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
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


    /*Eliminar niño (identificacion niño) -> Entra por parametro
      Cabeza es igual al primer costal o primer nodo
      Llamo a un ayudante y le digo que se posicione en la cabeza
      Pregunto si el ayudante tiene alguien adelante y sea igual a la identificacion pedida
      Si
        Paro y elimino al niño
      No
        Sigo hasta encontarar la posicion a eliminar*/
    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }

    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public float getAverageAgeByKid() {

        if (head != null) {
            int count = 0;
            byte age =0;
            Node temp = this.head;

            while (temp.getNext() != null) {
                count = count + 1;
                age += temp.getData().getAge();
                temp = temp.getNext();
            }

            return (float) age/count;
        }
        return (float)0;
    }

    public boolean getKidById (String identification)
    {
        if(head != null){
            Node temp = head;
            while(temp !=null)
            {
                if(temp.getData().getIdentification().equals(identification))
                    return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

}
