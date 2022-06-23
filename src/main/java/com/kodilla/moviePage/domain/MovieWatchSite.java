package com.kodilla.moviePage.domain;

import java.util.Objects;

public class MovieWatchSite {

    private String display_name;
    private String id;
    private String url;
    private String icon;

    public MovieWatchSite() {
    }

    public MovieWatchSite(String display_name, String id, String url, String icon) {
        this.display_name = display_name;
        this.id = id;
        this.url = url;
        this.icon = icon;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieWatchSite that = (MovieWatchSite) o;
        return Objects.equals(display_name, that.display_name) && Objects.equals(id, that.id) && Objects.equals(url, that.url) && Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(display_name, id, url, icon);
    }
}
