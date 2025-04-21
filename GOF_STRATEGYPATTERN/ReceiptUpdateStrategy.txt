@Component("Receipt")
public class ReceiptUpdateStrategy implements OperationUpdateStrategy {
    private final LegalEntityRepository legalEntityRepository;

    @Autowired
    public ReceiptUpdateStrategy(LegalEntityRepository legalEntityRepository) {
        this.legalEntityRepository = legalEntityRepository;
    }

    @Override
    public void update(Operation operation, OperationRequest request) throws Exception {
        if (request.getLegalEntity() != null) {
            LegalEntity entity = legalEntityRepository.findByNameEntity(request.getLegalEntity());
            operation.setLegalEntity(entity);
        }
    }
}
