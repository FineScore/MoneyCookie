package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.services.Evaluation;

public interface EvaluationRepository {
    void save(Evaluation evaluation);

    void update(Evaluation newEval);
}
