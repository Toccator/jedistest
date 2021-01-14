package patternClear;


import java.util.concurrent.Callable;

public interface IOperation extends Callable<Long> {
    void setOperationContext(OperationContext operationContext);

}
