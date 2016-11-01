package com.gidi.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gidi.game.BBGame;
import com.gidi.game.entities.Crystal;
import com.gidi.game.entities.Hud;
import com.gidi.game.entities.Player;
import com.gidi.game.handlers.GameStateManager;
import com.gidi.game.handlers.MyContactListener;
import com.gidi.game.handlers.MyInput;

import static com.gidi.game.handlers.B2DVars.BIT_BLUE;
import static com.gidi.game.handlers.B2DVars.BIT_CRYSTAL;
import static com.gidi.game.handlers.B2DVars.BIT_GREEN;
import static com.gidi.game.handlers.B2DVars.BIT_PLAYER;
import static com.gidi.game.handlers.B2DVars.BIT_RED;
import static com.gidi.game.handlers.B2DVars.PPM;

/**
 * Created by JustMac on 22/10/2016.
 */
public class Play extends GameState {
    private boolean debug = false;

    private World world;

    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    private MyContactListener cl;
    private TiledMap tiledMap;
    private float tilesize;

    private OrthoCachedTiledMapRenderer tmr;
    private Player player;
    private Array<Crystal> crystals;

    private Hud hud;

    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);
        // Set world and collision system
        world = new World(new Vector2(0, -9.81f), true);
        cl =  new MyContactListener();
        world.setContactListener( cl );
        b2dr = new Box2DDebugRenderer();
        
        //create player
        createPlayer();
        
        //create Tiles
        createTiles();
        
        // create crystals
        createCrystals();
        // set up b2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, BBGame.V_WIDTH / PPM, BBGame.V_HEIGHT / PPM);

        // set up Hud
        hud = new Hud(player);

    }

    private void createCrystals() {
        crystals = new Array<Crystal>();

        MapLayer layer =  tiledMap.getLayers().get("crystals");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.StaticBody;

        CircleShape cshape = new CircleShape();
        cshape.setRadius( 8 / PPM);
        fdef.shape = cshape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = BIT_CRYSTAL;
        fdef.filter.maskBits = BIT_PLAYER;

        for (MapObject mo : layer.getObjects()) {
            float x =  Float.valueOf(mo.getProperties().get("x").toString()) / PPM;
            float y =  Float.valueOf(mo.getProperties().get("y").toString()) / PPM;
            bdef.position.set(x, y);

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("crystal");

            Crystal c = new Crystal(body);
            crystals.add(c);

            body.setUserData(c);


        }
    }

    private void createTiles() {
        // load tile map
        tiledMap = new TmxMapLoader().load("maps/test.tmx");
        tmr = new OrthoCachedTiledMapRenderer(tiledMap);

        tilesize = Float.valueOf(tiledMap.getProperties().get("tilewidth").toString());
        System.out.println("tilesize "+ tiledMap.getProperties().get("tilewidth"));
        TiledMapTileLayer layer;

        layer = (TiledMapTileLayer) tiledMap.getLayers().get("red");
        createLayer(layer, BIT_RED);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("blue");
        createLayer(layer, BIT_BLUE);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("green");
        createLayer(layer, BIT_GREEN);


    }

    private void createLayer(TiledMapTileLayer layer, short bits) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        // go through all the cells in the layer
        for (int row = 0; row < layer.getHeight(); row++) {
            for(int col = 0; col < layer.getWidth(); col++){
                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                // check
                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                // create body and fixture for the cell
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(
                        (col + 0.5f) * tilesize / PPM,
                        (row + 0.5f) * tilesize / PPM
                );

                ChainShape chainShape = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tilesize / 2 / PPM, -tilesize / 2 / PPM);
                v[1] = new Vector2(-tilesize /2 / PPM, tilesize / 2 / PPM);
                v[2] = new Vector2(tilesize / 2 / PPM, tilesize/ 2 / PPM);
                chainShape.createChain(v);
                fdef.friction = 0;
                fdef.shape = chainShape;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = BIT_PLAYER;
                fdef.isSensor = false;

                world.createBody(bdef).createFixture(fdef);

            }
        }

    }

    private void createPlayer() {
        // create player
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        bdef.position.set(10 / PPM, 200 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(0.5f,0);
        Body body = world.createBody(bdef);

        shape.setAsBox(13 / PPM, 13 / PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_RED | BIT_CRYSTAL;
        body.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(13/ PPM, 2 / PPM, new Vector2(0, -13 / PPM),0);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_RED | BIT_CRYSTAL;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
        shape.dispose();

        // create player

        player = new Player(body);

        body.setUserData(player);
    }

    public void handleInput(){
         if(MyInput.isPressed(MyInput.BUTTON1)){
             System.out.println("pressed z");
             if (cl.isPlayerOnGround()){
                 player.getBody().applyForceToCenter(0, 300, true);
             }
         }

        // change color
//        if(MyInput.isDown(MyInput.BUTTON2)){
//            System.out.println("hold x");
//            switchBlock();
//        }

        if (MyInput.isPressed(MyInput.BUTTON2)) {
            switchBlock();
        }
    }

    private void switchBlock() {
        Filter filter = player.getBody().getFixtureList().first().getFilterData();
        short bits = filter.maskBits;

        // red -> green -> blue -> red
        if ((bits & BIT_RED) != 0) {
            bits &= ~BIT_RED;
            bits |= BIT_GREEN;
        }else if ((bits & BIT_GREEN) != 0) {
            bits &= ~BIT_GREEN;
            bits |= BIT_BLUE;
        }else if ((bits & BIT_BLUE) != 0) {
            bits &= ~BIT_BLUE;
            bits |= BIT_RED;
        }

        filter.maskBits = bits;
        player.getBody().getFixtureList().first().setFilterData(filter);
        // set maskbit for foot
        filter = player.getBody().getFixtureList().get(1).getFilterData();
        bits &= ~BIT_CRYSTAL;
        filter.maskBits = bits;
        player.getBody().getFixtureList().get(1).setFilterData(filter);

    }

    public void update(float dt){
        handleInput();
        world.step(Gdx.graphics.getDeltaTime(), 6,  2);

        // remove crystals
        Array<Body> bodies = cl.getBodiesToRemove();
        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            crystals.removeValue((Crystal) b.getUserData(), true);
            world.destroyBody(b);
            player.collectCrystal();
        }
        bodies.clear();

        player.update(dt);
        for (int i = 0; i < crystals.size; i++) {
            crystals.get(i).update(dt);
        }
    }
    public void render(){
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.set(player.getPosition().x * PPM + BBGame.V_WIDTH / 4,
                BBGame.V_HEIGHT / 2, 0);
        cam.update();

        // draw tile map
        tmr.setView(cam);
        tmr.render();

        // draw player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);

        // draw crystals
        for (int i = 0; i < crystals.size; i++) {
            crystals.get(i).render(sb);
        }

        // draw hud

        sb.setProjectionMatrix(hudCam.combined);
        hud.render(sb);

        if (debug){
            // draw box2d world
            b2dr.render(world, b2dCam.combined);
        }

    }
    public void dispose(){

    }

}
