package com.ocdsoft.bacta.swg.shared.object.template;

/**
 * Created by crush on 3/4/14.
 */
public class SharedFactoryObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedFactoryObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SFOT; }

}
