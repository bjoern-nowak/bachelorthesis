package com.oge.contrax.shared.infrastructure.jsf;

import *;

public class PostRenderViewListener implements PhaseListener {

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().publishEvent(context, PostRenderViewEvent.class, context.getViewRoot());
    }

}
