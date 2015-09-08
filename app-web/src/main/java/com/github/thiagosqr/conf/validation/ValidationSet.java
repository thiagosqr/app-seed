package com.github.thiagosqr.conf.validation;

import com.github.thiagosqr.conf.excecao.GoiasResourceMessage;
import com.github.thiagosqr.conf.converters.DateParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * <b>Título:</b> ValidationSet
 * <br><b>Descrição:</b> Coleção de validações <br>
 * Implementação de Bean Validations (validation-api-1.0.0)
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 * @see ValidationSet
 * @see FormValidationAspect
 */
public abstract class ValidationSet {

    private static final Map<Class<? extends Annotation>, Function<Object,Boolean>> rules = new HashMap<>();
    private static final Map<Class<? extends Annotation>, Function<Annotation,String>> msgFuncs = new HashMap<>();

    private static final List<Class> paramAnnotations = Arrays.asList(new Class[]{
        DecimalMax.class, DecimalMin.class, Digits.class, Max.class, Min.class, Pattern.class, Size.class
    });

    public ValidationSet(){

        rules.put(Future.class, ValidationSet::isFuture);
        msgFuncs.put(Future.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(Past.class, ValidationSet::isPast);
        msgFuncs.put(Past.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(NotEmpty.class, (Object s) -> {return String.class.isInstance(s)?!((String)s).isEmpty():false;});
        msgFuncs.put(NotEmpty.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(NotNull.class, (Object o) -> { return o != null; } );
        msgFuncs.put(NotNull.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(Null.class, (Object o) -> { return o == null; } );
        msgFuncs.put(Null.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(AssertFalse.class, (Object o) -> { return ((Boolean) o) == false; } );
        msgFuncs.put(AssertFalse.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(AssertTrue.class, (Object o) -> { return ((Boolean) o) == true; } );
        msgFuncs.put(AssertTrue.class, ValidationSet::getMsgDefault4Annotation);

        rules.put(DecimalMax.class, ValidationSet::isDecimalMax);
        msgFuncs.put(DecimalMax.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((DecimalMax) a).value()));

        rules.put(DecimalMin.class, ValidationSet::isDecimalMin);
        msgFuncs.put(DecimalMin.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((DecimalMin) a).value()));

        rules.put(Digits.class, ValidationSet::isWithinDigitsRange);
        msgFuncs.put(Digits.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((Digits) a).integer(), ((Digits) a).fraction()));

        rules.put(Max.class, ValidationSet::isLowerThanMax);
        msgFuncs.put(Max.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((Max) a).value()));

        rules.put(Min.class, ValidationSet::isGreaterThanMin);
        msgFuncs.put(Min.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((Min) a).value()));

        rules.put(Pattern.class, ValidationSet::checkPattern);
        msgFuncs.put(Pattern.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((Pattern) a).regexp()));

        rules.put(Size.class, ValidationSet::checkSize);
        msgFuncs.put(Size.class, (Annotation a) -> GoiasResourceMessage.getMessage(a.annotationType().getName(), ((Size) a).min(), ((Size) a).max()));


    }

    /**
     * Retorna um mapa com regras de validações mapeadas por classe
     * @return Map
     */
    public Map<Class<? extends Annotation>, Function<Object,Boolean>> getRules(){
        return rules;
    }

    /**
     * Retorna uma função que gera a mensagem de falha de validação
     * @param annotation annotation chave da mensagem
     * @return Function
     */
    public Function<Annotation, String> getValMsgFunc(Annotation annotation){
        return msgFuncs.get(annotation.annotationType());
    }

    /**
     * Retorna uma função que gera a mensagem de falha de validação
     * @param annotation classe da annotation chave da mensagem
     * @return Function
     */
    public Function<Annotation, String> getValMsgFunc(Class annotation){
        return msgFuncs.get(annotation);
    }



    /**
     * Registra uma nova regra para validação
     * @param annotation annotation que será usada como chave da regra
     * @param eval função que recebe um object e executa uma validação
     * @return true se a regra foi sobrescrita
     */
    public boolean registerRule(Class annotation, Function<Object, Boolean> eval) {
        final boolean constains = rules.containsKey(annotation);
        rules.put(annotation, eval);
        return constains;
    }

