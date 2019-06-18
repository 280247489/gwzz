package com.memory.gwzz.model;

import java.util.Objects;

/**
 * @ClassName Banner
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/14 15:35
 */
public class Banner {
   private String id;
   private String typeTable;
   private String typeTableId;
   private String bannerLogo;
   private int bannerSort;
   private int bannerOnline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeTable() {
        return typeTable;
    }

    public void setTypeTable(String typeTable) {
        this.typeTable = typeTable;
    }

    public String getTypeTableId() {
        return typeTableId;
    }

    public void setTypeTableId(String typeTableId) {
        this.typeTableId = typeTableId;
    }

    public String getBannerLogo() {
        return bannerLogo;
    }

    public void setBannerLogo(String bannerLogo) {
        this.bannerLogo = bannerLogo;
    }

    public int getBannerSort() {
        return bannerSort;
    }

    public void setBannerSort(int bannerSort) {
        this.bannerSort = bannerSort;
    }

    public int getBannerOnline() {
        return bannerOnline;
    }

    public void setBannerOnline(int bannerOnline) {
        this.bannerOnline = bannerOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return bannerSort == banner.bannerSort &&
                bannerOnline == banner.bannerOnline &&
                Objects.equals(id, banner.id) &&
                Objects.equals(typeTable, banner.typeTable) &&
                Objects.equals(typeTableId, banner.typeTableId) &&
                Objects.equals(bannerLogo, banner.bannerLogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeTable, typeTableId, bannerLogo, bannerSort, bannerOnline);
    }

    public Banner() {
    }

    public Banner(String id, String typeTable, String typeTableId, String bannerLogo, int bannerSort, int bannerOnline) {
        this.id = id;
        this.typeTable = typeTable;
        this.typeTableId = typeTableId;
        this.bannerLogo = bannerLogo;
        this.bannerSort = bannerSort;
        this.bannerOnline = bannerOnline;
    }
}
