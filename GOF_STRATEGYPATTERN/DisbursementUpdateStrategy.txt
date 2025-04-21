@Component("Disbursement")
public class DisbursementUpdateStrategy implements OperationUpdateStrategy {
    private final PersonneMoraleRepository personneMoraleRepository;
    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    public DisbursementUpdateStrategy(PersonneMoraleRepository pmRepo, PersonnePhysiqueRepository ppRepo) {
        this.personneMoraleRepository = pmRepo;
        this.personnePhysiqueRepository = ppRepo;
    }

    @Override
    public void update(Operation operation, OperationRequest request) throws Exception {
        if (request.getPersonneMorale() != null) {
            PersonMorale pm = personneMoraleRepository.findByCode(request.getPersonneMorale());
            operation.setPersonneMorale(pm);
        }
        if (request.getPersonnePhysique() != null) {
            PersonPhysique pp = personnePhysiqueRepository.findByCin(request.getPersonnePhysique());
            operation.setPersonnePhysique(pp);
        }
    }
}
