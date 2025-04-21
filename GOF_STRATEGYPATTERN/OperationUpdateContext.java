public class OperationUpdateContext {

    private OperationUpdateStrategy strategy;

    // Permet de définir dynamiquement la stratégie à utiliser
    public void setStrategy(OperationUpdateStrategy strategy) {
        this.strategy = strategy;
    }

    // Exécute la mise à jour en utilisant la stratégie courante
    public void doUpdate(Operation operation, OperationRequest request) throws Exception {
        if (strategy == null) {
            throw new IllegalStateException("No strategy set.");
        }
        strategy.update(operation, request);
    }
}
