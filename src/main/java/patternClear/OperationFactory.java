package patternClear;



import java.util.HashMap;
import java.util.Map;

public class OperationFactory {

    private static final Map<String,Class<? extends IOperation>> OPEARTION_MAP = new HashMap<>();
    static{
        OPEARTION_MAP.put("default",PatternDelStringOperation.class);
        OPEARTION_MAP.put("takePlace",PatternDelStringOperation.class);
    }

    public static IOperation CreateOperation(String key, OperationContext operationContext){
        Class<? extends IOperation> operationClass = OPEARTION_MAP.get(key);
        try {
            IOperation operation = operationClass.newInstance();
            operation.setOperationContext(operationContext);
            return operation;

        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
