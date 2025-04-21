@Service
@Transactional
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final PersonneMoraleRepository personneMoraleRepository;
    private final ReceiptUpdateStrategy receiptStrategy;
    private final DisbursementUpdateStrategy disbursementStrategy;

    public Operation addOperation(OperationRequest operation) throws Exception {

        if (operation.getType() == null ||operation.getEtat()==null || operation.getReglement()==null || operation.getBank() == null || operation.getLegalEntity() == null ||
                operation.getBankAccount() == null) {
            throw new IllegalArgumentException("Missing required fields in the Operation entity");
        }

        Bank bank=bankRepository.findByNameBanque(operation.getBank());
        LegalEntity legalEntity=legalEntityRepository.findByNameEntity(operation.getLegalEntity());
        BankAccount bankAccount=bankAccountRepository.findBankAccountByRib(operation.getBankAccount());
        PersonPhysique personPhysique=personnePhysiqueRepository.findByCin((operation.getPersonnePhysique()));
        PersonMorale personMorale=personneMoraleRepository.findByCode(operation.getPersonneMorale());

        Operation operation1=Operation.builder()
                .type(operation.getType()).etat(operation.getEtat()).montant(operation.getMontant()).reglement(operation.getReglement()).numcheque(operation.getNumcheque())
                        .bank(bank).legalEntity(legalEntity).bankAccount(bankAccount).personnePhysique(personPhysique)
                        .personneMorale(personMorale).creationDate(new Date())
                                .build();

        Operation savedoperation=operationRepository.save(operation1);

        return savedoperation;
    }

    public OperationResponse findOperationById(Integer id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (operation.isPresent()) {
            Operation op = operation.get();
            OperationResponse response = new OperationResponse();
            response.setId(op.getId());
            response.setType(op.getType());
            response.setEtat(op.getEtat());
            response.setMontant(op.getMontant());

            response.setReglement(op.getReglement());
            response.setNumcheque(op.getNumcheque()!=null ? op.getNumcheque():null);
            response.setBank(op.getBank().getNameBanque());
            response.setLegalEntity(op.getLegalEntity().getNameEntity());
            response.setBankAccount(op.getBankAccount().getRib());
            response.setPersonnePhysique(op.getPersonnePhysique() != null ? op.getPersonnePhysique().getCin() : null);
            response.setPersonneMorale(op.getPersonneMorale() != null ? op.getPersonneMorale().getCode() : null);
            response.setCreationDate(String.valueOf(op.getCreationDate()));

            return response;
        } else {
            throw new RuntimeException("Operation not found with ID: " + id);
        }
    }


    public List<OperationResponse> findAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations.stream()
                .map(operation -> {
                    OperationResponse response = new OperationResponse();
                    response.setId(operation.getId());
                    response.setType(operation.getType());
                    response.setEtat(operation.getEtat());
                    response.setMontant(operation.getMontant());

                    response.setReglement(operation.getReglement());
                    if (operation.getNumcheque() != null) {
                        response.setNumcheque(operation.getNumcheque());
                    }
                    response.setBank(operation.getBank().getNameBanque());
                    response.setLegalEntity(operation.getLegalEntity().getNameEntity());
                    response.setBankAccount(operation.getBankAccount().getRib());
                    if (operation.getPersonnePhysique() != null) {
                        response.setPersonnePhysique(operation.getPersonnePhysique().getCin());
                    }
                    if (operation.getPersonneMorale() != null) {
                        response.setPersonneMorale(operation.getPersonneMorale().getCode());
                    }
                    response.setCreationDate(String.valueOf(operation.getCreationDate()));

                    return response;
                })
                .collect(Collectors.toList());
    }


    public void deleteoperation (Integer id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("operation not found with ID: " + id));

        operationRepository.delete(operation);
    }

    public Operation updateOperation(Integer id, OperationRequest request) throws Exception {
        Operation operation = operationRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Operation not found with ID: " + id));

        // Mise à jour des champs principaux
        if (request.getType() != null && !request.getType().equals(operation.getType())) {
            operation.setType(request.getType());
        }

        if (request.getReglement() != null && !request.getReglement().equals(operation.getReglement())) {
            operation.setReglement(request.getReglement());
        }

        if (request.getEtat() != null && !request.getEtat().equals(operation.getEtat())) {
            operation.setEtat(request.getEtat());
            if (request.getNumcheque() != null && !request.getNumcheque().equals(operation.getNumcheque())) {
                operation.setNumcheque(request.getNumcheque());
            }
        }

        if (request.getBank() != null) {
            Bank bank = bankRepository.findByNameBanque(request.getBank());
            if (bank != null && !bank.equals(operation.getBank())) {
                operation.setBank(bank);
            }
        }

        if (request.getBankAccount() != null) {
            BankAccount bankAccount = bankAccountRepository.findBankAccountByRib(request.getBankAccount());
            if (bankAccount != null && !bankAccount.equals(operation.getBankAccount())) {
                operation.setBankAccount(bankAccount);
            }
        }

        // Création du contexte manuellement
        OperationUpdateContext context = new OperationUpdateContext();

        String type = request.getType();

        // Appliquer manuellement la bonne stratégie
        if ("Receipt".equalsIgnoreCase(type)) {
            context.setStrategy(receiptStrategy);
        } else if ("Disbursement".equalsIgnoreCase(type)) {
            context.setStrategy(disbursementStrategy);
        } else {
            throw new IllegalArgumentException("Unsupported operation type: " + type);
        }

        // Exécution de la stratégie
        context.doUpdate(operation, request);

        return operationRepository.save(operation);
    }
}
