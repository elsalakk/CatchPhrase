package StarApps.guessIt;

final class Node{
    private Object data;
    private Node next;
    private Node prev;
    Node(Object data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    Node getNext(){
        return next;
    }
    Node getPrev(){
        return prev;
    }
    void setNext(Node next) {
        this.next = next;
    }
    void setPrev(Node prev){
        this.prev = prev;
    }
    Object getObj(){
        return data;
    }
}