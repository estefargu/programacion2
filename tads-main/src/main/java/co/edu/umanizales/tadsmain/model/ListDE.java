package co.edu.umanizales.tadsmain.model;

import lombok.Data;

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
}
