package com.blkxltng.sdahymnal;

/**
 * Created by firej on 8/30/2016.
 */
public class Hymns {

    private int number;
    private String title;
    private String section;
    private int subSection;
    private int firstHymn;
    private int lastHymn;
    private String image;
    private int id;
    private String verse1;
    private String verse2;
    private String verse3;
    private String verse4;
    private String verse5;
    private String verse6;
    private String verse7;
    private String refrain;
    private String refrain2;
    private int favorited;

    public Hymns() {

    }

    public Hymns(String section) {
        this.section = section;
    }

    public String getRefrain2() {
        return refrain2;
    }

    public void setRefrain2(String refrain2) {
        this.refrain2 = refrain2;
    }

    public String getVerse7() {
        return verse7;
    }

    public void setVerse7(String verse7) {
        this.verse7 = verse7;
    }

    public int getFirstHymn() {
        return firstHymn;
    }

    public void setFirstHymn(int firstHymn) {
        this.firstHymn = firstHymn;
    }

    public int getLastHymn() {
        return lastHymn;
    }

    public void setLastHymn(int lastHymn) {
        this.lastHymn = lastHymn;
    }

    public int getFavorited() {
        return favorited;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }

    public String getRefrain() {
        return refrain;
    }

    public void setRefrain(String refrain) {
        this.refrain = refrain;
    }

    public String getVerse1() {
        return verse1;
    }

    public void setVerse1(String verse1) {
        this.verse1 = verse1;
    }

    public String getVerse2() {
        return verse2;
    }

    public void setVerse2(String verse2) {
        this.verse2 = verse2;
    }

    public String getVerse3() {
        return verse3;
    }

    public void setVerse3(String verse3) {
        this.verse3 = verse3;
    }

    public String getVerse4() {
        return verse4;
    }

    public void setVerse4(String verse4) {
        this.verse4 = verse4;
    }

    public String getVerse5() {
        return verse5;
    }

    public void setVerse5(String verse5) {
        this.verse5 = verse5;
    }

    public String getVerse6() {
        return verse6;
    }

    public void setVerse6(String verse6) {
        this.verse6 = verse6;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubSection() {
        return subSection;
    }

    public void setSubSection(int subSection) {
        this.subSection = subSection;
    }
}
