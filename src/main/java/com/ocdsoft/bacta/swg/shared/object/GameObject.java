package com.ocdsoft.bacta.swg.shared.object;

import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.soe.object.Transform;
import com.ocdsoft.bacta.swg.shared.collision.CollisionProperty;
import com.ocdsoft.bacta.swg.shared.container.ContainedByProperty;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.property.Property;
import com.ocdsoft.bacta.swg.template.ObjectTemplate;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by crush on 4/22/2016.
 */
public abstract class GameObject extends NetworkObject {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GameObject.class);

    @Getter
    private boolean inWorld;
    @Getter
    private boolean active;
    @Getter
    private boolean kill;
    @Getter
    private boolean authoritative;
    @Getter
    private boolean childObject;
    @Getter
    @Setter
    private volatile boolean objectToWorldDirty;
    @Getter
    private boolean destroyed;
    @Getter
    private boolean altering;

    @Getter
    private final ObjectTemplate objectTemplate;

    //private Appearance appearance;
    //private Controller controller;
    //private Dynamics dynamics;

    private GameObject attachedToObject;
    private List<GameObject> attachedObjects;
    //private DpvsObjects dpvsObjects;

    private int rotations;
    //private Vector scale;
    private Transform objectToParent;
    private Transform objectToWorld;

    //private volatile WatchedByList watchedByList;

    @Getter
    private Container containerProperty;
    private CollisionProperty collisionProperty;

    //private SpatialSubdivisionHandle spatialSubdivisionHandle;

    private boolean useAlterScheduler;
    //private ScheduleData scheduleData;

    private boolean shouldBakeIntoMesh;

    @Getter
    protected ContainedByProperty containedByProperty;
    protected final List<Property> propertyList = new ArrayList<>();

    public GameObject() {
        this(null);
    }

    public GameObject(final ObjectTemplate objectTemplate) {
        this(objectTemplate, NetworkObject.INVALID);
    }

    public GameObject(final ObjectTemplate objectTemplate, final long networkId) {
        this.inWorld = false;
        this.active = true;
        this.kill = false;
        this.authoritative = false;
        this.childObject = false;
        this.objectToWorldDirty = true;
        this.destroyed = false;
        this.altering = false;
        this.objectTemplate = objectTemplate;
        //this.notificationList(NotificationListManager.getEmptyNotificationList());
        this.networkId = networkId;
        //this.appearance = null;
        //this.controller = null;
        //this.dynamics = null;
        this.attachedToObject = null;
        this.attachedObjects = null;
        //this.dpvsObjects = null;
        this.rotations = 0;
        //this.scale = Vector.xyz111;
        this.objectToParent = new Transform();
        this.objectToWorld = null;
        //this.watchedByList = new WatchedByList();
        this.containerProperty = null;
        //this.collisionProperty = null;
        //this.spatialSubdivisionHandler = null;
        this.useAlterScheduler = true;
        //this.scheduleData = null;
        this.shouldBakeIntoMesh = true;
        //this.defaultAppearance = null;
        //this.alternateAppearance = null;
        this.containedByProperty = null;

        objectTemplate.addReference();
        //NetworkIdManager::addObject(this);
    }

    public String getObjectTemplateName() {
        return objectTemplate != null ? objectTemplate.getResourceName() : null;
    }

    public void addToWorld() {
        if (inWorld) {
            LOGGER.warn("Object ({} : {}) is already in the world.",
                    getObjectTemplateName(), getNetworkId());
            return;
        }

        setObjectToWorldDirty(true);

        inWorld = true;

        //if (appearance != null)
        //    appearance.addToWorld();

        //notificationList.addToWorld(this);

        if (attachedObjects != null && !attachedObjects.isEmpty()) {
            attachedObjects.stream()
                    .filter(attachedObject -> attachedObject.isChildObject() && !attachedObject.isInWorld())
                    .forEach(GameObject::addToWorld);
        }
    }

    public void removeFromWorld() {
    }
}
