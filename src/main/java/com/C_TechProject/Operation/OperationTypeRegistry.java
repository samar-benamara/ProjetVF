@Component
public class OperationTypeRegistry {

    private final Map<String, OperationType> types;

    public OperationTypeRegistry(List<OperationType> list) {
        this.types = list.stream()
            .collect(Collectors.toMap(t -> t.getType().toLowerCase(), t -> t));
    }

    public OperationType getHandler(String type) {
        OperationType handler = types.get(type.toLowerCase());
        if (handler == null) {
            throw new IllegalArgumentException("Type d'opération non reconnu : " + type);
        }
        return handler;
    }
}
