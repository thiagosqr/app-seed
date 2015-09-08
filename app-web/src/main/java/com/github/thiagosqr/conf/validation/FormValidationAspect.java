package com.github.thiagosqr.conf.validation;


import com.github.thiagosqr.conf.converters.DateParam;
import com.github.thiagosqr.conf.excecao.GoiasResourceMessage;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.glassfish.jersey.server.mvc.ErrorTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.FormParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Título:</b> FormValidationAspect
 * <br><b>Descrição:</b> Aspecto para mapeamento<br>
 * dos eventos de validação de form em controllers Restful JAX-RS
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 * @see FormValidation
 */
@Aspect
@Named
public class FormValidationAspect {

    @Inject
    private ValidationSet validations;

    Logger log = Logger.getLogger(FormValidationAspect.class);

    @Before("execution(* *.*(..)) && @annotation(formValidation) ")
    public void extractActionMetadata(JoinPoint joinPoint, FormValidation formValidation) throws ValidationException {

        try {

            HttpServletRequest request = validations.getServletrequest();

            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Iterator<Object> args = Arrays.asList(joinPoint.getArgs()).iterator();
            Map<String, List<Annotation>> errorParams = new HashMap<>();
            Map<String, Object> formParams = new HashMap<>();

            for (Parameter p : method.getParameters()) {

                final Object paramValue = args.next();

                List<Annotation> erros = Arrays.asList(p.getAnnotations()).stream()
                        .filter(a -> validations.getRules().containsKey(a.annotationType()))
                        .filter(a -> !validations.isParameterizedAnnotation(a.annotationType()))
                        .filter(ann -> !validations.getRules().get(ann.annotationType()).apply(paramValue))
                        .collect(Collectors.toList());

                erros.addAll(Arrays.asList(p.getAnnotations()).stream()
                                .filter(a -> validations.getRules().containsKey(a.annotationType()))
                                .filter(a -> validations.isParameterizedAnnotation(a.annotationType()))
                                .filter(ann -> !validations.getRules().get(ann.annotationType()).apply(new ValidationValue(ann, paramValue)))
                                .collect(Collectors.toList())
                );

                if(!erros.isEmpty() && !p.getAnnotation(FormParam.class).value().isEmpty()){
                    errorParams.put(p.getAnnotation(FormParam.class).value(), erros);
                }

                formParams.put(p.getAnnotation(FormParam.class).value(), getParamCorrespondingValue(paramValue));
            }

            if (errorParams.size() > 0) {
                request.setAttribute("formParams", formParams);
                request.setAttribute("validations", getValidationMsgs(errorParams));
                request.setAttribute("errorTemplate", method.getAnnotation(ErrorTemplate.class).name());
                throw new ValidationException();
            }

        } catch (NullPointerException e) {

            log.error(GoiasResourceMessage.getMessage("msg_erro_obter_template"), e);

        }

    }

    private Map<String, List<String>> getValidationMsgs(Map<String, List<Annotation>> errorParams){

        Map<String, List<String>> erros = new HashMap<>();

        for(String s : errorParams.keySet()){
            erros.put(s, getValidationMsgsByParam(errorParams.get(s)));
        }

        return erros;

    }

    private List<String> getValidationMsgsByParam(List<Annotation> errorParams){
        return errorParams.stream().map(a -> validations.getValMsgFunc(a).apply(a)).collect(Collectors.toList());

    }

    private Object getParamCorrespondingValue(final Object paramValue){

        if(DateParam.class.isInstance(paramValue))
            return ((DateParam)paramValue).getDate();

        return paramValue;

    }


}
