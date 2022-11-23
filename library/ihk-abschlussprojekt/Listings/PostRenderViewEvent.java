package com.oge.contrax.shared.infrastructure.jsf;

import *;

@NamedEvent(shortName="postRenderView")
public class PostRenderViewEvent extends ComponentSystemEvent {

    public PostRenderViewEvent(UIComponent component) {
        super(component);
    }

}
