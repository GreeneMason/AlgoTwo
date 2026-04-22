public class Main {
    public static void main(String[] args) {
    int[] unsortedArray = {3,4,7,2,5,3,7,3,7,4,8,9,1,2,3,4,5,6,7,8};
    
    // Heapify the array
    Heap heap = new Heap();
    heap.heapify(unsortedArray);
    System.out.println(heap.peek());
    System.out.println(heap.remove());
    heap.add(10);

    // Print Heap
        for (int i = 0; i < unsortedArray.length; i++) {
            System.out.print(unsortedArray[i] + " ");
        }
    System.out.println("\n");
    //Heap Sort
        for (int i = 0; i < unsortedArray.length; i++) {
            unsortedArray[i] = heap.remove();
        }
    // Print Heap
        for (int i = 0; i < unsortedArray.length; i++) {
            System.out.print(unsortedArray[i] + " ");
        }
        
    }
}

class Heap {
    private int size = 0;
    private int limit = 20;
    int[] heap;

    public Heap() {
        this.size = 0;
        this.heap = new int[limit];

    }
    //index operations
    private int leftNodeIndex(int index) {
        return 2 * index + 1;
    }
    private int rightNodeIndex(int index) {
        return 2 * index + 2;
    }
    private int parentNodeIndex(int index) {
        return (index - 1)/2;
    }

    //shifting opertions
    private void shiftUp(int index) {
        while (index > 0 && heap[index] > heap[parentNodeIndex(index)]) {
            swap(index, parentNodeIndex(index));
            index = parentNodeIndex(index);
        }
    }
    private void shiftDown(int index) {
        int maxIndex = index;
        int leftIndex = leftNodeIndex(index);
        int rightIndex = rightNodeIndex(index);
        if (leftIndex < size && heap[leftIndex] > heap[maxIndex]) {
            maxIndex = leftIndex;
        }

        if (rightIndex < size && heap[rightIndex] > heap[maxIndex]) {
            maxIndex = rightIndex;
        }
        
        if (index != maxIndex) {
            swap(index, maxIndex);
            shiftDown(maxIndex);
        }
    }

    //To view the top element of the heap
    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("The Heap is empty");
        }
        return heap[0];
    }

    //Add an element to the heap
    public void add(int value) {
        if (size == limit) {
            throw new IllegalStateException("The Heap's limit is at full capacity");
        }
        heap[size] = value;
        size++;
        shiftUp(size - 1);
    }

    //Remove the top element of the heap
    public int remove() {
        int result = heap[0];
        
        if (size == 0) {
            throw new IllegalStateException("The Heap is empty");
        }
        
        heap[0] = heap[size - 1];
        size--;
        shiftDown(0);
        return result;
    }

    //Swap two elements
    private void swap(int index1, int index2) {
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    // Heapify an array
    public void heapify(int[] array) {
        size = array.length;
        if (array.length > limit) {
            throw new IllegalStateException("The array size exceeds the heap's limit");
        }

        for (int i = 0; i < array.length; i++) {
            heap[i] = array[i];
        }
        
        for (int i = parentNodeIndex(size - 1); i >= 0; i--) {
            shiftDown(i);
        }
    }

}