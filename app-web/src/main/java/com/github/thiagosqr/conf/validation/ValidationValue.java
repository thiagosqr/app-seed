package com.github.thiagosqr.conf.validation;

import java.lang.annotation.Annotation;

/**
 * <b>Título:</b> ValidationValue
 * <br><b>Descrição:</b> Valor a ser usado em uma validação<br>
 * possibilita a associação de uma regra pela annotation e o valor a ser validado
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 * @see ValidationSet
 * @see FormValidationAspect
 */
public class ValidationValue {
    public ValidationValue(Annotation ann, Object value) {
        this.ann = ann;
        this.value = value;
    }

    private Annotation ann;
    private Object value;

    public Annotation getAnn() {
        return ann;
    }

    public Object getValue() {
        return value;
    }

    public void setAnn(Annotation ann) {
        this.ann = ann;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
