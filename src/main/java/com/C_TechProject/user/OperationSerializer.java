package com.C_TechProject.user;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.bankAccount.BankAccount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class
OperationSerializer extends StdSerializer<Operation> {

    public OperationSerializer() {
        this(null);
    }

    public OperationSerializer(Class<Operation> t) {
        super(t);
    }

    @Override
    public void serialize(Operation value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("type", value.getType());
        gen.writeStringField("etat", value.getEtat());
        gen.writeStringField("reglement", value.getReglement());
        gen.writeObjectField("bank", value.getBank().getNameBanque());
        gen.writeObjectField("legalEntity", value.getLegalEntity().getNameEntity());
        gen.writeObjectField("bankAccount", value.getBankAccount());
        gen.writeObjectField("personnePhysique", value.getPersonnePhysique().getFirstName());
        gen.writeObjectField("personneMorale", value.getPersonneMorale().getName());
        gen.writeObjectField("creationDate", value.getCreationDate());
        gen.writeEndObject();
    }
}
