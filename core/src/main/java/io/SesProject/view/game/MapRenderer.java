package io.SesProject.view.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Handles loading and rendering of TMX tile maps.
 * Supports orthogonal 2D maps with proper z-ordering.
 */
public class MapRenderer {
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Viewport viewport;

    /**
     * Creates a new MapRenderer.
     * 
     * @param assetManager The asset manager (reserved for future use)
     * @param viewport The viewport for rendering (can be set later)
     */
    public MapRenderer(AssetManager assetManager, Viewport viewport) {
        // AssetManager parameter kept for API compatibility but not currently used
        // Maps are loaded directly with TmxMapLoader
        this.viewport = viewport;
    }

    /**
     * Loads a TMX map from the assets/maps directory.
     * 
     * @param filename The TMX file name (e.g., "level1.tmx")
     * @throws RuntimeException if map loading fails
     */
    public void loadMap(String filename) {
        try {
            // Load the map using LibGDX's TmxMapLoader
            String mapPath = "maps/" + filename;
            TmxMapLoader loader = new TmxMapLoader();
            tiledMap = loader.load(mapPath);

            // Create the renderer for orthogonal maps
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

            System.out.println("[MAP] Successfully loaded: " + filename);
        } catch (Exception e) {
            System.err.println("[MAP] Error loading map: " + filename);
            System.err.println("[MAP] Error: " + e.getMessage());
            throw new RuntimeException("Failed to load map: " + filename, e);
        }
    }

    /**
     * Renders the map using the current viewport's camera.
     * Should be called after clearing the screen but before rendering sprites.
     */
    public void render() {
        if (tiledMapRenderer != null && viewport != null) {
            // Set the view to match the viewport's camera
            OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
        }
    }

    /**
     * Disposes of map resources. Call this in the screen's dispose method.
     */
    public void dispose() {
        if (tiledMap != null) {
            tiledMap.dispose();
            tiledMap = null;
        }
        tiledMapRenderer = null;
    }

    /**
     * Gets the loaded TiledMap.
     * @return The TiledMap, or null if not loaded
     */
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    /**
     * Gets the TiledMapRenderer.
     * @return The TiledMapRenderer, or null if not initialized
     */
    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    /**
     * Sets the viewport for rendering.
     * @param viewport The viewport to use
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
