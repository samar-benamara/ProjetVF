package com.C_TechProject;

import java.io.IOException;

import com.C_TechProject.bankAccount.BankAccount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BankAccountSerializer extends StdSerializer<BankAccount> {

    public BankAccountSerializer() {
        this(null);
    }

    public BankAccountSerializer(Class<BankAccount> t) {
        super(t);
    }

    @Override
    public void serialize(BankAccount value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());

        gen.writeStringField("rib", value.getRib());
        gen.writeObjectField("bank", value.getBank().getNameBanque());
        gen.writeObjectField("legalEntity", value.getLegalEntity().getNameEntity());
        gen.writeEndObject();
    }

}