    /**
     * Registra uma nova regra para validação
     * @param annotation annotation que será usada como chave da regra
     * @param eval função que recebe um object e executa uma validação
     * @param msgFunc função que gera uma mensagem de erro no caso de erro de validação da função eval
     * @return true se a regra foi sobrescrita
     */
    public boolean registerRule(Class annotation, Function<Object, Boolean> eval, Function<Annotation, String> msgFunc) {
        msgFuncs.put(annotation, msgFunc == null? ValidationSet::getMsgDefault4Annotation: msgFunc);
        return registerRule(annotation, eval);
    }

    public static String getMsgDefault4Annotation(Annotation a){
        return GoiasResourceMessage.getMessage(a.annotationType().getName());
    }

    public abstract HttpServletRequest getServletrequest();

    /**
     * Verifica se Annotation possúi parâmetro
     * @param ann annotation chave de paramAnnotations
     * @return verdadeiro caso annotation seja parametrizada
     */
    public boolean isParameterizedAnnotation(Class ann){
        return paramAnnotations.contains(ann);
    }


    public static Boolean isPast(final Object d){

        try{

            return ((Date) d).before(new Date());

        }catch (NullPointerException e){
            return false;
        }catch (ClassCastException e){
            return ((DateParam) d).getDate() != null?
                    ((DateParam) d).getDate().before(new Date()) :
                    false;
        }

    }

    public static Boolean isFuture(final Object d){

        try{

            return ((Date) d).after(new Date());

        }catch (NullPointerException e){
            return false;
        }catch (ClassCastException e){
            return ((DateParam) d).getDate() != null?
                    ((DateParam) d).getDate().after(new Date()) :
                    false;

        }

    }

    public static Boolean isDecimalMax(final Object v){

        try{

            final ValidationValue vv = (ValidationValue) v;
            final DecimalMax dm = (DecimalMax) vv.getAnn();

            if(Double.class.isInstance(vv.getValue())){

                return ((Double) vv.getValue()) <= Double.parseDouble(dm.value());

            }else if(BigDecimal.class.isInstance(vv.getValue())){

                return ((BigDecimal) vv.getValue()).doubleValue() <= Double.parseDouble(dm.value());
            }

            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean isDecimalMin(final Object v){

        try{

            final ValidationValue vv = (ValidationValue) v;
            final DecimalMin dm = (DecimalMin) vv.getAnn();

            if(Double.class.isInstance(vv.getValue())){

                return ((Double) vv.getValue()) >= Double.parseDouble(dm.value());

            }else if(BigDecimal.class.isInstance(vv.getValue())){

                return ((BigDecimal) vv.getValue()).doubleValue() >= Double.parseDouble(dm.value());
            }

            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean isWithinDigitsRange(final Object v){

        try{

            final ValidationValue vv = (ValidationValue) v;
            final Digits d = (Digits) vv.getAnn();

            final BigDecimal bd = BigDecimal.class.isInstance(vv.getValue())?
                    (BigDecimal) vv.getValue():
                    new BigDecimal(String.valueOf(vv.getValue()));

            return bd.scale() <= d.fraction() && (bd.precision() - bd.scale()) <= d.integer();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean isLowerThanMax(final Object v){

        ValidationValue vv = null;
        Max d = null;

        try{

            vv = (ValidationValue) v;
            d = (Max) vv.getAnn();

            return d.value() >= ((Integer) vv.getValue());

        }catch (ClassCastException e){

            return d.value() >= ((Double) vv.getValue());

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private static Boolean isGreaterThanMin(Object v) {

        ValidationValue vv = null;
        Min d = null;

        try{

            vv = (ValidationValue) v;
            d = (Min) vv.getAnn();

            return d.value() <= ((Integer) vv.getValue());

        }catch (ClassCastException e){

            return d.value() <= ((Double) vv.getValue());

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    private static Boolean checkPattern(Object v) {

        try{

            final ValidationValue vv = (ValidationValue) v;
            final Pattern d = (Pattern) vv.getAnn();
            return java.util.regex.Pattern.matches(d.regexp(), (CharSequence) vv.getValue());

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    private static Boolean checkSize(Object v) {

        ValidationValue vv = null;
        Size d = null;

        try{

            vv = (ValidationValue) v;
            d = (Size) vv.getAnn();

            return d.max() >= (Integer) vv.getValue() && (Integer) vv.getValue() >= d.min();

        }catch (ClassCastException e){

            return d.max() >= (Double) vv.getValue() && (Double) vv.getValue() >= d.min();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
