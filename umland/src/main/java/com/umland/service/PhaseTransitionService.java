package com.umland.service;

import com.umland.dao.PhaseDao;
import com.umland.dao.PhaseTransitionDao;
import com.umland.entities.Phase;
import com.umland.entities.PhaseTransition;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PhaseTransitionService {

    private final PhaseTransitionDao transitionDao;
    private final PhaseDao phaseDao;

    
    public PhaseTransitionService(PhaseTransitionDao transitionDao, PhaseDao phaseDao) {
        this.transitionDao = transitionDao;
        this.phaseDao = phaseDao;
    }

    @Transactional(readOnly = true)
    public List<PhaseTransition> getByFromPhase(Integer fromPhaseId) {
        return transitionDao.findByFromPhase_Id(fromPhaseId);
    }

    @Transactional(readOnly = true)
    public List<PhaseTransition> getByToPhase(Integer toPhaseId) {
        return transitionDao.findByToPhase_Id(toPhaseId);
    }
    
    @Transactional
    public PhaseTransition create(Integer fromPhaseId, Integer toPhaseId, String optionText) {
        Phase from = phaseDao.findById(fromPhaseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "fromPhase not found"));

        Phase to = null;
        if (toPhaseId != null) {
            to = phaseDao.findById(toPhaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "toPhase not found"));
        }

        // Cria a transição
        PhaseTransition t = new PhaseTransition();
        t.setFromPhase(from);
        t.setToPhase(to);
        t.setOptionText(optionText);

        // Verifica se já existe uma transição com os mesmos fromPhase e toPhase
        if (transitionDao.existsByFromPhaseAndToPhase(from, to)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PhaseTransition with the same fromPhase and toPhase already exists");
        }
        PhaseTransition savedTransition = transitionDao.save(t);

        return savedTransition;
    }

    @Transactional
	public PhaseTransition update(Integer transitionId, Integer fromPhaseId, Integer toPhaseId, String optionText) {
	    PhaseTransition t = transitionDao.findById(transitionId)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transition not found"));
	
	    // Armazena os valores antigos de fromPhase e toPhase
	    Phase oldFromPhase = t.getFromPhase();
	    Phase oldToPhase = t.getToPhase();
	
	    // Atualiza os campos
	    if (fromPhaseId != null) {
	        Phase from = phaseDao.findById(fromPhaseId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "fromPhase not found"));
	        t.setFromPhase(from);
	    }
	
	    if (toPhaseId == null) {
	        t.setToPhase(null);
	    } else {
	        Phase to = phaseDao.findById(toPhaseId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "toPhase not found"));
	        t.setToPhase(to);
	    }
	
	    if (optionText != null) {
	        t.setOptionText(optionText);
	    }
	
	    // Salva a transição atualizada
	    PhaseTransition updatedTransition = transitionDao.save(t);
	
	    // Verifica se os valores de fromPhase e toPhase foram alterados
	    if ((!oldFromPhase.equals(t.getFromPhase()) || !oldToPhase.equals(t.getToPhase())) &&
	        transitionDao.existsByFromPhaseAndToPhase(t.getFromPhase(), t.getToPhase())) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "PhaseTransition with the same fromPhase and toPhase already exists");
	    }
	
	    return updatedTransition;
	}
    

    @Transactional
    public void delete(Integer transitionId) {
        if (!transitionDao.existsById(transitionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "transition not found");
        }
        transitionDao.deleteById(transitionId);
    }
    

}