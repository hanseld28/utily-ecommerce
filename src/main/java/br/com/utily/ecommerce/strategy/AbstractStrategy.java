package br.com.utily.ecommerce.strategy;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStrategy<T> implements IStrategy<T> {

    private final List<String> requiredBusinessRules;

    public AbstractStrategy(List<String> requiredBusinessRules) {
        this.requiredBusinessRules = requiredBusinessRules;
    }

    public boolean canBeProcessed(ArrayList<String> validatedBusinessRules) {
        for (String requiredBusinessRuleName : requiredBusinessRules) {
            if (!validatedBusinessRules.contains(requiredBusinessRuleName)) {
                return false;
            }
        }
        return true;
    }
}
