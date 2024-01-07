package dev.supasintatiyanupanwong.libraries.android.kits.maps.model;

/**
 * @since 2.3.0
 */
public interface MapStyle extends MapClient.Style {

    /**
     * Defines styling options for a {@link MapClient}.
     *
     * <p>With style options you can customize the presentation of the standard map styles,
     * changing the visual display of features like roads, parks, and other points of interest.
     * As well as changing the style of these features, you can also hide features entirely.
     * This means that you can emphasize particular components of the map or make the map
     * complement the content of your app.
     */
    interface Options extends MapClient.Style.Options {}
}
