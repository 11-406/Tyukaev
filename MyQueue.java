import java.util.Stack;

public class MyQueue {
    private final Stack<Integer> inputStack;
    private final Stack<Integer> outputStack;

    public CustomQueue() {
        this.inputStack = new Stack<>();
        this.outputStack = new Stack<>();
    }

    public CustomQueue(Stack<Integer> initialInput, Stack<Integer> initialOutput) {
        this.inputStack = initialInput;
        this.outputStack = initialOutput;
    }

    public int getCurrentSize() {
        return inputStack.size() + outputStack.size();
    }

    public boolean hasNoElements() {
        return inputStack.empty() && outputStack.empty();
    }

    public void enqueue(int value) {
        inputStack.push(value);
    }

    public int dequeue() {
        if (hasNoElements()) {
            throw new RuntimeException("Cannot dequeue from empty queue");
        }

        if (outputStack.isEmpty()) {
            transferElements();
        }

        return outputStack.pop();
    }

    private void transferElements() {
        while (!inputStack.isEmpty()) {
            outputStack.push(inputStack.pop());
        }
    }
}