package com.github.thiagosqr.conf.view.model;

import com.github.thiagosqr.conf.validation.FormValidationAspect;
import com.github.thiagosqr.conf.validation.ValidationSet;

import java.util.List;
import java.util.Map;

/**
 * <b>Título:</b> ViewErrorModel
 * <br><b>Descrição:</b> View Model para validação <br>
 * de error para apresentação. Associa os erros <br>
 * dos parâmetros da requisição ou form e monta objeto <br>
 * para construção da apresentação com os erros
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 * @see ValidationSet
 * @see FormValidationAspect
 */
public class ViewErrorModel {

    private Map<String, Object> formParams;
    private Map<String, List<String>> validations;

    public ViewErrorModel(){}

    public ViewErrorModel(Map<String, Object> formParams, Map<String, List<String>> validations) {
        this.formParams = formParams;
        this.validations = validations;
    }

    public Map<String, Object> getFormParams() {
        return formParams;
    }

    public Map<String, List<String>> getValidations() {
        return validations;
    }

    public void setFormParams(Map<String, Object> formParams) {
        this.formParams = formParams;
    }

    public void setValidations(Map<String, List<String>> validations) {
        this.validations = validations;
    }
}


