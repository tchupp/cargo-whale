package com.cargowhale.division.raml;

import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;

import static java.lang.System.out;

abstract class RamlApiVerifier {

    static void verifyRamlModelResult(final RamlModelResult ramlModelResult, final String ramlFile) {
        if (ramlModelResult.hasErrors()) {
            for (final ValidationResult result : ramlModelResult.getValidationResults()) {
                out.println(result.getMessage());
            }
            throw new IllegalStateException("Errors in " + ramlFile);
        }
    }
}
