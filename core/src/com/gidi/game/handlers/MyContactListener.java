package com.gidi.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by JustMac on 22/10/2016.
 */

public class MyContactListener implements ContactListener {
    private boolean playerOnGround;
    private int numFootContacts;
    private Array<Body> bodiesToRemove;

    public MyContactListener() {
        super();
        this.bodiesToRemove = new Array<Body>();
    }

    @Override
    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts++;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts++;
        }
        if (fa.getUserData() != null && fa.getUserData().equals("crystal")) {
            // remove body
            bodiesToRemove.add(fa.getBody());
        }
        if (fb.getUserData() != null && fb.getUserData().equals("crystal")) {
            bodiesToRemove.add(fb.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts--;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts--;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
    }

    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
